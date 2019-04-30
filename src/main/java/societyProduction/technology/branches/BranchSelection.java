package societyProduction.technology.branches;

import metaControl.main.SaveHandler;

public enum BranchSelection {

    CHEMISTRY(SaveHandler.getSavePath() + "/treeSave/Chemistry.csv"),
    CULTURAL(SaveHandler.getSavePath() + "/treeSave/Cultural.csv"),
    MATERIAL(SaveHandler.getSavePath() + "/treeSave/Material.csv"),
    MATHEMATICS(SaveHandler.getSavePath() + "/treeSave/Mathematics.csv"),
    PHYSICS(SaveHandler.getSavePath() + "/treeSave/Physics.csv"),
    SOCIETAL(SaveHandler.getSavePath() + "/treeSave/Societal.csv");

    private final String filePath;

    BranchSelection(String filePath) {
        this.filePath = filePath;
    }

    String getFilePath() {
        return this.filePath;
    }
}

