package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.services.BonusCustomerService;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("customer-tab.fxml")
public class CustomersTabController {
    private final BonusCustomerService bonusCustomerService;

    @FXML
    private Button findButton;
    @FXML
    private TextField customerIdField;
    @FXML
    private Label errorLabel;
    @FXML
    private CustomerPageComponentController pageController;

    /**
     * The CustomersTabController's constructor
     */
    @Autowired
    public CustomersTabController(BonusCustomerService bonusCustomerService) {
        this.bonusCustomerService = bonusCustomerService;
    }

    @FXML
    private void initialize() {
        customerIdField.setOnAction(this::handleFind);
        findButton.setOnAction(this::handleFind);
        errorLabel.setText("");
    }

    private void handleFind(Event e) {
        errorLabel.setText("");
        bonusCustomerService.findById(Integer.parseInt(customerIdField.getText())).whenComplete((bonusCustomer, throwable) -> {
            Platform.runLater(() -> {
                if (throwable != null) {
                    errorLabel.setText("Failed to find customer!");
                } else {
                    pageController.setCustomer(bonusCustomer);
                }
            });
        });
    }
}
