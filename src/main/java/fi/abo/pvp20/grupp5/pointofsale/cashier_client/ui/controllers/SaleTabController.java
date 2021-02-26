package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.ActiveSaleService;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.components.SaleItemComponent;
import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.ProductService;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * The controller for the main sale tab
 */
@Component
@FxmlView("sale-tab.fxml")
public class SaleTabController {
    private final ActiveSaleService activeSaleService;
    private final ProductService productService;
    @FXML
    private TextField barcodeInput;
    @FXML
    private Button addByBarcodeButton;
    @FXML
    private Label barcodeInputError;
    @FXML
    private Pane saleItemList;

    /**
     * The SaleTabController's constructor
     */
    @Autowired
    public SaleTabController(ActiveSaleService activeSaleService, ProductService productService) {
        this.activeSaleService = activeSaleService;
        this.productService = productService;
    }

    @FXML
    private void initialize() {
        // Hide error labels at start
        barcodeInputError.setVisible(false);

        addByBarcodeButton.setOnAction(this::handleAddProduct);
        barcodeInput.setOnAction(this::handleAddProduct);
    }

    private void handleAddProduct(Event e) {
        barcodeInputError.setVisible(false);
        String barcode = barcodeInput.getText().trim();
        if (barcode.length() == 0) return;
        barcodeInput.clear();

        productService.getByBarcode(barcode).whenComplete((product, t) -> {
            if (t == null) {
                SaleItem item = activeSaleService.getActiveSale().addItemsToShoppingList(product, 1);
                activeSaleService.notifyChange(item, item.getNumberOfItems() == 1 ? SaleEvent.Change.ITEM_ADDED : SaleEvent.Change.ITEM_CHANGED);
            } else {
                barcodeInputError.setVisible(true);
            }
        });
    }

    /**
     * Update the shopping list and put focus on the barcode input box
     */
    public void update(Sale sale) {
        saleItemList.getChildren().clear();
        saleItemList.getChildren().addAll(sale.getShoppingList().stream()
                .map(s -> new SaleItemComponent(sale, s, activeSaleService))
                .collect(Collectors.toList()));

        barcodeInput.requestFocus();
    }

    /**
     * Handle an update to a sale
     * @param event A SaleEvent that describes the changes made to a sale
     */
    @EventListener
    public void handleSaleUpdate(SaleEvent event) {
        Platform.runLater(() -> update(event.sale));
    }
}
