package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The Sale class, used to store sales in the database
 */
@Entity
public class Sale {

    @ElementCollection
    private final List<SaleItem> shoppingList;
    @Id
    private UUID uuid;
    private boolean saleCompleted;
    private ZonedDateTime timeOfPurchase;
    private int bonusCustomerNumber;
    private int totalPaid;
    private String cardNumber = "";
    @Transient
    @JsonIgnore
    private BonusCustomer bonusCustomer;

    /**
     * The Sale's constructor
     */
    public Sale() {
        shoppingList = new LinkedList<>();
        saleCompleted = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Add a product to the sale
     * @param product The product to add
     * @param numberOfItems The ammount to add of the product
     * @return Returns a SaleItem containing the product
     */
    public SaleItem addItemsToShoppingList(Product product, int numberOfItems) {
        // Check if shopping list contains product
        SaleItem saleItem = shoppingList.stream()
                .filter(p -> p.getProduct().equals(product))
                .findAny().orElse(null);

        if (saleItem != null) {
            saleItem.setNumberOfItems(saleItem.getNumberOfItems() + numberOfItems);
        } else {
            saleItem = new SaleItem(product, numberOfItems);
            shoppingList.add(saleItem);
        }

        return saleItem;
    }

    /**
     * Remove a product from a SaleItem
     * @param saleItem The SaleItem to edit
     * @param numberOfItems The number of items to remove
     */
    public boolean removeItemsFromShoppingList(SaleItem saleItem, int numberOfItems) {
        if (!shoppingList.contains(saleItem)) {
            return false;
        } else if (numberOfItems > saleItem.getNumberOfItems()) {
            return false;
        } else if (numberOfItems == saleItem.getNumberOfItems()) {
            shoppingList.remove(saleItem);
            return true;
        } else {
            int newNumberOfItems = saleItem.getNumberOfItems() - numberOfItems;
            saleItem.setNumberOfItems(newNumberOfItems);
            return true;
        }
    }

    /**
     * Completely remove an item from the shopping list
     * @param product The product to remove
     * @param numberOfItems The number of products to remove
     */
    public boolean removeItemsFromShoppingList(Product product, int numberOfItems) {
        SaleItem saleItem = shoppingList.stream()
                .filter(s -> s.getProduct().equals(product))
                .findAny().orElse(null);

        return removeItemsFromShoppingList(saleItem, numberOfItems);
    }

    public void setSaleCompleted() {
        saleCompleted = true;
    }

    public boolean getSaleCompleted() {
        return saleCompleted;
    }

    public List<SaleItem> getShoppingList() {
        return shoppingList;
    }

    public void setTimeOfPurchase(ZonedDateTime timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }

    public boolean isSaleCompleted() {
        return saleCompleted;
    }

    public void setSaleCompleted(boolean saleCompleted) {
        this.saleCompleted = saleCompleted;
    }

    public void setTotalPaid(int totalPaid) {
        this.totalPaid = totalPaid;
    }

    public ZonedDateTime getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public int getBonusCustomerNumber() {
        return bonusCustomerNumber;
    }

    public void setBonusCustomerNumber(int bonusCustomerNumber) {
        this.bonusCustomerNumber = bonusCustomerNumber;
    }

    @JsonIgnore
    @Transient
    public int getTotalPaid() {
        return totalPaid;
    }

    @JsonIgnore
    @Transient
    public int getRemainingPayment() {
        return Math.max(computeTotalCost() - totalPaid, 0);
    }

    @JsonIgnore
    @Transient
    public int getReturnAmount() {
        return Math.max(totalPaid - computeTotalCost(), 0);
    }

    public void addPayment(int amount) {
        this.totalPaid += amount;
    }

    public int computeTotalCost() {
        int totalCost = 0;
        for (SaleItem saleItem : shoppingList) {
            totalCost += saleItem.computeUnitGrossPrice(bonusCustomer);
        }
        return totalCost;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Transient
    public BonusCustomer getBonusCustomer() {
        return bonusCustomer;
    }

    @Transient
    public void setBonusCustomer(BonusCustomer bonusCustomer) {
        this.bonusCustomer = bonusCustomer;
    }
}
