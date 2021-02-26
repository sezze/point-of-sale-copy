package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.RequestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

/**
 * The bonus customer repository
 * <p>
 * It connects to the Initech bonus customer registry and our bonus point database
 */
@Repository("bonusCustomerRepository")
public class BonusCustomerRepository implements IBonusCustomerRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IBonusRepository bonusRepository;

    @Value("${point-of-sale.bonus-customer.host}")
    private String host;

    /**
     * The BonusCustomerRepository's constructor
     *
     * @param bonusRepository The bonus point repository
     */
    @Autowired
    public BonusCustomerRepository(IBonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    /**
     * Finds a bonus customer by ID from the Initech registry
     *
     * @param customerNumber The customer ID
     * @return Returns a bonus customer if he/she exists
     * @throws RequestError Throws an error if the bonus customer is not found, the error propagates to the GUI
     */
    @Override
    public CompletableFuture<BonusCustomer> findById(int customerNumber) {
        return get(host + "/rest/findByCustomerNo/" + customerNumber);
    }

    /**
     * Finds a bonus customer by bonus card from the Initech registry
     *
     * @param number The bonus card number
     * @param year   The bonus cards expiration year
     * @param month  The bonus cards expiration month
     * @return Returns a bonus customer if he/she exists
     * @throws RequestError Throws an error if a bonus customer is not found, the error propagates to the GUI
     */
    @Override
    public CompletableFuture<BonusCustomer> findByCard(int number, int year, int month) {
        return get(host + "/rest/findByBonusCard/" + number + "/" + year + "/" + month);
    }

    /**
     * Get a customer from the Initech registry, converts the recieved XML to a BonusCustomer
     *
     * @param urlString The URL to the Initech registry
     * @return Returns a bonus customer if found
     */
    private CompletableFuture<BonusCustomer> get(String urlString) throws RequestError {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(urlString);
                XmlMapper xmlMapper = new XmlMapper();
                BonusCustomer bonusCustomer = xmlMapper.readValue(url, BonusCustomer.class);
                addBonusPoints(bonusCustomer);
                return bonusCustomer;
            } catch (IOException e) {
                throw new RequestError();
            }
        });
    }

    /**
     * Adds the recorded bonus points from the database if present
     */
    private void addBonusPoints(BonusCustomer customer) {
        bonusRepository.findById(customer.getCustomerNumber())
                .ifPresent(bonusEntry -> customer.setBonusPoints(bonusEntry.getBonusPoints()));
    }
}
