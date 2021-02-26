package fi.abo.pvp20.grupp5.pointofsale.shared.services;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The client service taking care of special offers
 */
@Service
public class SpecialOfferService {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${point-of-sale.server.host}")
    private String host;

    /**
     * Get all special offers in our repository
     * @return Returns a list of all special offers
     */
    public CompletableFuture<List<SpecialOffer>> getSpecialOffers() {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/offers", SpecialOffer[].class)));
    }

    /**
     * Returns all special offers that contain a specific product from our repository
     * @param barcode The barcode of the product
     * @return Returns all special offers that contain the specified product
     */
    public CompletableFuture<List<SpecialOffer>> getOffersByBarcode(String barcode) {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/offers/barcode/" + barcode, SpecialOffer[].class)));
    }

    /**
     * Retruns all special offers that contain a specific keyword from our repository
     * @param keyword The keyword to query for
     * @return Returns all special offers that contain the specified keyword
     */
    public CompletableFuture<List<SpecialOffer>> getOffersByKeyword(String keyword) {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/offers/keyword/" + keyword, SpecialOffer[].class)));
    }

    /**
     * Add a special offer to our repository
     * @param specialOffer The special offer to add
     * @return Returns the special offer that was just added
     */
    public CompletableFuture<SpecialOffer> addSpecialOffer(SpecialOffer specialOffer) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.postForObject(host + "/offers", specialOffer, SpecialOffer.class));
    }

    /**
     * Remove a special offer from our repository
     * @param name The name of the special offer to remove
     */
    public CompletableFuture<Void> removeSpecialOffer(String name) {
        return CompletableFuture.supplyAsync(() -> {
            restTemplate.delete(host + "/offers/" + name);
            return null;
        });
    }

}
