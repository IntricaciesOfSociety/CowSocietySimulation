package societalProductivity.cityPlanning;

import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Arrays;

public class CitySector {

    private ArrayList<Tile> sectorTiles = new ArrayList<>();

    private int sectorSize;
    private int openTiles;

    CitySector(@NotNull Tile ... sectorTiles) {
        sectorSize = sectorTiles.length;
        openTiles = sectorTiles.length;
        this.sectorTiles.addAll(Arrays.asList(sectorTiles));
    }
}
