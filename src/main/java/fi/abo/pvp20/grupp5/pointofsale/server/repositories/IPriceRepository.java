package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.server.models.PriceEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The price repository
 *
 * It stores PriceEntries in an automatic JPA database
 * Each PriceEntry contains a reference to a product (its barcode) and its price
 */
@Repository("priceRepository")
public interface IPriceRepository extends CrudRepository<PriceEntry, String> {
}
