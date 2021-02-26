package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferTest {

    private SpecialOffer testSpecialOffer;
    private Set<String> testProducts;
    private Set<String> testKeywords;
    private LocalDate testStartDate;
    private LocalDate testEndDate;

    @BeforeEach
    void setUp() {
        testProducts = new HashSet<>(Arrays.asList("1234", "5678"));
        testKeywords = new HashSet<>(Arrays.asList("Soda", "Candy"));
        testStartDate = LocalDate.of(2020,10,21);
        testEndDate = LocalDate.of(2020,10,30);

        testSpecialOffer = new SpecialOffer();

        testSpecialOffer.setName("TestOffer");
        testSpecialOffer.setProducts(testProducts);
        testSpecialOffer.setKeywords(testKeywords);
        testSpecialOffer.setStartDate(testStartDate);
        testSpecialOffer.setEndDate(testEndDate);
        testSpecialOffer.setDiscountPercentage(15);
        testSpecialOffer.setForBonusCustomersOnly(false);
    }

    @Test
    void getName() {
        assertEquals("TestOffer" , testSpecialOffer.getName());
    }

    @Test
    void setName() {
        assertEquals("TestOffer" , testSpecialOffer.getName());
        testSpecialOffer.setName("AnotherOffer");
        assertEquals("AnotherOffer" , testSpecialOffer.getName());
    }

    @Test
    void getProducts() {
        assertEquals(testProducts , testSpecialOffer.getProducts());
    }

    @Test
    void setProducts() {
        assertEquals(testProducts , testSpecialOffer.getProducts());
        testProducts.add("9012");
        testSpecialOffer.setProducts(testProducts);
        assertEquals(testProducts , testSpecialOffer.getProducts());
    }

    @Test
    void getKeywords() {
        assertEquals(testKeywords , testSpecialOffer.getKeywords());
    }

    @Test
    void setKeywords() {
        assertEquals(testKeywords , testSpecialOffer.getKeywords());
        testKeywords.add("Snacks");
        testSpecialOffer.setProducts(testKeywords);
        assertEquals(testKeywords , testSpecialOffer.getKeywords());
    }

    @Test
    void getStartDate() {
        assertEquals(testStartDate , testSpecialOffer.getStartDate());
    }

    @Test
    void setStartDate() {
        assertEquals(testStartDate, testSpecialOffer.getStartDate());
        testStartDate = LocalDate.of(2020,10,20);
        assertNotEquals(testStartDate, testSpecialOffer.getStartDate());
        testSpecialOffer.setStartDate(testStartDate);
        assertEquals(testStartDate, testSpecialOffer.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals(testEndDate , testSpecialOffer.getEndDate());
    }

    @Test
    void setEndDate() {
        assertEquals(testEndDate , testSpecialOffer.getEndDate());
        testEndDate = LocalDate.of(2020,10,31);
        assertNotEquals(testEndDate, testSpecialOffer.getEndDate());
        testSpecialOffer.setEndDate(testEndDate);
        assertEquals(testEndDate , testSpecialOffer.getEndDate());
    }

    @Test
    void isForBonusCustomersOnly() {
        assertEquals(false , testSpecialOffer.isForBonusCustomersOnly());
    }

    @Test
    void setForBonusCustomersOnly() {
        assertEquals(false , testSpecialOffer.isForBonusCustomersOnly());
        testSpecialOffer.setForBonusCustomersOnly(true);
        assertEquals(true , testSpecialOffer.isForBonusCustomersOnly());
    }

    @Test
    void getDiscountPercentage() {
        assertEquals(15D , testSpecialOffer.getDiscountPercentage());
    }

    @Test
    void setDiscountPercentage() {
        assertEquals(15D , testSpecialOffer.getDiscountPercentage());
        testSpecialOffer.setDiscountPercentage(20);
        assertEquals(20D , testSpecialOffer.getDiscountPercentage());
    }

    @Test
    void testToString() {
        assertEquals(testSpecialOffer.getName() , testSpecialOffer.toString());
    }
}