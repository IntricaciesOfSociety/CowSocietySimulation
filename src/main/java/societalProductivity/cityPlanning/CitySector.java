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

    public boolean getIsRoom(int size) {
        for (Tile sectorTile : sectorTiles) {
            if (Tile.getIsRoom(size, sectorTile))
                return true;
        }
        return false;
    }

    public Tile getOpenTile(int size) {
        for (Tile sectorTile : sectorTiles) {
            if (Tile.getIsRoom(size, sectorTile))
                return sectorTile;
        }
        return null;
    }
}
