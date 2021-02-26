package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncUtils {
    public static <T> void updateListWhenComplete(
            ListView<T> listView, CompletableFuture<List<T>> future, String nameOfItemType) {
        future.whenComplete((content, throwable) -> {
            if (throwable == null) {
                listView.setItems(FXCollections.observableList(content));
            }
        });
    }
}
