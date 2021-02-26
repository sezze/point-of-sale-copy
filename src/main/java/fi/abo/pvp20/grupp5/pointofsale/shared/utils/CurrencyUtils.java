package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyUtils {
    public static final Pattern CURRENCY_PATTERN = Pattern.compile("^(\\d+)(?>(?>,|.)(\\d{2}))?\\s*€?$");

    /**
     * Parses a string containing a money value, like "100.00 €", "4021" or "53€"
     *
     * @param value
     * @return The parsed money value (in cents)
     */
    public static int Parse(String value) {
        Matcher matcher = CURRENCY_PATTERN.matcher(value.trim());
        if (!matcher.matches()) throw new IllegalArgumentException();

        int euroAmount = Integer.parseInt(matcher.group(1)) * 100;
        int centAmount = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;
        return euroAmount + centAmount;
    }

    /**
     * Turn a currency amount, in cents, to a formatted string
     *
     * @param value amount in cents
     * @return Formatted amount, ex. 1 250,00 €
     */
    public static String toString(int value) {
        return toSimpleString(value)
                .replaceAll("(\\d)(?=(\\d{3})+,)", "$1\u00A0") // Insert spaces: 100 000,00 (u00A0 = non-breaking space)
                + "\u00A0€"; // Append euro sign
    }

    /**
     * Turn a currency amount, in cents, to a simple formatted string
     *
     * @param value amount in cents
     * @return Formatted amount, ex. 1250,00
     */
    public static String toSimpleString(int value) {
        String string = Integer.toString(value);
        String padding = "0".repeat(Math.max(0, 3 - string.length()));

        return (padding + string)
                .replaceAll("(?=.{2}$)", ","); // Insert comma:  100000,00
    }
}
