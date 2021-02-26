package fi.abo.pvp20.grupp5.pointofsale.server.services;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IServerSaleRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The service taking care of the server's sales
 */
@Service
public class ServerSaleService {

    private final IServerSaleRepository serverSaleRepository;

    /**
     * The ServerSaleService's constructor
     * @param serverSaleRepository The server's sale repository
     */
    @Autowired
    public ServerSaleService(IServerSaleRepository serverSaleRepository) {
        this.serverSaleRepository = serverSaleRepository;
    }

    /**
     * Add a new sale to the database
     * @param sale The sale to add to the repository
     */
    public void addSale(Sale sale) {
        serverSaleRepository.save(sale);
    }

    /**
     * Remove a sale from the database
     * @param uuid The UUID of the sale to remove
     */
    public void removeSale(UUID uuid) {
        serverSaleRepository.deleteById(uuid);
    }

    /**
     * Find a sale in the database
     * @param uuid The UUID of the sale to lookup
     * @return Returns a sale from the database if it's found
     */
    public Optional<Sale> findSale(UUID uuid) {
        return serverSaleRepository.findById(uuid);
    }

    /**
     * Get all sales from the database
     * @return Returns a list of all the sales in the database
     */
    public List<Sale> getSales() {
        ArrayList<Sale> sales = new ArrayList<>();
        serverSaleRepository.findAll().forEach(sales::add);
        return sales;
    }

    /**
     * Get all sales a bonus customer has made
     * @param bonusCustomerNumber The number of the bonus customer whose sales you want
     * @return Returns a list of all sales made by the bonus customer
     */
    public List<Sale> getSalesByBonusCustomerNumber(int bonusCustomerNumber) {
        return new ArrayList<>(
                serverSaleRepository.findAllByBonusCustomerNumber(bonusCustomerNumber));
    }

}
