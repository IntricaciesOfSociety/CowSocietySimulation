package societyProduction.technology.branches;

import java.util.ArrayList;

public class Physics implements GenericTree {

    private static ArrayList branchTree;

    public Physics(BranchSelection physics) {
        branchTree = GenericTree.populate(physics);
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
