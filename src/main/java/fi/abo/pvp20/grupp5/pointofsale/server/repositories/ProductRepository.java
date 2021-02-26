package fi.abo.pvp20.grupp5.pointofsale.server.repositories;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The product repository
 * <p>
 * It connects to the Intertrode product registry and our special offer database
 */
@Repository("productRepository")
public class ProductRepository implements IProductRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IPriceRepository priceRepository;
    private final ISpecialOfferRepository offerRepository;
    private final DocumentBuilderFactory factory;
    @Value("${point-of-sale.catalog.host}")
    private String host;

    /**
     * The ProductRepository's constructor
     *
     * @param priceRepository The server's price repository
     * @param offerRepository The server's offer repository
     */
    @Autowired
    public ProductRepository(IPriceRepository priceRepository, ISpecialOfferRepository offerRepository) {
        this.priceRepository = priceRepository;
        this.offerRepository = offerRepository;
        factory = DocumentBuilderFactory.newInstance();
    }

    /**
     * Search for products by name
     *
     * @param query The full or partial name of the product(s) to search for
     * @return Returns all products that fits the query
     */
    @Override
    public CompletableFuture<List<Product>> searchByName(String query) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(host + "/rest/findByName/*" + query + "*");
                return mapToProducts(doc);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                logger.warn("Failed to search products");
                throw new RequestError();
            }
        });
    }

    /**
     * Search for products by keyword, it requires the full keyword and is case sensitive
     *
     * @param keyword The keyword of the product(s) to search for
     * @return Returns all products that have the keyword
     */
    @Override
    public CompletableFuture<List<Product>> getByKeyword(String keyword) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(host + "/rest/findByKeyword/" + keyword);
                return mapToProducts(doc);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                logger.warn("Failed to search products by keyword");
                throw new RequestError();
            }
        });
    }

    /**
     * Find a product by its barcode, it requires the full barcode and is case sensitive
     *
     * @param barcode The barcode of the product
     * @return Returns a product if it exists in the repository
     */
    @Override
    public CompletableFuture<Product> getByBarcode(String barcode) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(host + "/rest/findByBarCode/" + barcode);
                Product product = mapToProduct(doc.getChildNodes().item(0));
                if (product == null) return null;
                addPrice(product);
                addOffer(product);
                return product;
            } catch (SAXException | IOException | ParserConfigurationException e) {
                logger.debug("Failed to search products by barcode: " + barcode);
                throw new RequestError();
            }
        });
    }

    //TODO GIVE THIS A COMMENT THAT MAKES SENSE
    private Product mapToProduct(Node node) {
        String barCode = null;
        String name = null;
        double vat = 0;
        List<String> keywords = new LinkedList<>();

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            String nodeValue = childNode.getTextContent() == null ? childNode.getNodeValue() : childNode.getTextContent();
            try {
                switch (childNode.getNodeName()) {
                    case "barCode":
                        barCode = nodeValue;
                        break;
                    case "name":
                        name = nodeValue;
                        break;
                    case "vat":
                        vat = Double.parseDouble(nodeValue);
                        break;
                    case "keyword":
                        keywords.add(nodeValue);
                        break;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if (barCode == null || name == null || vat == 0) {
            return null;
        }
        return new Product(name, barCode, keywords, vat, 999999);
    }

    //TODO GIVE THIS A COMMENT THAT MAKES SENSE
    private List<Product> mapToProducts(Document doc) {
        NodeList childNodes = doc.getDocumentElement().getElementsByTagName("product");
        var products = IntStream.range(0, childNodes.getLength())
                .mapToObj(childNodes::item)
                .map(this::mapToProduct)
                .collect(Collectors.toList());
        products.forEach(this::addPrice);
        products.forEach(this::addOffer);
        return products;
    }

    /**
     * Connects a product to a price in the price database
     *
     * @param product The product to request a price for
     */
    private void addPrice(Product product) {
        priceRepository.findById(product.getBarcode()).ifPresent(priceEntry -> product.changeNetPrice(priceEntry.getNetPrice()));
    }

    /**
     * Go through all offers applicable to the product.
     * Filter out all non-applicable
     */
    private void addOffer(Product product) {
        var offers = offerRepository.getAllByProducts(product.getBarcode());

        product.getKeywords().stream()
                .map(offerRepository::getAllByKeywords) // Get all offers for specified keyword
                .flatMap(List::stream) // Turn List<List<>> to List<>
                .collect(Collectors.toCollection(() -> offers)); // Add to offers list

        product.setSpecialOffers(offers);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private static class RequestError extends RuntimeException {
    }
}
