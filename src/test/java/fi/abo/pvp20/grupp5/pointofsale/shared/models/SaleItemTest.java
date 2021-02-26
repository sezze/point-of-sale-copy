package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SaleItemTest {
    @Test
    public void testSaleItemCreation() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("sports");
        keywords.add("holiday");

        // Prices are in cent (20000 = 200.00€)
        int netPrice = 20000;
        Product product = new Product("Badminton racket", "12345", keywords, 35, netPrice);


        SaleItem saleitem = new SaleItem(product, 1);

        assertSame("Badminton racket", saleitem.getProduct().getProductName());
        saleitem.getProduct().changeNetPrice(15000);
        saleitem.setSaleDiscountPercentage(30);
        assertEquals(14175, saleitem.computeUnitGrossPrice(null));
    }

}

