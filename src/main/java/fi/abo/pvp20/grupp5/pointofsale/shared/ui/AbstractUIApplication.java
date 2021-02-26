package fi.abo.pvp20.grupp5.pointofsale.shared.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractUIApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    /**
     * Used to setup all JavaFX windows
     */
    public abstract void setupStages(Stage mainStage, FxWeaver fxWeaver);

    /**
     * Used to get the "Spring Boot" application which the JavaFX application should "connect" to
     * @return A class that is to be used in the SpringApplicationBuilder
     */
    public abstract Class<?> getApplicationClass();

    /**
     * Initialize the JavaFX application, connecting it to a Spring Application
     */
    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(getApplicationClass())
                .run(args);
    }

    /**
     * Start and show the JavaFX window
     * @param mainStage
     */
    @Override
    public void start(Stage mainStage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);

        setupStages(mainStage, fxWeaver);
    }

    /**
     * Stop the JavaFX application and close all associated windows
     */
    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }


}
