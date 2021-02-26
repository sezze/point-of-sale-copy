package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BonusCardTest {


    final BonusCard bonusCard = new BonusCard("123412341234", 12, 12, "Lars Larsson", false, false);

    @Test
    public void getCardNumberTest() {
        assertEquals(bonusCard.getCardNumber(), "123412341234");
    }

    @Test
    public void getExpYearTest() {
        assertEquals(bonusCard.getGoodThruYear(), 12);
    }

    @Test
    public void getExpMonthTest() {
        assertEquals(bonusCard.getGoodThruMonth(), 12);
    }

    @Test
    public void getSecCodeTest() {
        assertSame("Lars Larsson", bonusCard.getHolderName());
    }

    @Test
    public void getExpired() {
        assertFalse(bonusCard.getExpired());
    }

    @Test
    public void getBlocked() {
        assertFalse(bonusCard.getBlocked());
    }

    @Test
    public void setCardNumberTest() {
        bonusCard.setCardNumber("432143214321");
        assertSame("432143214321", bonusCard.cardNumber);
    }

    @Test
    public void setExpYearTest() {
        bonusCard.setGoodThruYear(1);
        assertEquals(bonusCard.goodThruYear, 1);
    }

    @Test
    public void setExpMonthTest() {
        bonusCard.setGoodThruMonth(7);
        assertEquals(bonusCard.goodThruMonth, 7);
    }

    @Test
    public void setHolderNameTest() {
        bonusCard.setHolderName("Sven Svensson");
        assertSame("Sven Svensson", bonusCard.holderName);
    }

    @Test
    public void setExpired() {
        bonusCard.setExpired(true);
        assertTrue(bonusCard.expired);
    }

    @Test
    public void setBlocked() {
        bonusCard.setBlocked(true);
        assertTrue(bonusCard.blocked);
    }
}