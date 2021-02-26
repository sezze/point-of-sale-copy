package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.models.PriceEntry;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IPriceRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IProductRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * The ProductAPI
 *
 * Exposes product information to the clients
 */
@RestController
public class ProductAPI {

    private final IProductRepository productRepository;
    private final IPriceRepository priceRepository;

    /**
     * The ProductAPI's constructor
     * @param productRepository The product repository that connects to Intertrode's product repository
     * @param priceRepository The price repository that connects to the price database
     */
    @Autowired
    public ProductAPI(@Qualifier("productRepository") IProductRepository productRepository, IPriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Search for a products by a string, accepts a complete or partial name
     * @param name The complete or partial name of a product
     * @return A list of found products with names that match the search term
     */
    @GetMapping("/products/name/{name}")
    CompletableFuture<List<Product>> searchByName(@PathVariable String name) {
        return productRepository.searchByName(name);
    }

    /**
     * Find a product by its barcode, requires the full barcode
     * @param barcode The products complete barcode
     * @return Returns a product if it's found
     */
    @GetMapping("/products/{barcode}")
    CompletableFuture<Product> getByBarcode(@PathVariable String barcode) {
        return productRepository.getByBarcode(barcode);
    }

    /**
     * Updates a products price in the price repository
     * @param product The product whose price should be updated in the database
     * @return Returns the product with the new price
     */
    @PostMapping("/products")
    Product updateProductPrice(@RequestBody Product product) {
        priceRepository.save(new PriceEntry(product.getBarcode(), product.getNetPrice()));
        return product;
    }

}
