package resourcesManagement;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

/**
 * Defines what a resource is and how it is handled.
 */
public interface Resource {

    /**
     * Constructs a resource based off of the class being called from, the sprite given, and the tile given.
     * @param sourceSprite The sprite to create a resource with
     * @param tileToBuildOn The tile to create the resource upon
     */
    void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn);

}
