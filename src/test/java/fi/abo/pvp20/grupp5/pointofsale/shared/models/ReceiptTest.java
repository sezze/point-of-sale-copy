package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;

class ReceiptTest {
    @Test
    public void testReceiptCreation() {

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("sports");
        keywords.add("holiday");

        int netPrice1 = 200;
        int netPrice2 = 300;
        int netPrice3 = 400;
        int netPrice4 = 500;

        Product product1 = new Product("Badminton racket", "12345", keywords, 35, netPrice1);
        Product product2 = new Product("Tennis racket", "54321", keywords, 35, netPrice2);
        Product product3 = new Product("Shorts", "1", keywords, 35, netPrice3);
        Product product4 = new Product("Banana", "2", keywords, 35, netPrice2);
        Product product5 = new Product("T-shirt", "3", keywords, 35, netPrice4);
        Product product6 = new Product("PS4 game", "4", keywords, 35, netPrice4);
        Product product7 = new Product("Book", "5", keywords, 35, netPrice1);
        Product product8 = new Product("Car", "6", keywords, 35, netPrice4);
        Product product9 = new Product("Bike", "7", keywords, 35, netPrice3);

        Sale sale = new Sale();

        sale.addItemsToShoppingList(product1, 7);
        sale.addItemsToShoppingList(product2, 8);
        sale.removeItemsFromShoppingList(product1, 4);
        sale.removeItemsFromShoppingList(product2, 2);
        sale.addItemsToShoppingList(product3, 9);
        sale.addItemsToShoppingList(product4, 10);
        sale.addItemsToShoppingList(product5, 10);
        sale.addItemsToShoppingList(product6, 10);
        sale.addItemsToShoppingList(product7, 10);
        sale.addItemsToShoppingList(product8, 10);
        sale.addItemsToShoppingList(product9, 10);

        sale.setSaleCompleted();

        sale.setTimeOfPurchase(ZonedDateTime.now());

        Receipt receipt = new Receipt(sale);

        receipt.saveReceiptAsPNGImage("testReceipt");
    }
}
