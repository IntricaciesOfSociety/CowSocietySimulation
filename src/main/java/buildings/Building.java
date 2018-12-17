package buildings;

import cowParts.Cow;
import cowMovement.Movement;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Random;

public interface Building {

    /**
     * The random variable to be used to generate random street numbers.
     */
    Random random = new Random();

    /**
     * Creates a Building with the given image and ties that Building to a tile.
     * @param buildingSprite The image to be used for an ImageView relation to a tile
     * @param tileToBuildOn The tile that the building will be built on
     */
    void constructBuilding(Image buildingSprite, @NotNull Tile tileToBuildOn);

    void contributeResource(String resourceContribution, int amountToBeUsed);

    void finishConstruction();

    /**
     * Adds as an inhabitant by adding the cow to the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    void addInhabitant(Cow inhabitant);

    /**
     * Removes an inhabitant by removing the cow from the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    void removeInhabitant(Cow inhabitant);

    /**
     * Returns the list of all inhabitants within this building.
     * @return The list of all inhabitants
     */
    ArrayList<Cow> getCurrentInhabitants();

    /**
     * Calls for the creation or the destruction of this building's inhabitants menu based off the last state of the
     * inhabitants menu.
     */
    void toggleInhabitantsMenu();

    /**
     * Returns the street address of the given building.
     * @return The address found
     */
    @Contract(pure = true)
    String getStreetAddress();

    /**
     * @return The building as the tile type that it is instead of as the Building type
     */
    Tile getBuildingAsBuildingTile();

    ResourceRequirement getResourceRequirement();

    boolean isConstructed();

    /**
     * Adds the given cow to the given building and updates that cow's animation accordingly.
     * @param cowToMove The cow to be added into the building
     * @param buildingToMoveInto The building to add the cow into
     */
    static void enterBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToMoveInto) {
        cowToMove.hide();
        cowToMove.setBuildingIn((Building) buildingToMoveInto);

        ((Building) buildingToMoveInto).addInhabitant(cowToMove);

        if(cowToMove.animation != null) {
            cowToMove.animation.stop();
        }

        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
        cowToMove.setLayoutX(buildingToMoveInto.getLayoutX());
        cowToMove.setLayoutY(buildingToMoveInto.getLayoutY());
        cowToMove.relocate(buildingToMoveInto.getLayoutX(), buildingToMoveInto.getLayoutY());

        Movement.pauseMovement(cowToMove.getBuildingTime(), cowToMove);
    }

    /**
     * Removes the cow from the building that it is in.
     * @param cowToMove The cow to remove from the building
     * @param buildingToExitFrom The building to remove the given cow from
     */
    static void exitBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToExitFrom) {
        cowToMove.setBuildingIn(null);

        cowToMove.relocate(buildingToExitFrom.getLayoutX() + buildingToExitFrom.getImage().getWidth() / 2,
                buildingToExitFrom.getLayoutY() + buildingToExitFrom.getImage().getHeight() + 75);
        ((Building)buildingToExitFrom).removeInhabitant(cowToMove);
        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
        cowToMove.relocate(cowToMove.getAnimatedX(), cowToMove.getAnimatedY());
    }
}
