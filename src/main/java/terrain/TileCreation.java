package terrain;

import metaEnvironment.AssetLoading;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.Regioning.BinRegionHandler;

class TileCreation {

    static void createBasicTiles() {
        int horizontalRegions = LoadConfiguration.getHorizontalRegions();
        int verticalRegions = (LoadConfiguration.isSquareRegionSet()) ? horizontalRegions : LoadConfiguration.getVerticalRegions();
        for (int i = 0; i < horizontalRegions; i++)
            for (int j = 0; j < verticalRegions; j++) {
                BinRegion newRegion = BinRegionHandler.createNewRegion();
                BinRegionHandler.createGhostRegion(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400), LoadConfiguration.getBinRegionSize() * 400, LoadConfiguration.getBinRegionSize() * 400, newRegion.getBinId());
                newRegion.relocate(i * (LoadConfiguration.getBinRegionSize() * 400), j * (LoadConfiguration.getBinRegionSize() * 400));

                for (int k = 0; k < LoadConfiguration.getBinRegionSize(); k++)
                    for (int h = 0; h < LoadConfiguration.getBinRegionSize(); h++)
                        newRegion.addTile(new Tile(400 * k, 400 * h, AssetLoading.defaultTile, newRegion));
            }
    }
}
