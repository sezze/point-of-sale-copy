package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import fi.abo.pvp20.grupp5.pointofsale.shared.utils.CurrencyUtils;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

/**
 * The SaleItem class, contains a product, the number of products and a sale discount
 */
@Embeddable
public class SaleItem {

    @Convert(converter = ProductConverter.class)
    private Product product;
    private int numberOfItems;
    private double saleDiscountPercentage = 0.0;

    /**
     * The SaleItem's constuctor
     * @param product The product
     * @param numberOfItems The number of items
     */
    public SaleItem(Product product, int numberOfItems) {
        this.product = product;
        this.numberOfItems = numberOfItems;
    }

    /**
     * Constructor used by JPA
     */
    public SaleItem() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public double getSaleDiscountPercentage() {
        return saleDiscountPercentage;
    }

    public void setSaleDiscountPercentage(double saleDiscountPercentage) {
        this.saleDiscountPercentage = saleDiscountPercentage;
    }

    /**
     * Compute the gross price from the net price and any discounts
     * @param bonusCustomer A bonus customer, used with discounts that are only for bonus customers
     * @return Returns the gross price as an int (in cents)
     */
    public int computeUnitGrossPrice(BonusCustomer bonusCustomer) {
        return (int) ((1 - saleDiscountPercentage / 100.0) * (1 - product.getDiscountPercentage(bonusCustomer) / 100.0) * (1 + product.getVatPercentage() / 100.0) * product.getNetPrice() * numberOfItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        // Comparing SaleItems to products
        if (o.getClass() == Product.class) return product.equals(o);
        if (getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return product.equals(saleItem.product);
    }

    public String toString(BonusCustomer bonusCustomer) {
        double discount = product.getDiscountPercentage(bonusCustomer);
        return product + " x" + numberOfItems
                + (discount > 0 ? " -" + discount + "%" : "")
                + (saleDiscountPercentage > 0 ? " -" + saleDiscountPercentage + "%" : "")
                + " " + CurrencyUtils.toString(computeUnitGrossPrice(bonusCustomer));
    }
}
