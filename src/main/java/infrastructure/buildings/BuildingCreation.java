package infrastructure.buildings;

import infrastructure.buildings.buildingTypes.*;
import javafx.scene.image.Image;
import infrastructure.terrain.Tile;

/**
 * Handles the creation of large dwelling infrastructure. Called only if building prerequisites have been fulfilled
 * (resources and societyProduction.technology).
 */
public class BuildingCreation {

    public static GenericBuilding createAgriculturalBuilding(Image buildingSprite, String name, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            return new AgriculturalBuilding(buildingSprite, name, tileToBuildOn);
        else {
            System.out.println("Cannot construct " + name);
            return null;
        }
    }

    public static GenericBuilding createCommercialBuilding(Image buildingSprite, String name, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            return new CommercialBuilding(buildingSprite, name, tileToBuildOn);
        else {
            System.out.println("Cannot construct " + name);
            return null;
        }
    }

    public static GenericBuilding createGovernmentalBuilding(Image buildingSprite, String name, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            return new GovernmentalBuilding(buildingSprite, name, tileToBuildOn);
        else {
            System.out.println("Cannot construct " + name);
            return null;
        }
    }

    public static GenericBuilding createIndustrialBuilding(Image buildingSprite, String name, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            return new IndustrialBuilding(buildingSprite, name, tileToBuildOn);
        else {
            System.out.println("Cannot construct " + name);
            return null;
        }
    }

    public static GenericBuilding createResidentialBuilding(Image buildingSprite, String name, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            return new ResidentialBuilding(buildingSprite, name, tileToBuildOn);
        else {
            System.out.println("Cannot construct " + name);
            return null;
        }
    }
}
