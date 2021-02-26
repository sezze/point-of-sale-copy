package fi.abo.pvp20.grupp5.pointofsale.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * The spring boot application for the server
 */
@SpringBootApplication
@ComponentScan(basePackages = {"fi.abo.pvp20.grupp5.pointofsale.server", "fi.abo.pvp20.grupp5.pointofsale.shared"})
@EntityScan(basePackages = {"fi.abo.pvp20.grupp5.pointofsale.server", "fi.abo.pvp20.grupp5.pointofsale.shared"})
public class ServerApplication {
    /**
     * The entry point for the cashier client application
     */
    public static void main(String... args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
