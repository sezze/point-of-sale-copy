package fi.abo.pvp20.grupp5.pointofsale.shared.models;

/**
 * A simple class containing a product and a sale count.
 * Used by sales figures to count sold items.
 */
public class ProductSaleRecord {
    private Product product;
    private int count;

    public ProductSaleRecord(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBarcode() {
        return product.getBarcode();
    }

    public static ProductSaleRecord merge(ProductSaleRecord a, ProductSaleRecord b) {
        return new ProductSaleRecord(a.getProduct(), a.getCount() + b.getCount());
    }

    @Override
    public String toString() {
        return product + " x" + count;
    }
}
