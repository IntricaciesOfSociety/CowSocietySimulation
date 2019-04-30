package societyProduction.technology;

import metaControl.main.SaveHandler;
import societyProduction.technology.branches.*;

public class BranchHandler {

    private static Chemistry chemBranch;
    private static Cultural cultBranch;
    private static Material matBranch;
    private static Mathematics mathBranch;
    private static Physics physBranch;
    private static Societal socBranch;

    public static void init() {
        createBranchFiles();
        createBranches();
    }

    private static void createBranchFiles() {
        SaveHandler.initNewSimSave();
    }

    private static void createBranches() {
        chemBranch = new Chemistry(BranchSelection.CHEMISTRY);
        cultBranch = new Cultural(BranchSelection.CULTURAL);
        matBranch = new Material(BranchSelection.MATERIAL);
        mathBranch = new Mathematics(BranchSelection.MATHEMATICS);
        physBranch = new Physics(BranchSelection.PHYSICS);
        socBranch = new Societal(BranchSelection.SOCIETAL);
    }

    public static Chemistry getChemBranch() {
        return chemBranch;
    }

    public static Cultural getCultBranch() {
        return cultBranch;
    }

    public static Material getMatBranch() {
        return matBranch;
    }

    public static Mathematics getMathBranch() {
        return mathBranch;
    }

    public static Physics getPhysBranch() {
        return physBranch;
    }

    public static Societal getSocBranch() {
        return socBranch;
    }
}