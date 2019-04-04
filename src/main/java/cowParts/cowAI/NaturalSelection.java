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
        int survivalFitness = 0;
        int fameFitness = 0;

        Cognition cowCog = cowToCheck.self;

        survivalFitness += cowCog.getHunger();
        survivalFitness += cowCog.getThirst();
        survivalFitness += cowCog.getPhysicalHealth();
        survivalFitness += cowCog.getIntelligence();
        survivalFitness += cowCog.getMentalHealth();
        survivalFitness -= cowCog.getFear();

        fameFitness += cowCog.getHappiness();
        fameFitness += cowCog.getIncome();
        fameFitness += cowCog.getSavings();
        fameFitness += cowCog.getCompanionship();
        fameFitness -= cowCog.getAnger();
        fameFitness -= cowCog.getDebt();
        fameFitness -= cowCog.getBoredom();

        cowToCheck.self.setFitness(survivalFitness + fameFitness);
    }

    public static Cognition crossover(Cow parent1, Cow parent2) {
        Cognition crossoverCog = new Cognition();
        crossoverCog.clear();

        Cognition parent1Cog = parent1.self;
        Cognition parent2Cog = parent2.self;

        crossoverCog.setPhysicalHealth(Math.max(parent1Cog.getPhysicalHealth(), parent2Cog.getPhysicalHealth()));
        crossoverCog.setIntelligence(Math.max(parent1Cog.getIntelligence(), parent2Cog.getIntelligence()));
        crossoverCog.setFear(Math.min(parent1Cog.getFear(), parent2Cog.getFear()));
        crossoverCog.setMentalHealth(Math.max(parent1Cog.getMentalHealth(), parent2Cog.getMentalHealth()));
        crossoverCog.setHappiness(Math.max(parent1Cog.getHappiness(), parent2Cog.getHappiness()));
        crossoverCog.setCompanionship(Math.max(parent1Cog.getCompanionship(), parent2Cog.getCompanionship()));
        crossoverCog.setAnger(Math.min(parent1Cog.getAnger(), parent2Cog.getAnger()));
        crossoverCog.setBoredom(Math.min(parent1Cog.getBoredom(), parent2Cog.getBoredom()));

        mutate(crossoverCog);

        return crossoverCog;
    }

    public static void mutate(Cognition crossoverCog) {
        //Mutate first
        if (random.nextInt(3) == 0)
            crossoverCog.setPhysicalHealth(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setIntelligence(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setFear(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setMentalHealth(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setHappiness(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setCompanionship(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setAnger(random.nextInt(100));
        if (random.nextInt(3) == 0)
            crossoverCog.setBoredom(random.nextInt(100));
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
            if (CowHandler.liveCowList.get(i) != cowToCheck && CowHandler.liveCowList.get(i).birth.isFertile())
                return CowHandler.liveCowList.get(i);
        }
        return null;
    }
}
