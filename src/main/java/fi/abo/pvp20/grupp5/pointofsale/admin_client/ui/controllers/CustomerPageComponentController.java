package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCard;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.ProductSaleRecord;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.SaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@FxmlView("customer-page-component.fxml")
public class CustomerPageComponentController {
    private static final DateTimeFormatter BIRTHDAY_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final SaleService saleService;

    @FXML
    private VBox customerPage;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label detailsLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private ListView<ProductSaleRecord> productListView;
    @FXML
    private Label cardsLabel;

    private BonusCustomer customer;

    @Autowired
    public CustomerPageComponentController(SaleService saleService) {
        this.saleService = saleService;
    }

    @FXML
    private void initialize() {
        customerPage.setVisible(false);
    }

    /**
     * Set which bonus customer to show in the GUI
     * @param customer The bonus customer to show visually
     */
    public void setCustomer(BonusCustomer customer) {
        this.customer = customer;
        updateUi();
    }

    private void updateUi() {
        customerPage.setVisible(true);
        idLabel.setText("ID: " + customer.getCustomerNumber());
        nameLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        detailsLabel.setText("Birthdate: " + customer.getBirthDate().format(BIRTHDAY_FORMAT)
                + "\nSex: " + customer.getSex().toString().toLowerCase()
                + "\nBonus points: " + customer.getBonusPoints());
        addressLabel.setText(customer.getAddress().toString());

        productListView.getItems().clear();
        saleService.getCustomerPurchases(customer.getCustomerNumber()).whenComplete((map, throwable) -> {
            Platform.runLater(() -> {
                if (throwable == null) {
                    // Sort items by count
                    var records = map.values().stream()
                            .sorted(Comparator.comparingInt(ProductSaleRecord::getCount))
                            .collect(Collectors.toList());
                    Collections.reverse(records);
                    productListView.getItems().setAll(records);
                } else {
                    AppStatus.showError(productListView.getScene(), "Could not get the customer's purchases", true);
                }
            });
        });

        cardsLabel.setText("Bonus cards:" + customer.getBonusCards().stream()
                .filter(bc -> !bc.getExpired() && !bc.getBlocked())
                .map(BonusCard::toString)
                .reduce("", (a, card) -> a + "\n" + card));
    }
}
