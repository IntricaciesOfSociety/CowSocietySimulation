package societyProduction.technology.branches;

import java.util.ArrayList;

public class Material implements GenericTree {

    private static ArrayList branchTree;

    public Material(BranchSelection material) {
        branchTree = GenericTree.populate(material);
    }

    @Override
    public void discoverElement() {

    }

    @Override
    public void unDiscoverElement() {

    }

    @Override
    public void progressElement(BranchElement element, int progressionAmount) {

    }

    @Override
    public void regressElement(BranchElement element, int regressionAmount) {

    }

    @Override
    public void elementLookup(int elementId) {

    }
}
