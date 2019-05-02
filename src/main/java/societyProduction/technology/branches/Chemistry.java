package societyProduction.technology.branches;

import java.util.ArrayList;

public class Chemistry implements GenericTree {

    private static ArrayList<BranchElement> branchTree;

    public Chemistry(BranchSelection chemistry) {
        branchTree = GenericTree.populate(chemistry);
    }

    @Override
    public ArrayList<BranchElement> getTreeArray() {
        return branchTree;
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