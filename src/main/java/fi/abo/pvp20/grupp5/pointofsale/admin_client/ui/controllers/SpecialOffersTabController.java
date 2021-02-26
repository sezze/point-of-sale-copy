package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.SpecialOfferService;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.components.AppStatus;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("special-offers-tab.fxml")
public class SpecialOffersTabController {
    private final SpecialOfferService specialOfferService;

    @FXML
    private Button newOfferButton;
    @FXML
    private ListView<SpecialOffer> specialOfferListView;
    @FXML
    private SpecialOfferPageComponentController pageController;

    /**
     * The SpecialOffersTabController's constructor
     */
    @Autowired
    public SpecialOffersTabController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    @FXML
    private void initialize() {
        newOfferButton.setOnAction(this::handleNewOffer);
        specialOfferListView.setOnMouseClicked(this::handleOfferSelection);
        pageController.setOfferListChanged(this::updateList);

        // Populate list of special offers
        updateList();
    }

    private void updateList() {
        specialOfferService.getSpecialOffers().whenComplete((specialOffers, throwable) -> {
            Platform.runLater(() -> {
                if (throwable == null)
                    specialOfferListView.getItems().setAll(specialOffers);
                else AppStatus.showError(specialOfferListView.getScene(),
                        "Failed to get special offers! You probably started this program before the server.", true);
            });
        });
    }

    private void handleNewOffer(Event e) {
        pageController.reset();
    }

    private void handleOfferSelection(Event e) {
        SpecialOffer offer = specialOfferListView.getSelectionModel().getSelectedItem();
        if (offer != null) pageController.setSpecialOffer(offer);
    }
}
