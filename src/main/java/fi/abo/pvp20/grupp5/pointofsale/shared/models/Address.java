package fi.abo.pvp20.grupp5.pointofsale.shared.models;

/**
 * An address used by the BonusCustomer class
 */
public class Address {
    private String streetAddress;
    private String postalCode;
    private String postOffice;
    private String country;

    /**
     * The Address constructor
     * @param streetAddress The customers street address as a string
     * @param postalCode The customers postal code as a string
     * @param postOffice The customers post office as a string
     * @param country The customers country as a string
     */
    public Address(String streetAddress, String postalCode, String postOffice, String country) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.postOffice = postOffice;
        this.country = country;
    }

    public Address() {
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Creates a string that can be used directly in the GUI
     */
    @Override
    public String toString() {
        return streetAddress + "\n" + postalCode + "\n" + postOffice + "\n" + country;
    }
}
