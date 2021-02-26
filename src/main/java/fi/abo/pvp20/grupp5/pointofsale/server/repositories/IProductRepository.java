package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for the product repository
 */
public interface IProductRepository {

    /**
     * Search by name in the product repository
     * @param query Search query
     * @return
     */
    CompletableFuture<List<Product>> searchByName(String query);

    /**
     * Get all items that includes keyword in the product repository
     * @param keyword The whole keyword
     * @return
     */
    CompletableFuture<List<Product>> getByKeyword(String keyword);

    /**
     * Search for a product by its barcode in the product repository
     * @param barcode The product's barcode
     * @return Returns a product if it's found in the repository
     */
    CompletableFuture<Product> getByBarcode(String barcode);
}
