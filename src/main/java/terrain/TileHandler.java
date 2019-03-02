package terrain;

import javafx.scene.image.Image;
import metaEnvironment.Playground;
import metaEnvironment.Regioning.BinRegionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TileHandler {

    private static Random random = new Random();

    static final int ROWTILES = (int) Playground.playground.getPrefWidth() / 400;
    static final int COLTILES = (int) Playground.playground.getPrefHeight() / 400;

    public static void init() {
        TileCreation.createBasicTiles();
    }

    @Contract(pure = true)
    public static int getSize(@NotNull Image newSprite) {
        return (int) newSprite.getWidth() / 100;
    }

    /**
     * Adds tiles from the tileList that still have room, excluding any tile types given if any.
     * @return The random tile that is not an exclusion and that has room
     */
    @Nullable
    public static Tile getRandomNotFullTile(int size, @NotNull Image ... tilesToExclude) {
        List<Image> exclusions = Arrays.asList(tilesToExclude);

        List<Tile> baseTiles = BinRegionHandler.binRegionMap.get(random.nextInt(BinRegionHandler.newestRegionId)).getBasicTiles();
        ArrayList<Tile> possibleTiles = new ArrayList<>();

        boolean broken;
        for (Tile baseTile : baseTiles) {
            int[][] possibleTileArray = baseTile.placementArray;
            if (!exclusions.contains(baseTile.getImage()) && possibleTileArray[0][0] != 2) {
                tileCheck:
                for (int i = 0; i < possibleTileArray.length - (size - 1); i++)
                    for (int j = 0; j < possibleTileArray.length - (size - 1); j++) {
                        broken = false;
                        tileSectionCheck:
                        for (int k = 0; k < size; k++)
                            for (int h = 0; h < size; h++)
                                if (possibleTileArray[i + k][j + h] == 1) {
                                    broken = true;
                                    break tileSectionCheck;
                                }
                        if (!broken) {
                            possibleTiles.add(baseTile);
                            break tileCheck;
                        }
                    }
            }
        }
        if (possibleTiles.size() != 0)
            return possibleTiles.get(random.nextInt(possibleTiles.size()));
        else
            return null;
    }
}
