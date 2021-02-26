package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.components;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.ActiveSaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * The sale item GUI component
 */
public class SaleItemComponent extends HBox {
    private final Sale sale;
    private final SaleItem saleItem;
    private final TextField itemCountField;
    private final TextField discountField;
    private final ActiveSaleService activeSaleService;

    /**
     * The SaleItemComponent constructor
     */
    public SaleItemComponent(Sale sale, SaleItem saleItem, ActiveSaleService activeSaleService) {
        this.sale = sale;
        this.saleItem = saleItem;
        this.activeSaleService = activeSaleService;

        itemCountField = new TextField(Integer.toString(saleItem.getNumberOfItems()));
        itemCountField.setOnAction(this::updatePrice);
        itemCountField.setPrefWidth(60);

        double discount = saleItem.getSaleDiscountPercentage();
        discountField = new TextField(discount > 0 ? Double.toString(discount) : "");
        discountField.setPromptText("Discount %");
        discountField.setOnAction(this::updateDiscount);
        discountField.setPrefWidth(80);

        getChildren().addAll(
                new Label(saleItem.toString(sale.getBonusCustomer())) {{
                    HBox.setHgrow(this, Priority.ALWAYS);
                    getStyleClass().add("hgrow");
                }},
                discountField,
                itemCountField,
                new Button("Remove") {{
                    setOnAction(SaleItemComponent.this::removeProduct);
                }});

        setPadding(new Insets(8));
        setSpacing(8);
        setAlignment(Pos.CENTER_LEFT);
    }

    /**
     * Update the item count field
     */
    public void update() {
        itemCountField.setText(Integer.toString(saleItem.getNumberOfItems()));
    }

    /**
     * Update the price shown in the GUI and notify the active sale service
     */
    private void updatePrice(Event e) {
        try {
            int count = Integer.parseInt(itemCountField.getText());
            saleItem.setNumberOfItems(count);
            activeSaleService.notifyChange(saleItem, SaleEvent.Change.ITEM_CHANGED);
        } catch (NumberFormatException error) {
            AppStatus.showError(this.getScene(), "Bad item count input for " + saleItem.getProduct().getProductName(), true);
        }
    }

    /**
     * Remove a product from the shopping list and notify the active sale service
     */
    private void removeProduct(Event e) {
        sale.removeItemsFromShoppingList(saleItem, saleItem.getNumberOfItems());
        activeSaleService.notifyChange(saleItem, SaleEvent.Change.ITEM_REMOVED);
    }

    /**
     * Update the product's discount and show it in the GUI whilst notifying the change to the active sale service
     * @param actionEvent
     */
    private void updateDiscount(Event actionEvent) {
        try {
            double discount = Double.parseDouble(discountField.getText());
            // Set discount and clamp it to 0-100
            saleItem.setSaleDiscountPercentage(Math.max(Math.min(discount, 100), 0));
            activeSaleService.notifyChange(saleItem, SaleEvent.Change.ITEM_CHANGED);
        } catch (NumberFormatException e) {
            discountField.setText(Double.toString(saleItem.getSaleDiscountPercentage()));
        }
    }
}
