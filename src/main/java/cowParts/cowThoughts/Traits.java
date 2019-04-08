package cowParts.cowThoughts;

import cowParts.creation.Cow;

import java.util.Random;

public class Traits {

    private static Random random = new Random();

    //limited 1 - 10
    private int carefulness;
    private int creativeness;
    private int sensitivity;
    private int sociability;
    private int harmony;
    private int neuroticism;

    public Traits() {
        randomizeTraits();
    }

    public static Traits crossover(Cow parent1, Cow parent2) {
        Traits crossoverTraits = new Traits();
        crossoverTraits.clear();

        crossoverTraits.setCarefulness(Math.max(parent1.personality.getCarefulness(), parent2.personality.getCarefulness()));
        crossoverTraits.setCreativeness(Math.max(parent1.personality.getCreativeness(), parent2.personality.getCreativeness()));
        crossoverTraits.setSensitivity(Math.max(parent1.personality.getSensitivity(), parent2.personality.getSensitivity()));
        crossoverTraits.setSociability(Math.max(parent1.personality.getSociability(), parent2.personality.getSociability()));
        crossoverTraits.setHarmony(Math.max(parent1.personality.getHarmony(), parent2.personality.getHarmony()));
        crossoverTraits.setNeuroticism(Math.max(parent1.personality.getNeuroticism(), parent2.personality.getNeuroticism()));

        mutate(crossoverTraits);
        return crossoverTraits;
    }

    private static void mutate(Traits crossoverTraits) {
        if (random.nextInt(3) == 0)
            crossoverTraits.setCarefulness(random.nextInt(10));
        if (random.nextInt(3) == 0)
            crossoverTraits.setCreativeness(random.nextInt(10));
        if (random.nextInt(3) == 0)
            crossoverTraits.setSensitivity(random.nextInt(10));
        if (random.nextInt(3) == 0)
            crossoverTraits.setSociability(random.nextInt(10));
        if (random.nextInt(3) == 0)
            crossoverTraits.setHarmony(random.nextInt(10));
        if (random.nextInt(3) == 0)
            crossoverTraits.setNeuroticism(random.nextInt(10));
    }

    private void randomizeTraits() {
        carefulness = random.nextInt(11);
        creativeness = random.nextInt(11);
        sensitivity = random.nextInt(11);
        sociability = random.nextInt(11);
        harmony = random.nextInt(11);
        neuroticism = random.nextInt(11);
    }

    public int getCarefulness() {
        return carefulness;
    }

    private void setCarefulness(int carefulness) {
        this.carefulness = carefulness;
    }

    public int getCreativeness() {
        return creativeness;
    }

    private void setCreativeness(int creativeness) {
        this.creativeness = creativeness;
    }

    public int getSensitivity() {
        return sensitivity;
    }

    private void setSensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public int getSociability() {
        return sociability;
    }

    private void setSociability(int sociability) {
        this.sociability = sociability;
    }

    public int getHarmony() {
        return harmony;
    }

    private void setHarmony(int harmony) {
        this.harmony = harmony;
    }

    public int getNeuroticism() {
        return neuroticism;
    }

    private void setNeuroticism(int neuroticism) {
        this.neuroticism = neuroticism;
    }

    private void clear() {
        carefulness = 0;
        creativeness = 0;
        sensitivity = 0;
        sociability = 0;
        harmony = 0;
        neuroticism = 0;
    }
}