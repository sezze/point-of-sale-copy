package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.services.ServerSaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The SaleAPI
 * <p>
 * Exposes sale information to the clients
 */
@RestController
public class SaleAPI {

    private final ServerSaleService serverSaleService;

    /**
     * The SaleAPI's constructor
     *
     * @param serverSaleService The server's sale service that takes care of all sales
     */
    @Autowired
    public SaleAPI(ServerSaleService serverSaleService) {
        this.serverSaleService = serverSaleService;
    }

    /**
     * Get all sales
     *
     * @return A list of all sales in the database
     */
    @GetMapping("/sales")
    List<Sale> getSales() {
        return serverSaleService.getSales();
    }

    /**
     * Get a sale from its UUID
     *
     * @param uuid The sales UUID
     * @return Any found sale with the UUID
     */
    @GetMapping("/sales/{uuid}")
    Sale getSale(@PathVariable UUID uuid) {
        Optional<Sale> sale = serverSaleService.findSale(uuid);
        return sale.orElse(null);
    }

    /**
     * Get all sales from a bonus customer
     *
     * @param bonusCustomerNumber The bonus customers number
     * @return All sales associated with a bonus customer
     */
    @GetMapping("/sales/bonus/{bonusCustomerNumber}")
    List<Sale> getSaleByBonusCustomerNumber(@PathVariable int bonusCustomerNumber) {
        return serverSaleService.getSalesByBonusCustomerNumber(bonusCustomerNumber);
    }

    /**
     * Add a sale to the database
     *
     * @param sale The sale to add
     * @return The sale that was added
     */
    @PostMapping("/sales")
    Sale addSale(@RequestBody Sale sale) {
        sale.setUuid(UUID.randomUUID());
        sale.setSaleCompleted();
        serverSaleService.addSale(sale);
        return sale;
    }

    /**
     * Remove a sale from the database by its UUID
     *
     * @param uuid The sale's UUID
     */
    @DeleteMapping("/sales/{uuid}")
    void removeSale(@PathVariable UUID uuid) {
        serverSaleService.removeSale(uuid);
    }

}
