package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.ActiveSaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.ProductService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The controller for the catalog tab
 */
@Component
@FxmlView("catalog-tab.fxml")
public class CatalogTabController {

    private final ProductService productService;
    private final ActiveSaleService activeSaleService;

    @FXML
    private Button searchButton;
    @FXML
    private TextField productNameField;
    @FXML
    private ListView<Product> productListView;

    /**
     * The CatalogTabController's constructor
     */
    @Autowired
    public CatalogTabController(ProductService productService, ActiveSaleService activeSaleService) {
        this.productService = productService;
        this.activeSaleService = activeSaleService;
    }

    @FXML
    private void initialize() {
        searchButton.setOnAction(this::handleSearch);
        productNameField.setOnAction(this::handleSearch);
        productListView.setOnMouseClicked(this::handleSelection);
    }

    private void handleSearch(Event e) {
        String query = productNameField.getText().trim();
        if (query.length() > 0) {
            productService.searchByName(query).whenComplete((products, throwable) -> {
                Platform.runLater(() -> {
                    if (throwable == null) productListView.getItems().setAll(products);
                    else AppStatus.showError(searchButton.getScene(), "Failed to search for products", true);
                });
            });
        }
    }

    private void handleSelection(Event e) {
        Product product = productListView.getSelectionModel().getSelectedItem();
        if (product != null) {
            SaleItem item = activeSaleService.getActiveSale().addItemsToShoppingList(product, 1);
            activeSaleService.notifyChange(item, SaleEvent.Change.ITEM_ADDED);
        }
    }
}
