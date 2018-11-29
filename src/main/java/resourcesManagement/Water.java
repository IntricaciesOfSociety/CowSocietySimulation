package resourcesManagement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menus.MenuHandler;
import metaEnvironment.Playground;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * TODO: Implement proper water object
 * TODO: Move to tile structure
 * TEST ONLY, TEMPORARY.
 * Creates a rectangle that cows move to when their hunger is 0.
 */
public class Water {
    private static Random random = new Random();
    private static ImageView wateringHole = new ImageView();

    /**
     * Creates and moves the food object.
     */
    public static void initFood() {
        try {
            wateringHole.setImage(new Image(new FileInputStream("src/main/resources/Environment/WateringHole.png")));
        }
        catch (FileNotFoundException error) {
            MenuHandler.createErrorMenu();
        }
        wateringHole.setX(random.nextInt((int) Playground.playground.getPrefWidth()));
        wateringHole.setY(random.nextInt((int) Playground.playground.getPrefHeight()));
        Playground.playground.getChildren().add(wateringHole);
    }

    /**
     * @return The X value of the food.
     */
    public static double getX() {
        return wateringHole.getX();
    }

    /**
     * @return The Y value of the food.
     */
    public static double getY() {
        return wateringHole.getY();
    }
}