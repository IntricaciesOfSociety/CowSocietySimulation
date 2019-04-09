package cowParts.cowThoughts.traitsSystem;

import org.jetbrains.annotations.Contract;

public class Interests {

    //limited 1 - 10
    private int carefulness;
    private int creativeness;
    private int sensitivity;
    private int sociability;
    private int harmony;
    private int neuroticism;

    public Interests() {

    }

    @Contract(pure = true)
    private Interests(int carefulness, int creativeness, int sensitivity, int sociability, int harmony, int neuroticism) {
        this.carefulness = carefulness;
        this.creativeness = creativeness;
        this.sensitivity = sensitivity;
        this.sociability = sociability;
        this.harmony = harmony;
        this.neuroticism = neuroticism;
    }

    public static final Interests ENTREPRENEURSHIP = new Interests(0, 10, 0, 5, 0, 5);
    public static final Interests LEADERSHIP = new Interests(0, 0, 0, 10, 10, 0);

    int getCarefulness() {
        return carefulness;
    }

    int getCreativeness() {
        return creativeness;
    }

    int getSensitivity() {
        return sensitivity;
    }

    int getSociability() {
        return sociability;
    }

    int getHarmony() {
        return harmony;
    }

    int getNeuroticism() {
        return neuroticism;
    }
}