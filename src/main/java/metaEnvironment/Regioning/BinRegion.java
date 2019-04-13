package metaEnvironment.Regioning;

import infrastructure.buildings.buildingTypes.*;
import javafx.scene.Group;
import metaEnvironment.Regioning.regionContainers.Playground;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WaterSource;
import resourcesManagement.resourceTypes.WoodSource;
import terrain.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public class BinRegion extends Group {

    private int id;

    private ArrayList<Tile> basicTileList = new ArrayList<>();

    private LinkedList<GenericBuilding> constructionQueue = new LinkedList<>();

    private ArrayList<AgriculturalBuilding> agriculturalBuildings = new ArrayList<>();
    private ArrayList<CommercialBuilding> commercialBuildings = new ArrayList<>();
    private ArrayList<GovernmentalBuilding> governmentalBuildings = new ArrayList<>();
    private ArrayList<IndustrialBuilding> industrialBuildings = new ArrayList<>();
    private ArrayList<ResidentialBuilding> residentialBuildings = new ArrayList<>();

    private ArrayList<WoodSource> woodSources = new ArrayList<>();
    private ArrayList<RockSource> rockSources = new ArrayList<>();
    private ArrayList<WaterSource> waterSources = new ArrayList<>();

    private Playground playgroundParent;

    BinRegion(int id, Playground playgroundParent) {
        this.setId(id);
        this.playgroundParent = playgroundParent;
    }

    private void setId(int id) {
        this.id = id;
    }

    public void addToBuildQueue(GenericBuilding buildingToConstruct) {
        constructionQueue.add(buildingToConstruct);
    }

    /**
     * Finds and returns the next building that needs to be constructed. The last element in the list is the newest building
     * and the first element is the oldest building.
     * @return The oldest not constructed building
     */
    @Nullable
    public GenericBuilding getNextToConstruct() {
        if (constructionQueue.size() > 0)
            return constructionQueue.getFirst();
        else
            return null;
    }

    public ArrayList<AgriculturalBuilding> getAllAgriculturalBuildings() {
        return agriculturalBuildings;
    }

    public void addAgriculturalBuilding(AgriculturalBuilding agriculturalBuilding) {
        agriculturalBuildings.add(agriculturalBuilding);
        this.getChildren().add(agriculturalBuilding);
    }

    public ArrayList<CommercialBuilding> getAllCommercialBuildings() {
        return commercialBuildings;
    }

    public void addCommercialBuilding(CommercialBuilding commercialBuilding) {
        commercialBuildings.add(commercialBuilding);
        this.getChildren().add(commercialBuilding);
    }

    public ArrayList<GovernmentalBuilding> getAllGovernmentalBuildings() {
        return governmentalBuildings;
    }

    public void addGovernmentalBuilding(GovernmentalBuilding governmentalBuilding) {
        governmentalBuildings.add(governmentalBuilding);
        this.getChildren().add(governmentalBuilding);
    }

    public ArrayList<IndustrialBuilding> getAllIndustrialBuildings() {
        return industrialBuildings;
    }

    public void addIndustrialBuilding(IndustrialBuilding industrialBuilding) {
        industrialBuildings.add(industrialBuilding);
        this.getChildren().add(industrialBuilding);
    }

    public ArrayList<ResidentialBuilding> getAllResidentialBuildings() {
        return residentialBuildings;
    }

    public void addResidentialBuilding(ResidentialBuilding residentialBuilding) {
        residentialBuildings.add(residentialBuilding);
        this.getChildren().add(residentialBuilding);
    }

    public void addWoodSource(WoodSource woodSource) {
        woodSources.add(woodSource);
        this.getChildren().add(woodSource);
    }

    public void addRockSource(RockSource rockSource) {
        rockSources.add(rockSource);
        this.getChildren().add(rockSource);
    }

    public void addWaterSource(WaterSource waterSource) {
        waterSources.add(waterSource);
        this.getChildren().add(waterSource);
    }

    public ArrayList<RockSource> getAllRockSources() {
        return rockSources;
    }

    public ArrayList<WoodSource> getAllWoodSources() {
        return woodSources;
    }

    public ArrayList<WaterSource> getAllWaterSources() {
        return waterSources;
    }

    public void removeRockSource(RockSource rockSource) {
        rockSources.remove(rockSource);
    }

    public void removeWoodSource(WoodSource woodSource) {
        woodSources.remove(woodSource);
    }

    public void removeWaterSource(WaterSource waterSource) {
        waterSources.remove(waterSource);
    }

    public ArrayList<GenericBuilding> getAllBuildings() {
        ArrayList<GenericBuilding> combinedList = new ArrayList<>();
        combinedList.addAll(agriculturalBuildings);
        combinedList.addAll(commercialBuildings);
        combinedList.addAll(industrialBuildings);
        combinedList.addAll(governmentalBuildings);
        combinedList.addAll(residentialBuildings);

        return combinedList;
    }

    public void removeFromConstructionQueue(GenericBuilding buildingToRemove) {
        constructionQueue.remove(buildingToRemove);
    }

    public void addTile(Tile tile) {
        basicTileList.add(tile);
        tile.toBack();
        this.getChildren().add(tile);
    }

    public ArrayList<Tile> getBasicTiles() {
        return basicTileList;
    }

    public int getBinId() {
        return id;
    }

    public int getMaxY() {
        return (int) basicTileList.get(basicTileList.size() - 1).getLayoutY() + 400;
    }

    public int getMaxX() {
        return (int) basicTileList.get(basicTileList.size() - 1).getLayoutX() + 400;
    }

    public Playground getPlayground() {
        return playgroundParent;
    }
}
