package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The SpecialOffer class, used to make special offers that discount specific products and/or keywords. Is applied for
 * a set time and can also be applied to all customers or only bonus customers.
 */
@Entity
public class SpecialOffer {

    @Id
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> products;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> keywords;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private double discountPercentage;
    private boolean forBonusCustomersOnly;

    /**
     * The SpecialOffer's constructor
     */
    public SpecialOffer() {
        name = "";
        products = new HashSet<>();
        keywords = new HashSet<>();
        startDate = LocalDate.now();
        endDate = LocalDate.now().plusMonths(1);
        discountPercentage = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getProducts() {
        return products;
    }

    public void setProducts(Set<String> products) {
        this.products = products == null ? new HashSet<>() : products;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords == null ? new HashSet<>() : keywords;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate duration) {
        this.endDate = duration;
    }

    public boolean isForBonusCustomersOnly() {
        return forBonusCustomersOnly;
    }

    public void setForBonusCustomersOnly(boolean forBonusCustomersOnly) {
        this.forBonusCustomersOnly = forBonusCustomersOnly;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return name;
    }
}
