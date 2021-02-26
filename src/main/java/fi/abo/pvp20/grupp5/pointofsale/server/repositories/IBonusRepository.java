package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.server.models.BonusEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The bonus point repository
 *
 * It stores BonusEntries in an automatic JPA database
 * Each BonusEntry contains a reference to a bonus customer and how many bonus points he/she has
 */
@Repository("bonusRepository")
public interface IBonusRepository extends CrudRepository<BonusEntry, Integer> {
}
