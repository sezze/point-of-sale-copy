package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.models.BonusEntry;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IBonusCustomerRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IBonusRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.RequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * The BonusCustomerAPI
 * <p>
 * Exposes bonus customer information to the clients
 */
@RestController
public class BonusCustomerAPI {
    private final IBonusCustomerRepository customerRepository;
    private final IBonusRepository bonusRepository;

    /**
     * The BonusCustomerAPI's constructor
     *
     * @param customerRepository The bonus customer repository that is used to connect to Initech's CustomerRegister
     * @param bonusRepository    The bonus point repository that contains all bonus customers' collected points
     */
    @Autowired
    public BonusCustomerAPI(IBonusCustomerRepository customerRepository, IBonusRepository bonusRepository) {
        this.customerRepository = customerRepository;
        this.bonusRepository = bonusRepository;
    }

    /**
     * Find a bonus customer by his/her id
     *
     * @param id The bonus customer's id
     * @return The the bonus customer or throw a RequestError that propagates up to the GUI
     */
    @GetMapping("/customers/{id}")
    CompletableFuture<BonusCustomer> findById(@PathVariable int id) {
        return customerRepository.findById(id).handle((bonusCustomer, throwable) -> {
            if (throwable != null) {
                throw new RequestError();
            } else {
                addBonusPoints(bonusCustomer);
                return bonusCustomer;
            }
        });
    }

    /**
     * Find a bonus customer by his/her card number
     *
     * @param number The card number
     * @param year   The expiration year on the card
     * @param month  The expiration month on the card
     * @return The the bonus customer or throw a RequestError that propagates up to the GUI
     */
    @GetMapping("/customers/card/{number}/{year}/{month}")
    CompletableFuture<BonusCustomer> findByCard(@PathVariable int number, @PathVariable int year, @PathVariable int month) {
        return customerRepository.findByCard(number, year, month).handle((bonusCustomer, throwable) -> {
            if (throwable != null) {
                throw new RequestError();
            } else {
                addBonusPoints(bonusCustomer);
                return bonusCustomer;
            }
        });
    }

    /**
     * Save bonus points in the bonus point database
     *
     * @param bonusEntry A BonusEntry containing the bonus customer and the amount of points
     */
    @PostMapping("/customers/saveBonusPoints")
    void saveBonusPoints(@RequestBody BonusEntry bonusEntry) {
        bonusRepository.save(bonusEntry);
    }

    /**
     * Gets a customer's bonus points from the database
     *
     * @param bonusCustomer
     */
    private void addBonusPoints(BonusCustomer bonusCustomer) {
        bonusRepository.findById(bonusCustomer.getCustomerNumber())
                .ifPresent(bonusEntry -> bonusCustomer.setBonusPoints(bonusEntry.getBonusPoints()));
    }
}
