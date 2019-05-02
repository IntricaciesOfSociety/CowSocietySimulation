package societyProduction.technology.branches;

import java.util.ArrayList;
import java.util.Arrays;

public class BranchElement {

    private int progress = 0;

    private boolean discovered = false;

    private int id;

    private String name;

    private ArrayList<String> dependencies = new ArrayList<>();

    BranchElement(int id, String name, String ... dependencies) {
        this.id = id;
        this.name = name;
        this.dependencies.addAll(Arrays.asList(dependencies));
    }

    int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public String toString() {
        if (!dependencies.isEmpty())
            return "" + id + "," + name + "," + dependencies.toString().replaceAll("\\[", "").replaceAll("]","").replaceAll(" ","") + "\n";
        else
            return "" + id + "," + name + "\n";
    }

    void addDependency(String newDependency) {
        dependencies.add(newDependency);
    }

    private void increaseProgress(int increaseAmount) {
        progress += increaseAmount;
    }

    public void discover() {
        discovered = true;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public int getProgress() {
        return progress;
    }
}
