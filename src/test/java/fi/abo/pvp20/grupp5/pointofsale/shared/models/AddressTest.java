package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    private Address testAddress;

    @BeforeEach
    void setUp() {
        testAddress = new Address("Street 1", "1", "Town", "Country");
    }

    @Test
    void getStreetAddress() {
        assertEquals("Street 1", testAddress.getStreetAddress());
    }

    @Test
    void setStreetAddress() {
        assertEquals("Street 1", testAddress.getStreetAddress());
        testAddress.setStreetAddress("Street 2");
        assertEquals("Street 2", testAddress.getStreetAddress());
    }

    @Test
    void getPostalCode() {
        assertEquals("1", testAddress.getPostalCode());
    }

    @Test
    void setPostalCode() {
        assertEquals("1", testAddress.getPostalCode());
        testAddress.setPostalCode("2");
        assertEquals("2", testAddress.getPostalCode());
    }

    @Test
    void getPostOffice() {
        assertEquals("Town", testAddress.getPostOffice());
    }

    @Test
    void setPostOffice() {
        assertEquals("Town", testAddress.getPostOffice());
        testAddress.setPostOffice("Village");
        assertEquals("Village", testAddress.getPostOffice());
    }

    @Test
    void getCountry() {
        assertEquals("Country", testAddress.getCountry());
    }

    @Test
    void setCountry() {
        assertEquals("Country", testAddress.getCountry());
        testAddress.setCountry("Kingdom");
        assertEquals("Kingdom", testAddress.getCountry());
    }

    @Test
    void testToString() {
        assertEquals(testAddress.getStreetAddress()
                        + "\n" + testAddress.getPostalCode()
                        + "\n" + testAddress.getPostOffice()
                        + "\n" + testAddress.getCountry(),
                testAddress.toString());
    }
}