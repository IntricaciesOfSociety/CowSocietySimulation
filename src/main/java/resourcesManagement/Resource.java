package resourcesManagement;

import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

public interface Resource {

    void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn);
}
