package fi.abo.pvp20.grupp5.pointofsale.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The BonusCustomer class representation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BonusCustomer {

    @JacksonXmlProperty(localName = "customerNo", isAttribute = true)
    private int customerNumber;
    private String firstName;
    private String lastName;
    private Address address;
    private Sex sex;
    private int bonusPoints = 0;
    private ZonedDateTime birthDate;
    @JacksonXmlProperty(localName = "bonusCard")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BonusCard> bonusCards;

    /**
     * Constructor for a bonus customer class containing the customer number, firstname lastname and date of birth
     */
    public BonusCustomer(int customerNumber, String firstName, String lastName, Address address, Sex sex) {
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.sex = sex;
    }

    /**
     * Empty constructor used by Jackson
     */
    public BonusCustomer() {
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(Integer bonusPoints) {
        this.bonusPoints = bonusPoints;
    }

    public List<BonusCard> getBonusCards() {
        return bonusCards;
    }

    public void setBonusCards(List<BonusCard> bonusCards) {
        this.bonusCards = bonusCards;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = ZonedDateTime.parse(birthDate, DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Creates a string that can be used directly in the GUI
     */
    @Override
    public String toString() {
        return "BonusCustomer{" +
                "customerNumber=" + customerNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", sex=" + sex +
                '}';
    }

    public enum Sex {
        ANY,
        MALE,
        FEMALE
    }
}
