package metaControl.metaEnvironment.Regioning;

import javafx.scene.shape.Rectangle;
import metaControl.metaEnvironment.Regioning.regionContainers.Playground;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class BinRegionHandler {

    public static ConcurrentHashMap<Integer, BinRegion> binRegionMap = new ConcurrentHashMap<>();
    private static ArrayList<BinRegion> activeRegions = new ArrayList<>();
    public static ArrayList<Rectangle> ghostRegions = new ArrayList<>();

    private static Random random = new Random();

    public static int newestRegionId = 0;

    public static ArrayList<BinRegion> getActiveRegions() {
        return activeRegions;
    }

    public static BinRegion createNewRegion(Playground playgroundParent) {
        BinRegion newRegion = new BinRegion(newestRegionId, playgroundParent);
        binRegionMap.put(newestRegionId, newRegion);
        newestRegionId++;
        return newRegion;
    }

    public static void createGhostRegion(int x, int y, int width, int height, int id) {
        Rectangle newGhost = new Rectangle(x, y, width, height);
        newGhost.setId(Integer.toString(id));
        newGhost.setOpacity(0);
        newGhost.setMouseTransparent(true);
        ghostRegions.add(newGhost);
        PlaygroundHandler.playground.getChildren().add(newGhost);
    }

    public static void setActiveRegions(ArrayList<Integer> toDraw) {
        toDraw.forEach((index) -> activeRegions.add(binRegionMap.get(index)));
        for (int i = 0; i < binRegionMap.size(); i++) {
            if (toDraw.contains(i) && !PlaygroundHandler.playground.getChildren().contains(binRegionMap.get(i)))
                PlaygroundHandler.playground.getChildren().add(0, binRegionMap.get(i));
            else if (!toDraw.contains(i))
                PlaygroundHandler.playground.getChildren().remove(binRegionMap.get(i));
        }
    }

    public static BinRegion getRandomRegion(Playground playground) {
        if (playground.getMaxBinRegionId() - playground.getMinBinRegionId() > 0)
            return binRegionMap.get(playground.getMinBinRegionId() + random.nextInt(playground.getMaxBinRegionId() - playground.getMinBinRegionId()));
        else
            return binRegionMap.get(playground.getMinBinRegionId());
    }
}