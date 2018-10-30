package environment;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * TODO: Implement proper food
 * TEST ONLY, TEMPORARY.
 * Creates a rectangle that cows move to when their hunger is 0.
 */
public class Food {
    private static Random random = new Random();
    private static Rectangle foodObject = new Rectangle();

    /**
     * Creates and moves the food object.
     */
    public static void initFood() {
        foodObject.setWidth(50);
        foodObject.setHeight(50);
        foodObject.setFill(Color.GREEN);
        foodObject.setX(random.nextInt(800));
        foodObject.setY(random.nextInt(600));
        Playground.playground.getChildren().add(foodObject);
    }

    /**
     * @return The X value of the food
     */
    static double getX() {
        return foodObject.getX();
    }

    /**
     * @return The Y value of the food
     */
    static double getY() {
        return foodObject.getY();
    }
}
