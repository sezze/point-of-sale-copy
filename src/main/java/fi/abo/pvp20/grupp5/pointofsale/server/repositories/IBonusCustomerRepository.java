package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for the bonus customer repository
 */
public interface IBonusCustomerRepository {

    /**
     * Finds a bonus customer using customer number
     *
     * @return the bonus customer if it is found
     */
    CompletableFuture<BonusCustomer> findById(int customerNumber);

    /**
     * Finds a bonus customer using card number
     *
     * @return the bonus customer if it is found
     */
    CompletableFuture<BonusCustomer> findByCard(int cardNumber, int goodThruMonth, int goodThruYear);

}
