package societyProduction.urbanPlanning.zoning;

import infrastructure.terrain.Tile;
import societyProduction.urbanPlanning.zoning.implementations.*;

public class ZoneCreation {

    public static ZoningSector createAgriculturalSector(Tile... sectorTiles) {
        return new AgriculturalSector(sectorTiles);
    }

    public static ZoningSector createCommercialSector(Tile... sectorTiles) {
        return new CommercialSector(sectorTiles);
    }

    public static ZoningSector createGovernmentalSector(Tile... sectorTiles) {
        return new GovernmentalSector(sectorTiles);
    }

    public static ZoningSector createIndustrialSector(Tile... sectorTiles) {
        return new IndustrialSector(sectorTiles);
    }

    public static ZoningSector createResidentaialSector(Tile... sectorTiles) {
        return new ResidentialSector(sectorTiles);
    }
}