package fi.abo.pvp20.grupp5.pointofsale.admin_client;

import fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.AdminClientUIApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The spring boot application for the admin client with UI
 */
@SpringBootApplication
@ComponentScan(basePackages = {"fi.abo.pvp20.grupp5.pointofsale.shared", "fi.abo.pvp20.grupp5.pointofsale.admin_client"})
public class AdminClientApplication {

    /**
     * The entry point for the admin client application
     */
    public static void main(String[] args) {
        Application.launch(AdminClientUIApplication.class, args);
    }

}
