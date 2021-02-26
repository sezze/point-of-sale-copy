package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.SpecialOfferService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
@FxmlView("special-offer-page-component.fxml")
public class SpecialOfferPageComponentController {

    private final SpecialOfferService specialOfferService;

    @FXML
    private TextField nameField;
    @FXML
    private TextField discountField;
    @FXML
    private TextArea barcodeTextArea;
    @FXML
    private TextArea keywordTextArea;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private CheckBox bonusOnlyCheckBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label formErrorLabel;

    private Runnable onOfferListChanged = () -> {
    };
    private SpecialOffer offer;

    /**
     * The SpecialOfferPageComponentController's constructor
     */
    @Autowired
    public SpecialOfferPageComponentController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    @FXML
    private void initialize() {
        saveButton.setOnAction(this::handleSave);
        deleteButton.setOnAction(this::handleDeletion);
        reset();
    }

    /**
     * Set the current offer to show in the special offer page
     * @param offer The offer to shown visually
     */
    public void setSpecialOffer(SpecialOffer offer) {
        this.offer = offer;
        Platform.runLater(this::updateUi);
        hideError();
    }

    /**
     * Clear out all fields in the special offer page
     */
    public void reset() {
        setSpecialOffer(new SpecialOffer());
    }

    private void updateUi() {
        // If the offer already has a name it is a pre-existing offer
        // and you can not modify the name (database id!) after creation
        nameField.setDisable(offer.getName().length() != 0);

        nameField.setText(offer.getName());
        discountField.setText(Double.toString(offer.getDiscountPercentage()));
        barcodeTextArea.setText(String.join("\n", offer.getProducts()));
        keywordTextArea.setText(String.join("\n", offer.getKeywords()));
        startDatePicker.setValue(offer.getStartDate());
        endDatePicker.setValue(offer.getEndDate());
        bonusOnlyCheckBox.setSelected(offer.isForBonusCustomersOnly());
    }

    private void handleSave(Event e) {
        if (!validateForm()) return;

        double discount;
        try {
            discount = Double.parseDouble(discountField.getText());
        } catch (NumberFormatException ignored) {
            showError("Invalid discount percentage! Try a number like 25 for 25.0% off");
            return;
        }

        hideError();
        String name = nameField.getText().trim();
        offer.setName(name);
        offer.setDiscountPercentage(discount);
        offer.setProducts(new HashSet<>(Arrays.asList(barcodeTextArea.getText().split("\\n"))));
        offer.setKeywords(new HashSet<>(Arrays.asList(keywordTextArea.getText().split("\\n"))));
        offer.setStartDate(startDatePicker.getValue());
        offer.setEndDate(endDatePicker.getValue());
        offer.setForBonusCustomersOnly(bonusOnlyCheckBox.isSelected());

        specialOfferService.addSpecialOffer(offer).whenComplete((specialOffer, throwable) -> {
            if (throwable == null) {
                Platform.runLater(this::reset);
                onOfferListChanged.run();
                AppStatus.showInfo(nameField.getScene(), name + " successfully saved!", true);
            } else {
                showError("Failed to save special offer!");
                throwable.printStackTrace();
            }
        });
    }

    private boolean validateForm() {
        if (nameField.getText().trim().length() == 0) {
            showError("You must provide a name!");
            return false;
        }
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showError("The end date must come after the start date!");
            return false;
        }
        return true;
    }

    private void handleDeletion(Event e) {
        String name = offer.getName();
        if (name != null && name.length() > 0) {
            specialOfferService.removeSpecialOffer(name).whenComplete((unused, throwable) -> {
                if (throwable == null) {
                    Platform.runLater(this::reset);
                    onOfferListChanged.run();
                    AppStatus.showInfo(nameField.getScene(), name + " successfully removed!", true);
                } else {
                    showError("Failed to remove offer!");
                    throwable.printStackTrace();
                }
            });
        } else {
            showError("This offer was never saved so it can not be removed. Click 'New Special Offer' to reset fields.");
        }
    }

    private void showError(String msg) {
        Platform.runLater(() -> {
            formErrorLabel.setText(msg);
            formErrorLabel.setVisible(true);
        });
    }

    private void hideError() {
        Platform.runLater(() -> formErrorLabel.setVisible(false));
    }

    public void setOfferListChanged(Runnable onOfferListChanged) {
        this.onOfferListChanged = onOfferListChanged;
    }
}
