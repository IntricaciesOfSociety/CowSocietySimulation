package societalProductivity.urbanPlanning.zoning;

import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class CivicSector {

    private ArrayList<Tile> sectorTiles = new ArrayList<>();

    private int sectorSize;
    private int openTiles;

    CivicSector(@NotNull Tile ... sectorTiles) {
        sectorSize = sectorTiles.length;
        openTiles = sectorTiles.length;
        this.sectorTiles.addAll(Arrays.asList(sectorTiles));
    }
}
