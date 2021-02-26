package fi.abo.pvp20.grupp5.pointofsale.cashier_client;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.CashierClientUIApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The spring boot application for the cashier client with UI
 */
@SpringBootApplication
@ComponentScan(basePackages = {"fi.abo.pvp20.grupp5.pointofsale"})
public class CashierClientApplication {

    /**
     * The entry point for the cashier client application
     */
    public static void main(String[] args) {
        Application.launch(CashierClientUIApplication.class, args);
    }

}