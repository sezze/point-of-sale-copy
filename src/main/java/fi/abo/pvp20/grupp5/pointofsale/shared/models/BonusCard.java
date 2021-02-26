package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The BonusCard class representation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BonusCard {

    @JsonProperty("number")
    String cardNumber;
    int goodThruYear;
    int goodThruMonth;
    String holderName;
    boolean expired;
    boolean blocked;

    /**
     * Implement a BonusCard with a specific CardNumber, Exp month/year and Holder name .
     */
    public BonusCard(String number, int year, int month, String name, boolean exp, boolean blo) {
        cardNumber = number;
        goodThruYear = year;
        goodThruMonth = month;
        holderName = name;
        expired = exp;
        blocked = blo;
    }

    /**
     * Empty constructor used by Jackson
     */
    public BonusCard() {
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getGoodThruYear() {
        return goodThruYear;
    }

    public void setGoodThruYear(int goodThruYear) {
        this.goodThruYear = goodThruYear;
    }

    public int getGoodThruMonth() {
        return goodThruMonth;
    }

    public void setGoodThruMonth(int goodThruMonth) {
        this.goodThruMonth = goodThruMonth;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public boolean getExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * Creates a string that can be used directly in the GUI
     */
    @Override
    public String toString() {
        return cardNumber + " " + goodThruMonth + "/" + goodThruYear + " - " + holderName;
    }
}
