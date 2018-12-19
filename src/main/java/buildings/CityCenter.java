package buildings;

import cowParts.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import menus.MenuCreation;
import metaControl.SimState;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import terrain.Tile;

import java.util.ArrayList;

/**
 * TODO: Implement
 */
public class CityCenter extends Building {

    public CityCenter(Image buildingSprite, Tile tileToBuildOn) {
        constructBuilding(buildingSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructBuilding(Image buildingSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(buildingSprite);

        int tileSize = (buildingSprite.getWidth() <= 400) ? 1 : 4;
        streetAddress = random.nextInt(500) + " Cow Drive";

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);

        if (tileToBuildOn.tieToObject(this, tileSize))
            BuildingHandler.buildingsList.add(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void contributeResource(String resourceContribution, int amountToBeUsed) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void finishConstruction() {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void addInhabitant(Cow inhabitant) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeInhabitant(Cow inhabitant) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Cow> getCurrentInhabitants() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void toggleInhabitantsMenu() {

    }

    /**
     * @inheritDoc
     */
    @Override
    public String getStreetAddress() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Tile getBuildingAsBuildingTile() {
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ResourceRequirement getResourceRequirement() {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConstructed() {
        return false;
    }
}
