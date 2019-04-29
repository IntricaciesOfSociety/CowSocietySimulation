package societyProduction.technology.branches;

import technology.branches.BranchElement;
import technology.branches.BranchSelection;
import technology.branches.GenericTree;

import java.util.ArrayList;

public class Cultural implements GenericTree {

    private static ArrayList branchTree;

    public Cultural(BranchSelection cultural) {
        branchTree = GenericTree.populate(cultural);
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
