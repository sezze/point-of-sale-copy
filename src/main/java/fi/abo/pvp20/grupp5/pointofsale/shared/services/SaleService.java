package fi.abo.pvp20.grupp5.pointofsale.shared.services;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.ProductSaleRecord;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The client service taking care of sales
 */
@Service
public class SaleService {
    private static final RestTemplate restTemplate = new RestTemplate();

    private final BonusCustomerService bonusCustomerService;

    @Value("${point-of-sale.server.host}")
    private String host;

    /**
     * The SaleService's constructor
     * @param bonusCustomerService A reference to a BonusCustomerService
     */
    @Autowired
    public SaleService(BonusCustomerService bonusCustomerService) {
        this.bonusCustomerService = bonusCustomerService;
    }

    /**
     * Get a list of all sales from the repository
     * @return Returns a list of all sales in the repository
     */
    public CompletableFuture<List<Sale>> getList() {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/sales", Sale[].class)));
    }

    /**
     * Get all sales made by a specific bonus customer
     * @param customerID The ID of the bonus customer
     * @return Returns a list of all sales made by the bonus customer
     */
    public CompletableFuture<List<Sale>> getCustomerSales(int customerID) {
        return CompletableFuture.supplyAsync(() ->
                Arrays.asList(restTemplate.getForObject(host + "/sales/bonus/" + customerID, Sale[].class)));
    }

    /**
     * Save a new sale to the repository
     * @param sale The sale that is to be saved
     */
    public CompletableFuture<Void> save(Sale sale) {
        return CompletableFuture.runAsync(() ->
                restTemplate.postForObject(host + "/sales", sale, Sale.class));
    }

    /**
     * Get all purchases made by a specific bonus customer, used for sales figures
     * @param customerID The ID of the bonus customer
     * @return Returns a map of of all purchases made by the bonus customer
     */
    public CompletableFuture<Map<String, ProductSaleRecord>> getCustomerPurchases(int customerID) {
        return getCustomerSales(customerID).handleAsync((sales, throwable) ->
            throwable == null
            ? toRecordMap(sales.stream())
            : null);
    }

    /**
     * Get a filtered list of sales
     */
    public CompletableFuture<Map<String, ProductSaleRecord>> getSaleItems(ChronoZonedDateTime<?> fromDate,
                                                                          ChronoZonedDateTime<?> toDate, boolean checkAge,
                                                                          int fromAge, int toAge, BonusCustomer.Sex sex) {
        return getList().handleAsync((sales, throwable) ->
            throwable == null
            ? toRecordMap(sales.stream()
                // Filter by date
                .filter(sale -> sale.getTimeOfPurchase() != null
                        && sale.getTimeOfPurchase().isBefore(toDate)
                        && sale.getTimeOfPurchase().isAfter(fromDate))
                // Get the bonus customer who made the sale
                .map(this::addBonusCustomer)
                .map(CompletableFuture::join)
                // Filter by sex
                .filter(sale -> (sale.getBonusCustomer() != null && sex != BonusCustomer.Sex.ANY
                        && sale.getBonusCustomer().getSex() == sex) || sex == BonusCustomer.Sex.ANY)
                // Filter by age
                .filter(sale -> {
                    if (!checkAge) return true;
                    if (sale.getBonusCustomer() == null) return false;
                    int age = Period.between(
                            sale.getBonusCustomer().getBirthDate().toLocalDate(), LocalDate.now()).getYears();
                    return age >= fromAge && age <= toAge;
                }))
            : null);
    }

    private Map<String, ProductSaleRecord> toRecordMap(Stream<Sale> sales) {
        return sales
            // Get sale items from sales
            .map(Sale::getShoppingList)
            .flatMap(List::stream)
            // Convert sale items to a record of the product and quantity
            .map(saleItem -> new ProductSaleRecord(saleItem.getProduct(), saleItem.getNumberOfItems()))
            // Merge all with the same type (barcode) into one map
            .collect(Collectors.toMap(ProductSaleRecord::getBarcode, Function.identity(), ProductSaleRecord::merge));
    }

    private CompletableFuture<Sale> addBonusCustomer(Sale sale) {
        int customerNo = sale.getBonusCustomerNumber();

        // If it is, get the bonus customer and add it to the sale
        return customerNo != 0 ? bonusCustomerService.findById(customerNo).handle((bonusCustomer, throwable) -> {
            if (throwable == null) sale.setBonusCustomer(bonusCustomer);
            return sale;
        }) : CompletableFuture.completedFuture(sale);
    }
}
