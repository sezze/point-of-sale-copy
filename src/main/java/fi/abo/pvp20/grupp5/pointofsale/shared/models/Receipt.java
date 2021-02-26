package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The Receipt class, used to print a receipt
 */
public class Receipt {
    private static final DateTimeFormatter FILE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
    private static final DateTimeFormatter IMAGE_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final Sale sale;
    private final ArrayList<String> receiptText = new ArrayList<>();
    private int numberOfReceiptLines;

    /**
     * The Receipt's constructor
     * @param sale The sale whose receipt you want to print
     */
    public Receipt(Sale sale) {
        this.sale = sale;
        constructReceiptText(sale.getBonusCustomer());
    }

    /**
     * Print the sale, in our case it saves it to a PNG
     * @param sale The sale whose receipt you want to print
     */
    public static void printSale(Sale sale) {
        var receipt = new Receipt(sale);
        receipt.saveReceiptAsPNGImage("receipt-" + LocalDateTime.now().format(FILE_DATE_FORMATTER));
    }

    /**
     * Generate the text that is to appear in the receipt
     * @param bonusCustomer The bonus customer that is to be associated with the sale. Can be null.
     */
    private void constructReceiptText(BonusCustomer bonusCustomer) {
        numberOfReceiptLines = 10;

        receiptText.add("                   Receipt");
        receiptText.add("____________________________________________");
        receiptText.add("");
        receiptText.add("OmniStore Oy.");
        receiptText.add("");
        receiptText.add("Omni Street 20700 Turku");
        receiptText.add("");
        receiptText.add("Time of purchase: " + sale.getTimeOfPurchase().format(IMAGE_DATE_FORMATTER));
        // If the sale is tied to a bonus customer show it on the receipt
        if (bonusCustomer != null) {
            receiptText.add("Customer: " + bonusCustomer.getFirstName() + " " + bonusCustomer.getLastName());
            receiptText.add("  ID: " + bonusCustomer.getCustomerNumber());
            numberOfReceiptLines += 2;
        }
        receiptText.add("____________________________________________");
        receiptText.add("");

        for (SaleItem saleItem : sale.getShoppingList()) {
            String productText = saleItem.getProduct().toString() + " x" + saleItem.getNumberOfItems();
            String priceText = CurrencyUtils.toString(saleItem.computeUnitGrossPrice(bonusCustomer));
            double discount1 = saleItem.getSaleDiscountPercentage();
            double discount2 = saleItem.getProduct().getDiscountPercentage(bonusCustomer);
            if (discount1 != 0 || discount2 != 0) {
                String saleText = "  "
                        + (discount1 != 0 ? "-" + discount1 + "% " : "")
                        + (discount2 != 0 ? "-" + discount2 + "%" : "");

                receiptText.add(productText);
                receiptText.add(saleText + " ".repeat(44 - saleText.length() - priceText.length()) + priceText);
                numberOfReceiptLines += 2;
            } else {
                receiptText.add(productText + " ".repeat(44 - productText.length() - priceText.length()) + priceText);
                numberOfReceiptLines += 1;
            }
        }
        receiptText.add("\n");
        receiptText.add("Total cost: " + CurrencyUtils.toString(sale.computeTotalCost()));

        numberOfReceiptLines += 2;
    }

    private int getNumberOfReceiptLines() {
        return numberOfReceiptLines;
    }

    /**
     * Export receipt as a PNG image
     * @param fileName The name of the png
     */
    public void saveReceiptAsPNGImage(String fileName) {
        int width = 1000;
        int height = 600 + (getNumberOfReceiptLines() - 10) * 40;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        Font font = new Font("Courier New", Font.BOLD, 35);
        g2d.setFont(font);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);

        int nextLinePosition = 100;
        int fontSize = 35;
        for (String string : receiptText) {
            g2d.drawString(string, 32, nextLinePosition);
            nextLinePosition = nextLinePosition + fontSize;
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(fileName + ".png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
