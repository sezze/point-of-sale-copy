package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SaleTest {
    @Test
    public void testSaleCreation() {
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("sports");
        keywords.add("holiday");

        // Prices are in cent (20000 = 200.00€)
        int netPrice1 = 20000;
        int netPrice2 = 30000;
        Product product1 = new Product("Badminton racket", "12345", keywords, 35, netPrice1);
        Product product2 = new Product("Tennis racket", "54321", keywords, 35, netPrice2);

        Sale sale = new Sale();

        SaleItem saleItem1 = sale.addItemsToShoppingList(product1, 2);
        SaleItem saleItem2 = sale.addItemsToShoppingList(product2, 5);
        sale.removeItemsFromShoppingList(saleItem1, 1);
        sale.removeItemsFromShoppingList(saleItem2, 3);

        assertSame(sale.getShoppingList().get(0), saleItem1);
        assertSame(sale.getShoppingList().get(1), saleItem2);
        assertEquals(sale.computeTotalCost(), 108000);
    }
}