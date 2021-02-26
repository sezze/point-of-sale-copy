package fi.abo.pvp20.grupp5.pointofsale.cashier_client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The response from the card reader
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardReaderResponse {
    private String bonusCardNumber;
    private String paymentCardNumber;
    private PaymentCardType paymentCardType;
    private BonusState bonusState;
    private PaymentState paymentState;
    private int goodThruMonth;
    private int goodThruYear;

    /**
     * The CardReaderResponse constructor
     */
    public CardReaderResponse() {
    }

    public String getBonusCardNumber() {
        return bonusCardNumber;
    }

    public void setBonusCardNumber(String bonusCardNumber) {
        this.bonusCardNumber = bonusCardNumber;
    }

    public BonusState getBonusState() {
        return bonusState;
    }

    public void setBonusState(BonusState bonusState) {
        this.bonusState = bonusState;
    }

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(PaymentState paymentState) {
        this.paymentState = paymentState;
    }

    public PaymentCardType getPaymentCardType() {
        return paymentCardType;
    }

    public void setPaymentCardType(PaymentCardType paymentCardType) {
        this.paymentCardType = paymentCardType;
    }

    public int getGoodThruMonth() {
        return goodThruMonth;
    }

    public void setGoodThruMonth(int goodThruMonth) {
        this.goodThruMonth = goodThruMonth;
    }

    public int getGoodThruYear() {
        return goodThruYear;
    }

    public void setGoodThruYear(int goodThruYear) {
        this.goodThruYear = goodThruYear;
    }
}
