package fi.abo.pvp20.grupp5.pointofsale.cashier_client.models;

/**
 * The different states a payment can be in
 */
public enum PaymentState {
    UNDEFINED,
    ACCEPTED,
    INSUFFICIENT_FUNDS,
    UNSUPPORTED_CARD,
    INVALID_PIN,
    NETWORK_ERROR
}
