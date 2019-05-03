package societyProduction.urbanPlanning.zoning.implementations;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import org.jetbrains.annotations.NotNull;
import infrastructure.terrain.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ZoningSector {

    ArrayList<Tile> sectorTiles = new ArrayList<>();
    ArrayList<GenericBuilding> sectorMembers = new ArrayList<>();

    int sectorSize;

    public abstract void zoneBuilding(GenericBuilding buildingToAdd);

    public abstract boolean hasRoom(int buildingSize);
}
