package societyProduction.urbanPlanning.zoning.implementations;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import infrastructure.terrain.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class IndustrialSector extends ZoningSector {

    public IndustrialSector(@NotNull Tile... sectorTiles) {
        sectorSize = sectorTiles.length;
        this.sectorTiles.addAll(Arrays.asList(sectorTiles));
    }

    @Override
    public void zoneBuilding(GenericBuilding buildingToAdd) {
        sectorMembers.add(buildingToAdd);
    }

    @Override
    public boolean hasRoom(int buildingSize) {
        return false;
    }
}
