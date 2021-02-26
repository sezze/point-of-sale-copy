package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The server's special offer repository
 *
 * It stores SpecialOffers in an automatic JPA database
 */
@Repository("specialOfferRepository")
public interface ISpecialOfferRepository extends CrudRepository<SpecialOffer, String> {
    /**
     * Get all special offers that contain a certain product
     * @param barcode The barcode of the product to search by
     * @return Returns a list of all special offers that contain the product
     */
    List<SpecialOffer> getAllByProducts(String barcode);

    /**
     * Get all special offers that contain a certain keyword
     * @param keyword The keyword to search by
     * @return Returns a list of all special offers that contain the keyword
     */
    List<SpecialOffer> getAllByKeywords(String keyword);
}
