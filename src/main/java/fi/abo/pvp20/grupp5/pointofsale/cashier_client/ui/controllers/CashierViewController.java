package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.CardReaderResponse;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.CashBoxStatus;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.models.PaymentStatus;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.ActiveSaleService;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.CardReaderService;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.services.CashBoxService;
import fi.abo.pvp20.grupp5.pointofsale.shared.events.SaleEvent;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCard;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.BonusCustomerService;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The controller for the cashier view
 */
@Component
@FxmlView("cashier-view.fxml")
public class CashierViewController {
    private final Logger logger = LoggerFactory.getLogger(CashierViewController.class);

    private final CashBoxService cashBoxService;
    private final CardReaderService cardReaderService;
    private final ActiveSaleService activeSaleService;
    private final BonusCustomerService customerService;

    @FXML
    private TabPane tabPane;
    @FXML
    private Button clearSaleButton;
    @FXML
    private Button shelfButton;

    // Status
    @FXML
    private Label bonusCardLabel;
    @FXML
    private Label itemCountLabel;
    @FXML
    private Label remainingLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label paidInCashLabel;
    @FXML
    private Label returnLabel;

    // Cash
    @FXML
    private TextField cashInput;
    @FXML
    private Label cashInputError;
    @FXML
    private Button addCashButton;
    @FXML
    private Button openCashBoxButton;
    @FXML
    private Button readCardButton;
    @FXML
    private Button abortButton;

    private Sale sale;

    /**
     * The CashierViewController's constructor
     */
    @Autowired
    public CashierViewController(CashBoxService cashBoxService, CardReaderService cardReaderService, ActiveSaleService activeSaleService, BonusCustomerService customerService) {
        this.cashBoxService = cashBoxService;
        this.cardReaderService = cardReaderService;
        this.activeSaleService = activeSaleService;
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        // Hide error labels at start
        hideError();

        // Map actions
        addCashButton.setOnAction(this::handleAddCashAction);
        cashInput.setOnAction(this::handleAddCashAction);
        openCashBoxButton.setOnAction(this::handleOpenCashBox);
        readCardButton.setOnAction(this::handleReadCard);
        clearSaleButton.setOnAction(this::handleClearing);
        shelfButton.setOnAction(this::handleShelving);
        abortButton.setOnAction(a -> cardReaderService.abort());

        sale = activeSaleService.getActiveSale();
        updateUi();
    }

    private void handleOpenCashBox(Event event) {
        hideError();
        cashBoxService.openCashBox();
    }

    private void handleReadCard(Event event) {
        hideError();
        var thread = new Thread(this::handlePayment);
        thread.start();
    }

    private void handlePayment() {
        try {
            cardReaderService.waitForPayment(sale.getRemainingPayment());

            boolean done = false;
            while (!done) {
                Thread.sleep(1000);
                PaymentStatus status = cardReaderService.getStatus();
                switch (status) {
                    case DONE:
                        done = true;
                        CardReaderResponse res = cardReaderService.getResult();
                        Platform.runLater(() -> handleCard(res));
                        cardReaderService.resetCardReader();
                    case IDLE:
                        done = true;
                        break;
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            showError("Card payment failed due to an unexpected error!");
        }
    }

    private void handleCard(CardReaderResponse res) {
        var bonusNumber = res.getBonusCardNumber();
        var bonusState = res.getBonusState();
        var paymentState = res.getPaymentState();
        int year = res.getGoodThruYear();
        int month = res.getGoodThruMonth();

        if (bonusState != null)
            switch (bonusState) {
                case ACCEPTED:
                    try {
                        BonusCustomer customer = customerService.findByCard(bonusNumber, year, month).get();
                        BonusCard bonusCard = customer.getBonusCards().stream()
                                .filter(bc -> bc.getCardNumber().equals(bonusNumber))
                                .findAny().orElse(null);
                        if (bonusCard != null && !bonusCard.getBlocked() && !bonusCard.getExpired()) {
                            sale.setBonusCustomer(customer);
                            sale.setBonusCustomerNumber(customer.getCustomerNumber());
                            activeSaleService.notifyChange(null, SaleEvent.Change.PAYMENT);
                        } else {
                            showError("Bonus card is blocked or expired!");
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        showError("Bonus card was not found in database!");
                    }
                    break;
                case UNSUPPORTED_CARD:
                    showError("Unsupported bonus card used!");
                    break;
            }

        if (paymentState != null)
            switch (paymentState) {
                case ACCEPTED:
                    sale.addPayment(sale.getRemainingPayment());
                    activeSaleService.notifyChange(null, SaleEvent.Change.PAYMENT);
                    break;
                case INSUFFICIENT_FUNDS:
                    showError("Insufficient funds! Payment failed.");
                    break;
                case INVALID_PIN:
                    showError("Invalid pin code provided! Payment failed.");
                    break;
                case NETWORK_ERROR:
                    showError("A network error occurred! Payment failed.");
                    break;
                case UNSUPPORTED_CARD:
                    showError("An unsupported card was used! Payment failed.");
                    break;
            }
    }

    private void handleAddCashAction(ActionEvent a) {
        try {
            // Hide any previous error message when starting new action
            hideError();
            int amount = CurrencyUtils.Parse(cashInput.getText());
            if (cashBoxService.getStatus() == CashBoxStatus.OPEN) {
                sale.addPayment(amount);
                activeSaleService.notifyChange(null, SaleEvent.Change.PAYMENT);
            } else {
                showError("Cash box is closed!");
            }
            cashInput.clear();
        } catch (IllegalArgumentException e) {
            showError("Invalid cash amount!");
        }
    }

    private void handleClearing(Event e) {
        activeSaleService.resetSale();
    }

    private void handleShelving(Event e) {
        if (activeSaleService.hasShelvedSale()) {
            activeSaleService.unshelveSale();
        } else {
            activeSaleService.shelveSale();
        }
    }

    private void updateUi() {
        int count = sale.getShoppingList().size();
        bonusCardLabel.setText(sale.getBonusCustomer() == null ?
                "Not a bonus customer" : "Bonus customer: " + sale.getBonusCustomer().getFirstName());
        itemCountLabel.setText(count + " item" + (count != 1 ? "s" : ""));
        remainingLabel.setText("Remaining: " + CurrencyUtils.toString(sale.getRemainingPayment()));
        totalLabel.setText("Total: " + CurrencyUtils.toString(sale.computeTotalCost()));
        paidInCashLabel.setText("Paid: " + CurrencyUtils.toString(sale.getTotalPaid()));
        returnLabel.setText("Return: " + CurrencyUtils.toString(sale.getReturnAmount()));
        shelfButton.setText(activeSaleService.hasShelvedSale() ? "Unshelve" : "Shelve");
    }

    private void showError(String msg) {
        Platform.runLater(() -> {
            cashInputError.setText(msg);
            cashInputError.setVisible(true);
        });
    }

    private void hideError() {
        Platform.runLater(() -> cashInputError.setVisible(false));
    }

    /**
     * Handle a sale update
     */
    @EventListener
    public void handleSaleUpdate(SaleEvent event) {
        sale = event.sale;
        Platform.runLater(this::updateUi);

        // If an item was added, change to the first tab to visibly show the change
        // Used when items are added from the product catalog
        if (event.change == SaleEvent.Change.ITEM_ADDED) {
            tabPane.getSelectionModel().select(0);
        }
    }
}
