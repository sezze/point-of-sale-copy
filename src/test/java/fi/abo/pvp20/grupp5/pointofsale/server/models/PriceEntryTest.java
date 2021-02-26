package fi.abo.pvp20.grupp5.pointofsale.server.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the PriceEntry class
 */
class PriceEntryTest {

    @Test
    void getBarcode() {
        PriceEntry testPriceEntry = new PriceEntry("1234", 100);
        assertEquals("1234", testPriceEntry.getBarcode());
    }

    @Test
    void getNetPrice() {
        PriceEntry testPriceEntry = new PriceEntry("1234", 100);
        assertEquals(100, testPriceEntry.getNetPrice());
    }

    @Test
    void setNetPrice() {
        PriceEntry testPriceEntry = new PriceEntry("1234", 100);
        assertEquals(100, testPriceEntry.getNetPrice());
        testPriceEntry.setNetPrice(200);
        assertEquals(200, testPriceEntry.getNetPrice());
    }
}