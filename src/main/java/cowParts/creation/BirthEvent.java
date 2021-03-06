package cowParts.creation;

import cowParts.CowHandler;
import cowParts.cowAI.NaturalSelection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Handles the creation event and creation related values for all cows. Includes new cow creation and fertility tracking.
 */
public class BirthEvent {

    static Random random = new Random();

    //TODO: Make an actual date (Not just a time)
    private Date birthDay;

    private int fertility = random.nextInt(100);

    private ArrayList<Cow> offspring = new ArrayList<>();
    private Cow spouse;

    /**
     * Sets the birth date of a new cow.
     * @param date The date of the new cow's birth
     */
    public void setBirthday(Date date) {
        birthDay = date;
    }

    /**
     * Creates a new cow as a child and instantiates the cow as such.
     * @param parent1 The first parent of the new cow
     * @param parent2 The second parent of the new cow
     */
    public static void createChild(@NotNull Cow parent1, @NotNull Cow parent2) {
        parent1.parent = true;
        parent2.parent = true;
        parent1.birth.setFertility(0);
        parent2.birth.setFertility(0);

        parent1.birth.spouse = parent2;
        parent2.birth.spouse = parent1;
        NaturalSelection.calculateFitness(parent1);
        NaturalSelection.calculateFitness(parent2);

        if (random.nextInt(2000) == 1)
            parent1.kill();
        if (random.nextInt(2000) == 1)
            parent2.kill();

        Cow newCow = CowHandler.createCow(parent1, parent2);

        newCow.setRegionIn(parent1.getRegionIn());
        newCow.self.setAge(-newCow.self.getAge() + 1);

        newCow.setTranslateX(parent1.getTranslateX());
        newCow.setTranslateY(parent1.getTranslateY());

        parent2.setLivingSpace(parent1.getLivingSpace());
        newCow.setLivingSpace(parent1.getLivingSpace());

        parent1.birth.offspring.add(newCow);
        parent2.birth.offspring.add(newCow);
    }

    private void setFertility(int i) {
        this.fertility = i;
    }

    /**
     * @return If the given cow is fertile or not
     */
    public boolean isFertile() {
        return fertility >= 60 + (offspring.size() * 10);
    }

    /**
     * Updates the given cow's fertility if the cow is not fertile.
     */
    public void updateFertility() {
        if (fertility <= 100)
            fertility += 5;
    }

    ArrayList<Cow> getOffspring() {
        return offspring;
    }

    Cow getSpouse() {
        return spouse;
    }
}
