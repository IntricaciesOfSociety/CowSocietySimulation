package terrain;

import metaEnvironment.AssetLoading;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.Regioning.BinRegionHandler;

class TileCreation {

    static void createBasicTiles() {
        final int binOffset = LoadConfiguration.getBinRegionSize() * 400;

        int horizonalRegions = LoadConfiguration.getHorizontalRegions();
        int verticalRegions = (LoadConfiguration.isSquareRegionSet()) ? horizonalRegions : LoadConfiguration.getVerticalRegions();
        for (int i = 0; i < horizonalRegions; i++) {
            for (int j = 0; j < verticalRegions; j++) {
                BinRegion newRegion = BinRegionHandler.createNewRegion();

                for (int k = 0; k < LoadConfiguration.getBinRegionSize(); k++) {
                    for (int h = 0; h < LoadConfiguration.getBinRegionSize(); h++) {
                        newRegion.addTile(
                                new Tile(
                                        (400 * k) + (binOffset * i),
                                        (400 * h) + (binOffset * j),
                                        AssetLoading.defaultTile, newRegion)
                        );
                    }
                }
            }
        }
    }
}
