package technology.branches;

public enum BranchSelection {

    CHEMISTRY("src/main/saves/CITYNAME/treeSave/Cultural.csv"),
    CULTURAL("src/main/saves/CITYNAME/treeSave/Cultural.csv"),
    MATERIAL("src/main/saves/CITYNAME/treeSave/Material.csv"),
    MATHEMATICS("src/main/saves/CITYNAME/treeSave/Mathematics.csv"),
    PHYSICS("src/main/saves/CITYNAME/treeSave/Physics.csv"),
    SOCIETAL("src/main/saves/CITYNAME/treeSave/Societal.csv");

    private final String filePath;

    BranchSelection(String filePath) {
        this.filePath = filePath;
    }

    String getFilePath() {
        return this.filePath;
    }
}

