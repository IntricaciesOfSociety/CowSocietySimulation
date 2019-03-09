package metaEnvironment.Regioning;

import javafx.scene.shape.Rectangle;
import metaEnvironment.Playground;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class BinRegionHandler {

    public static ConcurrentHashMap<Integer, BinRegion> binRegionMap = new ConcurrentHashMap<>();
    private static ArrayList<BinRegion> activeRegions = new ArrayList<>();
    public static ArrayList<Rectangle> ghostRegions = new ArrayList<>();

    public static int newestRegionId = 0;

    public static ArrayList<BinRegion> getActiveRegions() {
        return activeRegions;
    }

    public static BinRegion createNewRegion() {
        BinRegion newRegion = new BinRegion(newestRegionId);
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
        Playground.playground.getChildren().add(newGhost);
    }

    public static void setActiveRegions(ArrayList<Integer> toDraw) {
        toDraw.forEach((index) -> activeRegions.add(binRegionMap.get(index)));
        for (int i = 0; i < binRegionMap.size(); i++) {
            if (toDraw.contains(i) && !Playground.playground.getChildren().contains(binRegionMap.get(i)))
                Playground.playground.getChildren().add(0, binRegionMap.get(i));
            else if (!toDraw.contains(i))
                Playground.playground.getChildren().remove(binRegionMap.get(i));
        }
    }
}