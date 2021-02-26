package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyUtilTest {
    @Test
    public void testParsing() {
        assertEquals(10000, CurrencyUtils.Parse("100 "));
        assertEquals(38300, CurrencyUtils.Parse("  383€"));
        assertEquals(10053, CurrencyUtils.Parse("100.53€"));
        assertEquals(0, CurrencyUtils.Parse("0.00 €"));
        assertEquals(2402842, CurrencyUtils.Parse("  24028,42 € "));
    }

    @Test
    public void testToString() {
        assertEquals("1,00\u00A0€", CurrencyUtils.toString(100));
        assertEquals("100,00\u00A0€", CurrencyUtils.toString(10000));
        assertEquals("0,00\u00A0€", CurrencyUtils.toString(0));
    }
}
