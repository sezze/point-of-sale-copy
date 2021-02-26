package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.services.ServerSpecialOfferService;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * The SpecialOfferAPI
 *
 * Exposes special offer information to the clients
 */
@RestController
public class SpecialOfferAPI {

    private final ServerSpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferAPI(ServerSpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    /**
     * Get all special offers
     * @return A list of all special offers in the database
     */
    @GetMapping("/offers")
    List<SpecialOffer> getSpecialOffers() {
        return specialOfferService.getSpecialOffers();
    }

    /**
     * Get a special offer from its name
     * @param name The name of the special offer
     * @return Any found offer with the name
     */
    @GetMapping("/offers/{name}")
    Optional<SpecialOffer> getByName(@PathVariable String name) {
        return specialOfferService.getSpecialOffer(name);
    }

    /**
     * Get a list of special offers that are associated with a barcode
     * @return Returns a list of any found offers associated with the barcode
     */
    @GetMapping("/offers/barcode/{barcode}")
    List<SpecialOffer> getByBarcode(@PathVariable String barcode) {
        return specialOfferService.getByBarcode(barcode);
    }

    /**
     * Get a list of special offers that are associated with a keyword
     * @return Returns a list of any found offers associated with the keyword
     */
    @GetMapping("/offers/keyword/{keyword}")
    List<SpecialOffer> getByKeyword(@PathVariable String keyword) {
        return specialOfferService.getByKeyword(keyword);
    }

    /**
     * Add a special offer to the database
     * @param specialOffer The special offer to add to the database
     * @return The special offer that was added
     */
    @PostMapping("/offers")
    SpecialOffer addSpecialOffer(@RequestBody SpecialOffer specialOffer) {
        specialOfferService.addOffer(specialOffer);
        return specialOffer;
    }

    /**
     * Remove a special offer from the database
     * @param name The specific name of the special offer
     */
    @DeleteMapping("/offers/{name}")
    void removeSpecialOffer(@PathVariable String name) {
        specialOfferService.removeOffer(name);
    }

}
