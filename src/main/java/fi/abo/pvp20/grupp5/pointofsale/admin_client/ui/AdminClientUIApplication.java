package fi.abo.pvp20.grupp5.pointofsale.admin_client.ui;

import fi.abo.pvp20.grupp5.pointofsale.admin_client.AdminClientApplication;
import fi.abo.pvp20.grupp5.pointofsale.admin_client.ui.controllers.AdminViewController;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.AbstractUIApplication;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.ImageUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

/**
 * The admin client's GUI application
 */
public class AdminClientUIApplication extends AbstractUIApplication {
    /**
     * Setup all windows
     */
    @Override
    public void setupStages(Stage mainStage, FxWeaver fxWeaver) {
        setupAdminStage(fxWeaver, mainStage);
    }

    /**
     * Get the AdminClientApplication class used in the SpringApplicationBuilder. It is used to connect JavaFX and
     * Spring Boot
     * @return The AdminClientApplication class
     */
    @Override
    public Class<?> getApplicationClass() {
        return AdminClientApplication.class;
    }

    /**
     * Setup the admin client window
     */
    private void setupAdminStage(FxWeaver fxWeaver, Stage stage) {
        Parent root = fxWeaver.loadView(AdminViewController.class);
        Scene scene = new Scene(root);

        stage.setTitle("Administrator");
        int adminStageWidth = 800;
        stage.setWidth(adminStageWidth);
        int adminStageHeight = 540;
        stage.setHeight(adminStageHeight);
        stage.setScene(scene);
        stage.getIcons().add(ImageUtils.loadImage("admin-icon.png"));
        stage.show();
    }
}
