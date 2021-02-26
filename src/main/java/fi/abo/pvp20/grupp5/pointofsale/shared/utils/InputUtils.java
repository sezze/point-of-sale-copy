package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class InputUtils {
    public static final UnaryOperator<TextFormatter.Change> INTEGER_INPUT_VALIDATOR = new UnaryOperator<TextFormatter.Change>() {
        @Override
        public TextFormatter.Change apply(TextFormatter.Change c) {
            if (c.getControlNewText().matches("([1-9][0-9]*)?")) return c;
            return null;
        }
    };
}
