package fi.abo.pvp20.grupp5.pointofsale.shared.services;

import fi.abo.pvp20.grupp5.pointofsale.server.models.BonusEntry;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

/**
 * The client service taking care of bonus customers
 */
@Service
public class BonusCustomerService {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${point-of-sale.server.host}")
    private String host;

    /**
     * Find a bonus customer by ID in our repository
     * @param number The bonus customers ID
     * @return Returns a BonusCustomer
     */
    public CompletableFuture<BonusCustomer> findById(int number) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(host + "/customers/" + number, BonusCustomer.class));
    }

    /**
     * Find a bonus customer by a bonus card in our repository
     * @param number The bonus card's number
     * @param year The cards expiration year
     * @param month The cards expiration month
     * @return
     */
    public CompletableFuture<BonusCustomer> findByCard(String number, int year, int month) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(host + "/customers/card/" + number + "/" + year + "/" + month, BonusCustomer.class));
    }

    /**
     * Save bonus point for a bonus customer in our repository
     * @param customer The bonus customer
     */
    public CompletableFuture<Void> saveBonusPoints(BonusCustomer customer) {
        return CompletableFuture.runAsync(() ->
                restTemplate.postForObject(host + "/customers/saveBonusPoints",
                        new BonusEntry(customer.getCustomerNumber(), customer.getBonusPoints()), String.class));
    }

}
