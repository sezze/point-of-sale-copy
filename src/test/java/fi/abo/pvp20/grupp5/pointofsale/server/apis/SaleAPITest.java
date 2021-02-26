package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IServerSaleRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.services.ServerSaleService;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SaleAPITest {

    @Autowired
    private IServerSaleRepository serverSaleRepository;
    private ServerSaleService serverSaleService;
    private SaleAPI saleAPI;

    private final Sale testSale = new Sale();

    @BeforeEach
    void setUp() {
        serverSaleService = new ServerSaleService(serverSaleRepository);
        saleAPI = new SaleAPI(serverSaleService);

        testSale.setUuid(UUID.randomUUID());
        testSale.setBonusCustomerNumber(1);
    }

    @Test
    void getSales() {
        saleAPI.addSale(testSale);
        assertEquals(testSale.getUuid(), saleAPI.getSales().get(0).getUuid());
    }

    @Test
    void getSale() {
        saleAPI.addSale(testSale);
        assertEquals(testSale.getUuid(), saleAPI.getSale(testSale.getUuid()).getUuid());
    }

    @Test
    void getSaleByBonusCustomerNumber() {
        saleAPI.addSale(testSale);
        assertEquals(testSale.getUuid(), saleAPI.getSale(testSale.getUuid()).getUuid());
    }

    @Test
    void addSale() {
        assertEquals(0, saleAPI.getSales().size());
        saleAPI.addSale(testSale);
        assertEquals(1, saleAPI.getSales().size());
    }

    @Test
    void removeSale() {
        assertEquals(0, saleAPI.getSales().size());
        saleAPI.addSale(testSale);
        assertEquals(1, saleAPI.getSales().size());
        saleAPI.removeSale(testSale.getUuid());
        assertEquals(0, saleAPI.getSales().size());
    }
}