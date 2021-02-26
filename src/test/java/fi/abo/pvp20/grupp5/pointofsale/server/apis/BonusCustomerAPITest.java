package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.models.BonusEntry;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IBonusCustomerRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.repositories.IBonusRepository;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCard;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.BonusCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class BonusCustomerAPITest {

    @MockBean
    private IBonusCustomerRepository customerRepository;

    @Autowired
    private IBonusRepository bonusRepository;

    private BonusCustomerAPI bonusCustomerAPI;

    private final BonusCustomer testBonusCustomer = new BonusCustomer();

    @BeforeEach
    void setUp() {
        bonusCustomerAPI = new BonusCustomerAPI(customerRepository, bonusRepository);

        testBonusCustomer.setCustomerNumber(1);
        testBonusCustomer.setFirstName("John");
        testBonusCustomer.setBonusCards(Arrays.asList(new BonusCard("1", 1, 1, "John Doe", false, false)));

        when(customerRepository.findById(1)).thenReturn(CompletableFuture.completedFuture(testBonusCustomer));
        when(customerRepository.findByCard(1, 1, 1)).thenReturn(CompletableFuture.completedFuture(testBonusCustomer));
    }

    @Test
    void findById() throws ExecutionException, InterruptedException {
        assertEquals("John", bonusCustomerAPI.findById(1).get().getFirstName());
    }

    @Test
    void findByCard() throws ExecutionException, InterruptedException {
        assertEquals("John", bonusCustomerAPI.findByCard(1, 1, 1).get().getFirstName());
    }

    @Test
    void saveBonusPoints() {
        bonusCustomerAPI.saveBonusPoints(new BonusEntry(1, 500));
        assertEquals(500, bonusRepository.findById(1).get().getBonusPoints());
        bonusCustomerAPI.saveBonusPoints(new BonusEntry(1, 1000));
        assertEquals(1000, bonusRepository.findById(1).get().getBonusPoints());
    }
}