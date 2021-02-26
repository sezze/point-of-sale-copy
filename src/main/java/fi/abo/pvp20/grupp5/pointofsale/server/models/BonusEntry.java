package fi.abo.pvp20.grupp5.pointofsale.server.models;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An entry in the bonus point database
 *
 * It connects a bonus customer with his/her collected bonus points
 */
@Entity
public class BonusEntry {
    @Id
    private int bonusCustomerNumber;
    private int bonusPoints;

    /**
     * The BonusEntry's constructor
     * @param number The bonus customer number
     * @param points The number of points
     */
    public BonusEntry(int number, int points) {
        bonusCustomerNumber = number;
        bonusPoints = points;
    }

    /**
     * Used by the JPA
     */
    public BonusEntry() {
    }

    public int getBonusCustomerNumber() {
        return bonusCustomerNumber;
    }

    public void setBonusCustomerNumber(int bonusCustomerNumber) {
        this.bonusCustomerNumber = bonusCustomerNumber;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }

    public void setBonusPoints(int bonusPoints) {
        this.bonusPoints = bonusPoints;
    }
}
