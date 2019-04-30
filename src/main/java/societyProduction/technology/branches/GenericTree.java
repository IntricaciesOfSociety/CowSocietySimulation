package societyProduction.technology.branches;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public interface GenericTree {

    static ArrayList<BranchElement> populate(BranchSelection selection) {

        ArrayList<BranchElement> tree = new ArrayList<>();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(selection.getFilePath()));

            if (br.ready()) {
                String line = br.readLine();
                while (line != null) {
                    String[] values = line.split("[,]");

                    BranchElement newElement = new BranchElement(Integer.parseInt(values[0]), values[1]);
                    if (values.length > 2) {
                        for (int i = 2; i < values.length; i++) {
                            newElement.addDependency(values[i]);
                        }
                    }
                    tree.add(newElement);
                    line = br.readLine();
                }
                System.out.println(tree);
            }
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND (or parse error)???? " + selection.getFilePath());
        }

        return tree;
    }

    void discoverElement();

    void unDiscoverElement();

    void progressElement(BranchElement element, int progressionAmount);

    void regressElement(BranchElement element, int regressionAmount);

    void elementLookup(int elementId);
}