package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IPriceRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.ProductRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
class ProductAPITest {

    // Create example classes
    private final Product cocaCola = new Product("Coca-Cola 1.5l", "1234", Collections.emptyList(), 14, 150);
    private final Product pepsiMax = new Product("Pepsi Max 1.5l", "5678", Collections.emptyList(), 14, 140);
    private final List<Product> cocaColaList = Collections.singletonList(cocaCola);
    private final List<Product> pepsiMaxList = Collections.singletonList(pepsiMax);
    private final List<Product> products = Arrays.asList(cocaCola, pepsiMax);
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private IPriceRepository priceRepository;
    private ProductAPI productAPI;

    @BeforeEach
    void init() {
        productAPI = new ProductAPI(productRepository, priceRepository);


    }

    /**
     * Test if searchByName works
     */
    @Test
    void searchByName() throws ExecutionException, InterruptedException {
        // Mock results
        when(productRepository.searchByName("1.5l")).thenReturn(CompletableFuture.completedFuture(products));
        when(productRepository.searchByName("cola")).thenReturn(CompletableFuture.completedFuture(cocaColaList));
        when(productRepository.searchByName("pepsi")).thenReturn(CompletableFuture.completedFuture(pepsiMaxList));

        var products = productAPI.searchByName("1.5l").get();
        assertEquals(2, products.size());
        assertTrue(products.contains(cocaCola));
        assertTrue(products.contains(pepsiMax));
    }

    /**
     * Test if getByBarcode works
     */
    @Test
    void getByBarcode() throws ExecutionException, InterruptedException {
        // Mock results
        when(productRepository.getByBarcode("1234")).thenReturn(CompletableFuture.completedFuture(cocaCola));
        when(productRepository.getByBarcode("5678")).thenReturn(CompletableFuture.completedFuture(pepsiMax));

        var products = new ArrayList<Product>();
        products.add(productAPI.getByBarcode("1234").get());
        products.add(productAPI.getByBarcode("5678").get());
        assertEquals(2, products.size());
        assertTrue(products.contains(cocaCola));
        assertTrue(products.contains(pepsiMax));
    }

    /**
     * Test if updateProductPrice works
     */
    @Test
    void updateProductPrice() throws ExecutionException, InterruptedException {
        // Mock results
        when(productRepository.getByBarcode("1234")).thenReturn(CompletableFuture.completedFuture(cocaCola));

        assertEquals(productAPI.updateProductPrice(cocaCola), cocaCola);
        assertEquals(150, priceRepository.findById("1234").get().getNetPrice());

        cocaCola.changeNetPrice(170);
        assertEquals(productAPI.updateProductPrice(cocaCola), cocaCola);
        assertEquals(170, priceRepository.findById("1234").get().getNetPrice());
    }
}