package societyProduction.urbanPlanning.zoning;

import infrastructure.buildings.buildingTypes.*;
import infrastructure.terrain.TileHandler;
import societyProduction.urbanPlanning.zoning.implementations.*;

import java.util.ArrayList;

public class ZoningHandler {

    private static ArrayList<AgriculturalSector> agriculturalSectors = new ArrayList<>();
    private static ArrayList<CommercialSector> commercialSectors = new ArrayList<>();
    private static ArrayList<GovernmentalSector> governmentalSectors = new ArrayList<>();
    private static ArrayList<IndustrialSector> industrialSectors = new ArrayList<>();
    private static ArrayList<ResidentialSector> residentialSectors = new ArrayList<>();

    public static void zoneBuilding(GenericBuilding buildingToAdd) {
        if (buildingToAdd instanceof AgriculturalBuilding)
            addToOpenSector(TileHandler.getSize(buildingToAdd.getImage()), agriculturalSectors, buildingToAdd);
        else if (buildingToAdd instanceof CommercialBuilding)
            addToOpenSector(TileHandler.getSize(buildingToAdd.getImage()), commercialSectors, buildingToAdd);
        else if (buildingToAdd instanceof GovernmentalBuilding)
            addToOpenSector(TileHandler.getSize(buildingToAdd.getImage()), governmentalSectors, buildingToAdd);
        else if (buildingToAdd instanceof IndustrialBuilding)
            addToOpenSector(TileHandler.getSize(buildingToAdd.getImage()), industrialSectors, buildingToAdd);
        else if (buildingToAdd instanceof ResidentialBuilding)
            addToOpenSector(TileHandler.getSize(buildingToAdd.getImage()), residentialSectors, buildingToAdd);
    }

    private static void addToOpenSector(int buildingSize, ArrayList listToCheck, GenericBuilding buildingToAdd) {
        boolean ableSectorFound = false;
        for (Object sector : listToCheck) {
            if (((ZoningSector)sector).hasRoom(buildingSize)) {
                ((ZoningSector)sector).zoneBuilding(buildingToAdd);
                ableSectorFound = true;
            }
        }
        if (!ableSectorFound)
            System.out.println("No available sector found " + (!listToCheck.isEmpty() ? listToCheck.get(0).toString() : ""));
    }
}