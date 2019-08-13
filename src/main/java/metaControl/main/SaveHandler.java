package metaControl.main;

import org.apache.commons.io.FileUtils;
import societyProduction.urbanPlanning.CivicControl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveHandler {

    private static String savePath;

    public static void initNewSimSave() {
        createSavePath();
        createTechTreeCopies();

    }

    private static void createTechTreeCopies() {
        try {
            Files.walk(Paths.get("src/main/treeCSVs/default")).filter(Files::isRegularFile).forEach(filePath -> {
                try {
                    FileUtils.copyFile(filePath.toFile(), new File(savePath + "/treeSave/" + filePath.getFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSavePath() {
        savePath = "src/main/saves/" + CivicControl.getCityName();
        new File(savePath);
    }

    public static void saveSim() {

    }

    public static String getSavePath() {
        return savePath;
    }
}
