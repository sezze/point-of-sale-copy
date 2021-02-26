package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    /**
     * Creates a CreditCard object with given parameters, and thus testing geting them and changing them
     */
    final CreditCard creditCard = new CreditCard("123412341234", 12, 12, "201", false, false);

    @Test
    public void getCardNumberTest() {
        assertSame("123412341234", creditCard.getCardNumber());
    }

    @Test
    public void getExpYearTest() {
        assertEquals(creditCard.getExpYear(), 12);
    }

    @Test
    public void getExpMonthTest() {
        assertEquals(creditCard.getExpMonth(), 12);
    }

    @Test
    public void getSecCodeTest() {
        assertSame("201", creditCard.getSecCode());
    }

    @Test
    public void getExpired() {
        assertFalse(creditCard.getExpired());
    }

    @Test
    public void getBlocked() {
        assertFalse(creditCard.getBlocked());
    }

    @Test
    public void setCardNumberTest() {
        creditCard.setCardNumber("432143214321");
        assertSame("432143214321", creditCard.cardNumber);
    }

    @Test
    public void setExpYearTest() {
        creditCard.setExpYear(1);
        assertEquals(creditCard.expYear, 1);
    }

    @Test
    public void setExpMonthTest() {
        creditCard.setExpMonth(7);
        assertEquals(creditCard.expMonth, 7);
    }

    @Test
    public void setSecCodeTest() {
        creditCard.setSecCode("961");
        assertSame("961", creditCard.secCode);
    }

    @Test
    public void setExpired() {
        creditCard.setExpired(true);
        assertTrue(creditCard.getExpired());
    }

    @Test
    public void setBlocked() {
        creditCard.setBlocked(true);
        assertTrue(creditCard.getBlocked());
    }

}