package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BonusCustomerTest {
    /**
     * Tests
     * Creates a BonusCustomer object with given parameters, performs tests to get the correct values as well as changing them.
     */
    final BonusCustomer bonusCustomer = new BonusCustomer(1, "Robert", "Meller", null, BonusCustomer.Sex.MALE);

    @Test
    public void getCustomerNoTest() {
        assertSame(1, bonusCustomer.getCustomerNumber());
    }

    @Test
    public void getFirstNameTest() {
        assertSame("Robert", bonusCustomer.getFirstName());
    }

    @Test
    public void getLastNameTest() {
        assertSame("Meller", bonusCustomer.getLastName());
    }

    @Test
    public void getSex() {
        assertSame(BonusCustomer.Sex.MALE, bonusCustomer.getSex());
    }

    @Test
    public void getBonusPoints() {
        assertEquals(0, bonusCustomer.getBonusPoints());
        bonusCustomer.setBonusPoints(55);
        assertEquals(55, bonusCustomer.getBonusPoints());
    }

    @Test
    public void setCustomerNoTest() {
        bonusCustomer.setCustomerNumber(2);
        assertSame(2, bonusCustomer.getCustomerNumber());
    }

    @Test
    public void setFirstNameTest() {
        bonusCustomer.setFirstName("Johan");
        assertSame("Johan", bonusCustomer.getFirstName());
    }

    @Test
    public void setLastNameTest() {
        bonusCustomer.setLastName("Johansson");
        assertEquals(bonusCustomer.getLastName(), "Johansson");
    }

    @Test
    public void setSex() {
        bonusCustomer.setSex(BonusCustomer.Sex.MALE);
        assertSame(BonusCustomer.Sex.MALE, bonusCustomer.getSex());
    }

    @Test
    public void setBonuspoints() {
        bonusCustomer.setBonusPoints(10);
        assertEquals(10, bonusCustomer.getBonusPoints());
    }

}
