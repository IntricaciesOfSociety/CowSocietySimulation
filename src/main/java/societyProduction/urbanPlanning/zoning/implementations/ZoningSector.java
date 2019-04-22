package societyProduction.urbanPlanning.zoning.implementations;

import org.jetbrains.annotations.NotNull;
import infrastructure.terrain.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ZoningSector {

    private ArrayList<Tile> sectorTiles = new ArrayList<>();

    private int sectorSize;
    private int openTiles;

    ZoningSector(@NotNull Tile ... sectorTiles) {
        sectorSize = sectorTiles.length;
        openTiles = sectorTiles.length;
        this.sectorTiles.addAll(Arrays.asList(sectorTiles));
    }
}
