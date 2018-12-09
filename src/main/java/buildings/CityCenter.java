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

public class CityCenter extends Tile implements Building {

    //TODO: Implement
    Point2D buildingEntrance;

    // TODO:Implement
    private int maximumCapacity = 10;

    private boolean inhabitantsMenuOpened = false;
    private MenuCreation inhabitantsMenu;

    private String streetAddress;

    private ArrayList<Cow> currentInhabitants = new ArrayList<>();

    public CityCenter(Image buildingSprite, Tile tileToBuildOn) {
        constructBuilding(buildingSprite, tileToBuildOn);
    }

    @Override
    public void constructBuilding(Image buildingSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(buildingSprite);

        int tileSize = (buildingSprite.getWidth() <= 400) ? 1 : 4;
        streetAddress = random.nextInt(500) + " Cow Drive";

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);

        if (tileToBuildOn.tieToObject(this, tileSize))
            BuildingHandler.constructedBuildings.add(this);
    }

    @Override
    public void contributeResource(String resourceContribution, int amountToBeUsed) {

    }

    @Override
    public void finishConstruction() {

    }

    @Override
    public void addInhabitant(Cow inhabitant) {

    }

    @Override
    public void removeInhabitant(Cow inhabitant) {

    }

    @Override
    public ArrayList<Cow> getCurrentInhabitants() {
        return null;
    }

    @Override
    public void toggleInhabitantsMenu() {

    }

    @Override
    public String getStreetAddress() {
        return null;
    }

    @Override
    public Tile getBuildingAsBuildingTile() {
        return null;
    }

    @Override
    public ResourceRequirement getResourceRequirement() {
        return null;
    }
}
