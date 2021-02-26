package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IProductRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.services.ProductService;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.RequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Product converter class used by JPA, used to convert a barcode to a product or a product to a barcode
 */
@Component
public class ProductConverter implements AttributeConverter<Product, String> {
    private static IProductRepository repository;
    private static ProductService service;

    @Autowired
    public ProductConverter(@Qualifier("productRepository") Optional<IProductRepository> repository, Optional<ProductService> service) {
        ProductConverter.repository = repository.orElse(null);
        ProductConverter.service = service.orElse(null);
    }

    @Override
    public String convertToDatabaseColumn(Product product) {
        return product.getBarcode();
    }

    @Override
    public Product convertToEntityAttribute(String s) {
        try {
            return repository != null ? repository.getByBarcode(s).get() : service.getByBarcode(s).get();
        } catch (InterruptedException | ExecutionException | RequestError e) {
            return null;
        }
    }
}
