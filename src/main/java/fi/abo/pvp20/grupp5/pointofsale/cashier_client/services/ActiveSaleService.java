package fi.abo.pvp20.grupp5.pointofsale.cashier_client.services;

import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Receipt;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.BonusCustomerService;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.SaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * The service taking care of the active sale
 */
@Service
public class ActiveSaleService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CashBoxService cashBoxService;
    private final SaleService saleService;
    private final BonusCustomerService customerService;

    private Sale activeSale = new Sale();
    private Sale shelvedSale;

    /**
     * The ActiveSaleService's constructor
     */
    @Autowired
    public ActiveSaleService(ApplicationEventPublisher publisher, CashBoxService cashBoxService, SaleService saleService, BonusCustomerService customerService) {
        this.applicationEventPublisher = publisher;
        this.cashBoxService = cashBoxService;
        this.saleService = saleService;
        this.customerService = customerService;
    }

    /**
     * Notifies the UI about any changes to the sale so they can update
     */
    public void notifyChange(SaleItem item, SaleEvent.Change change) {
        applicationEventPublisher.publishEvent(new SaleEvent(activeSale, item, change));
    }

    /**
     * Moves the current sale to the shelf
     */
    public void shelveSale() {
        if (shelvedSale == null) {
            shelvedSale = activeSale;
            resetSale();
        } else {
            throw new RuntimeException("There is already a shelved sale!");
        }
    }

    /**
     * Restore the shelved item, removes the current sale
     */
    public void unshelveSale() {
        if (shelvedSale != null) {
            activeSale = shelvedSale;
            shelvedSale = null;
            notifyChange(null, SaleEvent.Change.NEW_SALE);
        } else {
            throw new RuntimeException("Can't unshelve a non-existent sale!");
        }
    }

    /**
     * Get the active sale
     *
     * @return Returns the active sale
     */
    public Sale getActiveSale() {
        return activeSale;
    }

    /**
     * Returns the shelved sale
     *
     * @return The shelved sale if it exists
     */
    public boolean hasShelvedSale() {
        return shelvedSale != null;
    }

    /**
     * Checks after a payment if the sale is complete.
     */
    @EventListener
    public void handleCompletedPayment(SaleEvent event) {
        Sale sale = event.sale;
        if (event.change == SaleEvent.Change.PAYMENT
                && sale.getRemainingPayment() == 0 && sale.getShoppingList().size() > 0) {
            sale.setSaleCompleted();
            sale.setTimeOfPurchase(ZonedDateTime.now());

            // Open the cash box if the cashier has to return cash
            if (sale.getReturnAmount() > 0) cashBoxService.openCashBox();

            saleService.save(sale).whenComplete((unused, throwable) -> {
                if (throwable == null) {
                    // Save sale & bonus points
                    BonusCustomer customer = sale.getBonusCustomer();
                    if (customer != null) {
                        customer.setBonusPoints(customer.getBonusPoints() + (sale.computeTotalCost() / 100));
                        customerService.saveBonusPoints(customer);
                    }

                    Platform.runLater(() -> askForReceipt(sale));
                    resetSale();
                } else {
                    throwable.printStackTrace();
                }
            });
        }
    }

    /**
     * Reset the sale, clearing out the shopping list, any ammounts and any bonus customer association
     */
    public void resetSale() {
        // Reset to new sale
        activeSale = new Sale();
        notifyChange(null, SaleEvent.Change.NEW_SALE);
    }

    /**
     * Ask the cashier for a receipt with an alert
     */
    private void askForReceipt(Sale sale) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sale complete!");
        alert.setHeaderText("Return amount: " + CurrencyUtils.toString(sale.getReturnAmount()));
        alert.setContentText("Would you like to print a receipt?");
        var printButton = new ButtonType("Print");
        alert.getButtonTypes().setAll(printButton, new ButtonType("Do not print"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == printButton) {
            Receipt.printSale(sale);
        }
    }
}
