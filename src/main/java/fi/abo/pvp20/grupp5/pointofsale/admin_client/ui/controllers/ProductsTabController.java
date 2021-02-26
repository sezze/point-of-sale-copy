package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.ProductService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView("products-tab.fxml")
public class ProductsTabController {
    private final ProductService productService;

    @FXML
    private TextField nameField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Product> itemList;
    @FXML
    private ProductPageComponentController productPageController;

    /**
     * The ProductsTabController's constructor
     */
    @Autowired
    public ProductsTabController(ProductService productService) {
        this.productService = productService;
    }

    @FXML
    private void initialize() {
        nameField.setOnAction(this::handleSearch);
        searchButton.setOnAction(this::handleSearch);
        itemList.setOnMouseClicked(this::handleSelection);
    }

    private void handleSearch(Event e) {
        String query = nameField.getText().toLowerCase().trim();
        if (query.length() == 0) return;

        productService.searchByName(query).whenComplete((productList, throwable) -> {
            if (throwable == null) {
                Platform.runLater(() -> setListItems(productList));
            } else {
                AppStatus.showError(this.itemList.getScene(), "Failed to search for products", true);
            }
        });
    }

    private void handleSelection(Event e) {
        Product p = itemList.getSelectionModel().getSelectedItem();
        if (p != null) productPageController.setProduct(p);
    }

    private void setListItems(List<Product> products) {
        if (products != null)
            itemList.setItems(FXCollections.observableList(products));
    }
}
