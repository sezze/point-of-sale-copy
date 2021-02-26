package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import javafx.scene.image.Image;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class ImageUtils {
    public static Image loadImage(String path) {
        try {
            return new Image(new ClassPathResource("/images/" + path).getInputStream());
        } catch (IOException e) {
            System.out.println("Image could not be found in /images/" + path);
            return null;
        }
    }
}
