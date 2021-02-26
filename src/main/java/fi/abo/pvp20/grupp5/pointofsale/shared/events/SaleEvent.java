package fi.abo.pvp20.grupp5.pointofsale.shared.events;

import fi.abo.pvp20.grupp5.pointofsale.shared.models.Sale;
import fi.abo.pvp20.grupp5.pointofsale.shared.models.SaleItem;

/**
 * A sale event
 */
public class SaleEvent {
    public final Sale sale;
    public final SaleItem item;
    public final Change change;

    /**
     * The SaleEvent's constructor
     */
    public SaleEvent(Sale sale, SaleItem item, Change change) {
        this.sale = sale;
        this.item = item;
        this.change = change;
    }

    /**
     * The kind of event
     */
    public enum Change {
        ITEM_ADDED,
        ITEM_REMOVED,
        ITEM_CHANGED,
        NEW_SALE,
        PAYMENT
    }
}
