package cowParts.cowAI;

import cowParts.Cow;
import cowParts.CowHandler;
import cowParts.cowThoughts.Cognition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.Random;

public class NaturalSelection {
    private static Random random = new Random();

    public static void calculateFitness(@NotNull Cow cowToCheck) {
        int survivalFitness = random.nextInt(900);
        int fameFitness = random.nextInt(900);

        Cognition cowCog = cowToCheck.self;

        //survivalFitness += cowCog;
        //fameFitness += cowCog;

        cowToCheck.self.setFitness(survivalFitness + fameFitness);
    }

    public static Cognition crossover(Cow parent1, Cow parent2) {
        Cognition crossoverCog = new Cognition();
        //Crossover first
        mutate();
        return crossoverCog;
    }

    private static void mutate() {
        //Mutate first

    }

    public static void rankFitness() {
        CowHandler.liveCowList.sort(Comparator.comparingInt(cow -> cow.self.getFitness()));
    }


    /**
     * TODO: Implement
     * @param cowToCheck
     * @return
     */
    @Nullable
    public static Cow getMostFitAndFertile(Cow cowToCheck) {
        for (int i = 0; i < CowHandler.liveCowList.size(); i++ ) {
            if (CowHandler.liveCowList.get(i).birth.isFertile())
                return CowHandler.liveCowList.get(i);
        }
        return null;
    }
}
