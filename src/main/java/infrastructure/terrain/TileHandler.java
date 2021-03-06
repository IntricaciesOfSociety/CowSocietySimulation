package infrastructure.terrain;

import cowParts.creation.Cow;
import cowParts.actionSystem.ActionHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import metaControl.metaEnvironment.LoadConfiguration;
import metaControl.metaEnvironment.Regioning.BinRegion;
import metaControl.metaEnvironment.Regioning.BinRegionHandler;
import metaControl.metaEnvironment.Regioning.regionContainers.Playground;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TileHandler {

    private static Random random = new Random();

    public static void init() {
        TileCreation.createBasicTiles();
    }

    @Contract(pure = true)
    public static int getSize(@NotNull Image newSprite) {
        return (int) newSprite.getWidth() / 100;
    }

    public static Tile getRandOuterProxTile(int size, Tile centerTile, @NotNull Image ... tilesToExclude) {
        List<Image> exclusions = Arrays.asList(tilesToExclude);
        BinRegion region = centerTile.getRegion();
        ArrayList<Tile> regionTiles = region.getBasicTiles();

        int govOffsetIndex = 0;
        for (int j = 0; j < regionTiles.size(); j++) {
            if (regionTiles.get(j).getBoundsInParent().intersects(centerTile.getBoundsInParent())) {
                govOffsetIndex = j;
                for (int q = 0; q < 5; q++) {
                    if (govOffsetIndex > 0) {
                        govOffsetIndex -= 1;
                        if (q % 2 == 0 && (govOffsetIndex - LoadConfiguration.getBinRegionSize()) > 0)
                            govOffsetIndex -= LoadConfiguration.getBinRegionSize();
                    }
                }
                break;
            }
        }

        int randXOffset = -1;
        int randYOffset = -1;
        int failureBuffer = 0;
        while (true) {
            if (failureBuffer == 20) {
                System.out.println("Could not build at prox tile");
                return getRandRegionTile(size, region.getPlayground(), tilesToExclude);
            }
            randXOffset += 1 + (random.nextInt(30) + 1);
            randYOffset += 1 + (random.nextInt(30) + 1);

            try {
                Tile randCloseTile = regionTiles.get(govOffsetIndex + randXOffset + (randYOffset * LoadConfiguration.getBinRegionSize()));

                if (randCloseTile != null) {
                    Point2D randPoint = randCloseTile.getIsRoom(size);
                    if ((!exclusions.contains(randCloseTile.getImage())) && (randPoint != null)) {
                        System.out.println("Built at prox tile. Size: " + size);
                        return randCloseTile;
                    }
                }
            } catch (IndexOutOfBoundsException ignored) {
                failureBuffer++;
            }
        }
    }

    /**
     * Adds tiles from the tileList that still have room, excluding any tile types given if any.
     * @return The random tile that is not an exclusion and that has room
     */
    @Nullable
    public static Tile getRandRegionTile(int size, Playground regionArea, @NotNull Image ... tilesToExclude) {
        List<Image> exclusions = Arrays.asList(tilesToExclude);
        ArrayList<Tile> possibleTiles = new ArrayList<>();

        int startRegion = random.nextInt((PlaygroundHandler.getMaxBinId(regionArea) - PlaygroundHandler.getMinBinId(regionArea)) + 1) + PlaygroundHandler.getMinBinId(regionArea);
        int nextRegion = startRegion;

        boolean firstRegion = true;
        boolean allRegionsSearched = false;

        while (!allRegionsSearched) {
            List<Tile> baseTiles = BinRegionHandler.binRegionMap.get(nextRegion).getBasicTiles();

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
            if (possibleTiles.size() != 0) {
                return possibleTiles.get(random.nextInt(possibleTiles.size()));
            }


            if (nextRegion == startRegion && !firstRegion)
                allRegionsSearched = true;
            else
                nextRegion = (nextRegion + 1 < PlaygroundHandler.getMaxBinId(regionArea)) ? nextRegion + 1 : PlaygroundHandler.getMinBinId(regionArea);

            firstRegion = false;
        }
        return null;
    }

    @Nullable
    public static Tile getClosestTile(Cow cowToCheck, @NotNull List<Tile> resourceList) {
        if (!resourceList.isEmpty()) {
            Tile closestTile = resourceList.get(0);
            int smallestDistance = (int) ActionHandler.findDistanceBetweenCowAndObject(cowToCheck, resourceList.get(0));

            for (Tile resource : resourceList) {
                int tempDistance = (int) ActionHandler.findDistanceBetweenCowAndObject(cowToCheck, resource);
                if (tempDistance < smallestDistance) {
                    smallestDistance = tempDistance;
                    closestTile = resource;
                }
            }
            return closestTile;
        }
        else
            return null;
    }
}