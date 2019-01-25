package cowParts.cowAI;

import cowParts.Cognition;
import cowParts.Cow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NaturalSelection {

    //Ordered from greatest to least fittest cow
    private static ArrayList<Cow> fitnessList = new ArrayList<>();

    public static void calculateFitness(@NotNull Cow cowToCheck) {
        int survivalFitness = 0;
        int fameFitness = 0;

        Cognition cowCog = cowToCheck.self;

        //survivalFitness += cowCog;
        //fameFitness += cowCog;


        cowToCheck.self.setFitness(survivalFitness + fameFitness);

        if (fitnessList.size() > 0) {
            for (int i = 0; i < fitnessList.size(); i++) {
                if (fitnessList.get(i).self.getFitness() <= (survivalFitness + fameFitness))
                    fitnessList.add(i + 1, cowToCheck);
            }
        }
    }

    public static void selection() {

    }

    public static void crossover() {

    }

    public static void mutation() {

    }
}
