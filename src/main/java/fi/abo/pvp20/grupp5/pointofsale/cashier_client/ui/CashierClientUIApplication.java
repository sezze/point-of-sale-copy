package fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui;

import fi.abo.pvp20.grupp5.pointofsale.cashier_client.CashierClientApplication;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers.CashierViewController;
import fi.abo.pvp20.grupp5.pointofsale.cashier_client.ui.controllers.CustomerViewController;
import fi.abo.pvp20.grupp5.pointofsale.shared.ui.AbstractUIApplication;
import fi.abo.pvp20.grupp5.pointofsale.shared.utils.ImageUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;

/**
 * The cashier client's GUI application
 */
public class CashierClientUIApplication extends AbstractUIApplication {
    private final int initialWindowPadding = 16;

    /**
     * Setup all windows
     */
    @Override
    public void setupStages(Stage mainStage, FxWeaver fxWeaver) {
        setupCashierStage(fxWeaver, mainStage);
        setupCustomerStage(fxWeaver, new Stage());
    }

    /**
     * Get the CashierClientApplication class used in the SpringApplicationBuilder. It is used to connect JavaFX and
     * Spring Boot
     * @return The CashierClientApplication class
     */
    @Override
    public Class<?> getApplicationClass() {
        return CashierClientApplication.class;
    }

    /**
     * Setup the cashier window
     */
    private void setupCashierStage(FxWeaver fxWeaver, Stage stage) {
        Parent root = fxWeaver.loadView(CashierViewController.class);
        Scene scene = new Scene(root);

        stage.setTitle("Cashier display");
        int cashierStageWidth = 800;
        stage.setWidth(cashierStageWidth);
        int cashierStageHeight = 600;
        stage.setHeight(cashierStageHeight);
        stage.setScene(scene);
        stage.getIcons().add(ImageUtils.loadImage("cashier-icon.png"));
        stage.show();
    }

    private void setupCustomerStage(FxWeaver fxWeaver, Stage stage) {
        Parent root = fxWeaver.loadView(CustomerViewController.class);
        Scene scene = new Scene(root);

        stage.setTitle("Customer display");
        int customerStageWidth = 480;
        stage.setWidth(customerStageWidth);
        int customerStageHeight = 260;
        stage.setHeight(customerStageHeight);
        stage.setScene(scene);
        stage.getIcons().add(ImageUtils.loadImage("cashier-icon.png"));
        stage.show();
    }
}
