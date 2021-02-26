package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.CustomerDisplayService;
import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * The controller for the customer facing display view
 */
@Component
@FxmlView("customer-view.fxml")
public class CustomerViewController implements ApplicationListener<CustomerDisplayService.DisplayTextChangedEvent> {

    @FXML
    private Label label;

    /**
     * Update the text that is shown on the screen
     */
    @Override
    public void onApplicationEvent(CustomerDisplayService.DisplayTextChangedEvent e) {
        label.setText(e.getText());
    }

    /**
     * Handle a change in the sale and show what is happening to the customer
     */
    @EventListener
    public void handleSaleChange(SaleEvent event) {
        Platform.runLater(() -> {
            Sale sale = event.sale;
            SaleItem item = event.item;
            switch (event.change) {
                case ITEM_ADDED:
                case ITEM_CHANGED:
                    label.setText(item.toString(sale.getBonusCustomer()) + "\nTotal: " + CurrencyUtils.toString(sale.computeTotalCost()));
                    break;
                case ITEM_REMOVED:
                    label.setText("Removed " + item.getProduct()
                            + "\nTotal: " + CurrencyUtils.toString(sale.computeTotalCost()));
                    break;
                case PAYMENT:
                    // If the items have been paid for
                    if (sale.getRemainingPayment() == 0) {
                        int returnAmount = sale.getReturnAmount();
                        label.setText("Thank you for your purchase!"
                                + "\nTotal: " + CurrencyUtils.toString(sale.computeTotalCost())
                                + (returnAmount > 0 ? "\nReturn: " + CurrencyUtils.toString(returnAmount) : ""));
                    } else {
                        label.setText("Total paid: " + CurrencyUtils.toString(sale.getTotalPaid())
                                + "\nRemaining: " + CurrencyUtils.toString(sale.getRemainingPayment()));
                    }
                    break;
            }
        });
    }
}
