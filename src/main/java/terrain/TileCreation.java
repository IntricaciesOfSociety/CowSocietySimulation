package terrain;

import javafx.scene.image.Image;
import metaEnvironment.AssetLoading;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.Regioning.regionContainers.Playground;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.Regioning.BinRegionHandler;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TileCreation {

    private static Random random = new Random();

    static void createBasicTiles() {
        int horizontalRegions = LoadConfiguration.getWorldHRegions();
        int verticalRegions = (LoadConfiguration.isWorldSquare()) ? horizontalRegions : LoadConfiguration.getWorldVRegions();
        for (int i = 0; i < horizontalRegions; i++) {
            for (int j = 0; j < verticalRegions; j++) {
                BinRegion newRegion = BinRegionHandler.createNewRegion(PlaygroundHandler.getMotion());
                BinRegionHandler.createGhostRegion(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400), LoadConfiguration.getBinRegionSize() * 400, LoadConfiguration.getBinRegionSize() * 400, newRegion.getBinId());
                newRegion.relocate(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400));

                for (int k = 0; k < LoadConfiguration.getBinRegionSize(); k++)
                    for (int h = 0; h < LoadConfiguration.getBinRegionSize(); h++)
                        newRegion.addTile(new Tile(400 * k, 400 * h, AssetLoading.defaultWorldTile, newRegion));
            }
        }
        PlaygroundHandler.getMotion().setBinRegionIds(0, BinRegionHandler.newestRegionId - 1);
        createBiomes();
        createMines();
    }

    private static void createMines() {
        int minId = BinRegionHandler.newestRegionId;
        int horizontalRegions = LoadConfiguration.getMineHRegions();
        int verticalRegions = (LoadConfiguration.isMineSquare()) ? horizontalRegions : LoadConfiguration.getMineVRegions();
        PlaygroundHandler.setPlayground("Mines");
        PlaygroundHandler.getMines().setBrightness(-0.7);
        for (int i = 0; i < horizontalRegions; i++) {
            for (int j = 0; j < verticalRegions; j++) {
                BinRegion newRegion = BinRegionHandler.createNewRegion(PlaygroundHandler.getMines());
                BinRegionHandler.createGhostRegion(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400), LoadConfiguration.getBinRegionSize() * 400, LoadConfiguration.getBinRegionSize() * 400, newRegion.getBinId());
                newRegion.relocate(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400));

                for (int k = 0; k < LoadConfiguration.getBinRegionSize(); k++)
                    for (int h = 0; h < LoadConfiguration.getBinRegionSize(); h++)
                        newRegion.addTile(new Tile(400 * k, 400 * h, AssetLoading.defaultMineTile, newRegion));
            }
        }
        PlaygroundHandler.setPlayground("Motion");
        PlaygroundHandler.getMines().setBinRegionIds(minId, (BinRegionHandler.newestRegionId - 1));
    }

    private static void createBiomes() {
        ArrayList<Tile> tempTiles = new ArrayList<>();
        for (int i = 0; i < LoadConfiguration.getDesertBiomes(); i++) {
            tempTiles.addAll(BinRegionHandler.binRegionMap.get(random.nextInt(BinRegionHandler.newestRegionId)).getBasicTiles());
            changeBasicTiles(tempTiles, AssetLoading.desertTileFull);
            tempTiles.clear();
        }

        random = new Random();
        for (int i = 0; i < LoadConfiguration.getMountainBiomes(); i++) {
            tempTiles.addAll(BinRegionHandler.binRegionMap.get(random.nextInt(BinRegionHandler.newestRegionId)).getBasicTiles());
            changeBasicTiles(tempTiles, AssetLoading.mountainTileFull);
            tempTiles.clear();
        }
    }

    private static void changeBasicTiles(List<Tile> tilesToChange, Image newImage) {
        for (Tile tile : tilesToChange) tile.setImage(newImage);
    }
}
