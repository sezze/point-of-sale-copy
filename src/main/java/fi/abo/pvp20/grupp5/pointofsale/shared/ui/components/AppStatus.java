package fi.abo.pvp20.grupp5.pointofsale.shared.ui.components;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class AppStatus extends HBox {
    private AppStatus(String msg, String className, boolean isDismissible) {
        getStyleClass().addAll("app-status", className);

        getChildren().add(new Label(msg) {{
            HBox.setHgrow(this, Priority.ALWAYS);
        }});

        if (isDismissible) {
            getChildren().add(new Button("X") {{
                setOnAction(e -> remove());
            }});
        }
    }

    private static Runnable showMessage(Scene scene, String msg, String className, boolean isDismissible) {
        var parent = scene.getRoot();
        if (parent instanceof Pane) {
            var appStatus = new AppStatus(msg, className, isDismissible);
            Platform.runLater(() -> ((Pane) parent).getChildren().add(0, appStatus));
            return appStatus::remove;
        }
        return null;
    }

    public static Runnable showError(Scene scene, Throwable throwable, boolean isDismissible) {
        String msg = throwable.getMessage().replaceFirst("^\\S+:\\s", "");
        return showError(scene, msg, isDismissible);
    }

    public static Runnable showError(Scene scene, String msg, boolean isDismissible) {
        return showMessage(scene, msg, "app-status-error", isDismissible);
    }

    public static Runnable showInfo(Scene scene, String msg, boolean isDismissible) {
        return showMessage(scene, msg, "app-status-info", isDismissible);
    }

    public void remove() {
        if (getParent() instanceof Pane) {
            var children = ((Pane) getParent()).getChildren();
            int i = children.indexOf(this);
            if (i != -1) children.remove(i);
        }
    }
}
