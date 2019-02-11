package cowParts;

import cowParts.cowMovement.DecideActions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    private static ArrayList<Cow> creationList = new ArrayList<>();

    private int fertility = random.nextInt(100);

    private ArrayList<Cow> offspring = new ArrayList<>();
    private Cow spouse;

    /**
     * TODO: Implement
     * @param cowToCheck
     * @return
     */
    @Nullable
    public static Cow getProcreatingGroupMatch(Cow cowToCheck) {
        if (creationList.size() != 0)
            return creationList.get(0);
        else
            return null;
    }

    @Contract(pure = true)
    public static ArrayList<Cow> getCreationList() {
        return creationList;
    }

    /**
     * Sets the birth date of a new cow.
     * @param date The date of the new cow's birth
     */
    void setBirthday(Date date) {
        birthDay = date;
    }

    /**
     * Creates a new cow as a child and instantiates the cow as such.
     * @param parent1 The first parent of the new cow
     * @param parent2 The second parent of the new cow
     */
    public static void createChild(@NotNull Cow parent1, @NotNull Cow parent2) {
        Cow newCow = CowHandler.createCow();
        newCow.self.setAge(-newCow.self.getAge() + 1);

        DecideActions.decideActions(newCow);
        newCow.setTranslateX(parent1.getTranslateX());
        newCow.setTranslateY(parent1.getTranslateY());

        parent2.setLivingSpace(parent1.getLivingSpace());
        newCow.setLivingSpace(parent1.getLivingSpace());

        parent1.parent = true;
        parent2.parent = true;
        parent1.birth.setFertility(0);
        parent2.birth.setFertility(0);
        creationList.remove(parent1);
        creationList.remove(parent2);

        parent1.birth.offspring.add(newCow);
        parent2.birth.offspring.add(newCow);

        parent1.birth.spouse = parent2;
        parent2.birth.spouse = parent1;

        if (random.nextInt(2000) == 1)
            parent1.kill();
        if (random.nextInt(2000) == 1)
            parent2.kill();
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
