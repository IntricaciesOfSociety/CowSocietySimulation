package societyProduction.technology.branches;

import java.util.ArrayList;

public class Mathematics implements GenericTree {

    private static ArrayList branchTree;

    public Mathematics(BranchSelection mathematics) {
        branchTree = GenericTree.populate(mathematics);
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

    @Override
    public ArrayList<BranchElement> getTreeArray() {
        return branchTree;
    }
}
