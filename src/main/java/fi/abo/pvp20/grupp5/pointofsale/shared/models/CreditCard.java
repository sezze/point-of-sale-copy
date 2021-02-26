package fi.abo.pvp20.grupp5.pointofsale.shared.models;

/**
 * The CreditCard class representation
 */
public class CreditCard {

    String cardNumber;
    int expYear;
    int expMonth;
    String secCode;
    boolean expired;
    boolean blocked;

    /**
     * Constructor for a creditcard class containing the cardnumber, expiration month/year, the securityCode and if the
     * card is expired or not
     */
    public CreditCard(String number, int year, int month, String code, boolean exp, boolean block) {
        cardNumber = number;
        expYear = year;
        expMonth = month;
        secCode = code;
        expired = exp;
        blocked = block;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
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

}
