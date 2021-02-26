package fi.abo.pvp20.grupp5.pointofsale.server.apis;

import fi.abo.pvp20.grupp5.pointofsale.server.repositories.ISpecialOfferRepository;
import fi.abo.pvp20.grupp5.pointofsale.server.services.ServerSpecialOfferService;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SpecialOffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class SpecialOfferAPITest {

    @Qualifier("specialOfferRepository")
    @Autowired
    private ISpecialOfferRepository offerRepository;
    private ServerSpecialOfferService specialOfferService;
    private SpecialOfferAPI specialOfferAPI;

    private final SpecialOffer testSpecialOffer = new SpecialOffer();

    @BeforeEach
    void setUp() {
        specialOfferService = new ServerSpecialOfferService(offerRepository);
        specialOfferAPI = new SpecialOfferAPI(specialOfferService);

        testSpecialOffer.setName("TestOffer");
        testSpecialOffer.setDiscountPercentage(15);
        testSpecialOffer.setProducts(new HashSet<>(Arrays.asList("1234", "5678")));
        testSpecialOffer.setKeywords(new HashSet<>(Arrays.asList("Soda", "Candy")));
    }

    @Test
    void getSpecialOffers() {
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getSpecialOffers().get(0).getName());
    }

    @Test
    void getByName() {
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getByName("TestOffer").get().getName());
    }

    @Test
    void getByBarcode() {
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getByBarcode("1234").get(0).getName());
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getByBarcode("5678").get(0).getName());
    }

    @Test
    void getByKeyword() {
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getByKeyword("Soda").get(0).getName());
        assertEquals(testSpecialOffer.getName(), specialOfferAPI.getByKeyword("Candy").get(0).getName());
    }

    @Test
    void addSpecialOffer() {
        assertEquals(0, specialOfferAPI.getSpecialOffers().size());
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(1, specialOfferAPI.getSpecialOffers().size());
    }

    @Test
    void removeSpecialOffer() {
        assertEquals(0, specialOfferAPI.getSpecialOffers().size());
        specialOfferAPI.addSpecialOffer(testSpecialOffer);
        assertEquals(1, specialOfferAPI.getSpecialOffers().size());
        specialOfferAPI.removeSpecialOffer("TestOffer");
        assertEquals(0, specialOfferAPI.getSpecialOffers().size());
    }
}