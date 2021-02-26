package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The server's sale repository
 *
 * It stores Sales in an automatic JPA database
 */
@Repository("serverSaleRepository")
public interface IServerSaleRepository extends CrudRepository<Sale, UUID> {

    /**
     * Get all sales made to a certain bonus customer
     * @param bonusCustomerNumber The bonus customer's number
     * @return Returns a list of all the bonus customer's purchases
     */
    List<Sale> findAllByBonusCustomerNumber(int bonusCustomerNumber);

    /**
     * Get all sales made in a certain time period
     * @return Returns a list of all sales made in the supplied time frame
     */
    List<Sale> findAllByTimeOfPurchaseIsBetween(ZonedDateTime from, ZonedDateTime to);
}
