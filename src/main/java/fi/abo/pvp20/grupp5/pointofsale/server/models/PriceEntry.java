package fi.abo.pvp20.grupp5.pointofsale.server.models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An entry in the price database
 *
 * It connects a product with a price
 */
@Entity
public class PriceEntry {

    @Id
    private String barcode;
    private int netPrice;

    /**
     * Empty constructor used by JPA
     */
    public PriceEntry() {
    }

    /**
     * The PriceEntry's constructor
     * @param barcode The products barcode
     * @param netPrice The price of the product
     */
    public PriceEntry(String barcode, int netPrice) {
        this.barcode = barcode;
        this.netPrice = netPrice;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(int netPrice) {
        this.netPrice = netPrice;
    }
}
