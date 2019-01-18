package cowParts.cowAI;

import cowParts.Cognition;
import cowParts.Cow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NaturalSelection {

    //Ordered from greatest to least fittest cow
    ArrayList<Cow> fitnessList = new ArrayList<>();

    public static void calculateFitness(@NotNull Cow cowToCheck) {
        int survivalFitness = 0;
        int fameFitness = 0;

        Cognition cowCog = cowToCheck.self;

    }

    public static void selection() {

    }

    public static void crossover() {

    }

    public static void mutation() {

    }
}
