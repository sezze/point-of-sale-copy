package fi.abo.pvp20.grupp5.pointofsale.server.services;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.ISpecialOfferRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The service taking care of the servers special offers
 */
@Service
public class ServerSpecialOfferService {

    private final ISpecialOfferRepository offerRepository;

    /**
     * The ServerSpecialOfferService's constructor
     * @param offerRepository The server's special offer repository
     */
    @Autowired
    public ServerSpecialOfferService(ISpecialOfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Add a special offer to the database
     * @param specialOffer The special offer
     */
    public void addOffer(SpecialOffer specialOffer) {
        offerRepository.save(specialOffer);
    }

    /**
     * Remove a special offer from the database
     * @param name The name of the special offer
     */
    public void removeOffer(String name) {
        offerRepository.deleteById(name);
    }

    /**
     * Get a special offer from its name
     * @return A special offer from its name in the database
     */
    public Optional<SpecialOffer> getSpecialOffer(String name) {
        return offerRepository.findById(name);
    }

    /**
     * Get a list containing all special offers
     * @return A list of all special offers in the database
     */
    public List<SpecialOffer> getSpecialOffers() {
        ArrayList<SpecialOffer> specialOffers = new ArrayList<>();
        offerRepository.findAll().forEach(specialOffers::add);
        return specialOffers;
    }

    /**
     * Get all special offers that have a specific product in it
     * @param barcode The barcode of the product whose special offers you want
     * @return Returns all special offers that contain the specified product
     */
    public List<SpecialOffer> getByBarcode(String barcode) {
        return offerRepository.getAllByProducts(barcode);
    }

    /**
     * Get all special offers that have a specific keyword in it
     * @param keyword The keyword whose special offers you want
     * @return Returns all special offers that contain the specified keyword
     */
    public List<SpecialOffer> getByKeyword(String keyword) {
        return offerRepository.getAllByKeywords(keyword);
    }
}
