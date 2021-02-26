package fi.abo.pvp20.grupp5.pointofsale.shared.services;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The client service taking care of products
 */
@Service
public class ProductService {
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${point-of-sale.server.host}")
    private String host;

    /**
     * Search for a product by name in our product repository
     * @param query The product name to search for
     * @return Returns a list of found products
     */
    public CompletableFuture<List<Product>> searchByName(String query) {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/products/name/" + query, Product[].class)));
    }

    /**
     * Update a products price in our price database
     * @param product The product with updated price
     */
    public CompletableFuture<Void> updatePrice(Product product) {
        return CompletableFuture.runAsync(() ->
                restTemplate.postForObject(host + "/products", product, Product.class));
    }

    /**
     * Get a product from our product repository by barcode
     * @param barcode The barcode of the barcode
     * @return Returns a product with the queried barcode if it exists
     */
    public CompletableFuture<Product> getByBarcode(String barcode) {
        return CompletableFuture.supplyAsync(() ->
                restTemplate.getForObject(host + "/products/" + barcode, Product.class));
    }
}
