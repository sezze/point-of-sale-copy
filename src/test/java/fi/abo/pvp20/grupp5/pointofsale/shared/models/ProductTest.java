package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ProductTest {
    @Test
    public void testProductCreation() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("sports");
        keywords.add("holiday");

        Product product = new Product("My Product Name", "12345", keywords, 35, 20000);
        assertSame("My Product Name", product.getProductName());
        assertEquals(product.getVatPercentage(), 35);
        product.changeVatPercentage(55);
        assertEquals(product.getVatPercentage(), 55);
        assertSame("12345", product.getBarcode());

        assertSame("sports", product.getKeywords().get(0));
        assertSame("holiday", product.getKeywords().get(1));

    }
}

