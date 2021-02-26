package fi.abo.pvp20.grupp5.pointofsale.server.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the BonusEntry class
 */
class BonusEntryTest {

    @Test
    void getBonusCustomerNumber() {
        BonusEntry testBonusEntry = new BonusEntry(1, 100);
        assertEquals(1, testBonusEntry.getBonusCustomerNumber());
    }

    @Test
    void setBonusCustomerNumber() {
        BonusEntry testBonusEntry = new BonusEntry(1, 100);
        assertEquals(1, testBonusEntry.getBonusCustomerNumber());
        testBonusEntry.setBonusCustomerNumber(2);
        assertEquals(2, testBonusEntry.getBonusCustomerNumber());
    }

    @Test
    void getBonusPoints() {
        BonusEntry testBonusEntry = new BonusEntry(1, 100);
        assertEquals(100, testBonusEntry.getBonusPoints());
    }

    @Test
    void setBonusPoints() {
        BonusEntry testBonusEntry = new BonusEntry(1, 100);
        assertEquals(100, testBonusEntry.getBonusPoints());
        testBonusEntry.setBonusPoints(200);
        assertEquals(200, testBonusEntry.getBonusPoints());
    }
}