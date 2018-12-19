package cowParts;

import cowMovement.Movement;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Random;

import static cowParts.Cow.cowList;

/**
 * Handles the creation event and creation related values for all cows. Includes new cow creation and fertility tracking.
 */
public class BirthEvent {

    static Random random = new Random();

    private String parent1;
    private String parent2;

    //TODO: Make an actual date (Not just a time)
    private Date birthDay;

    private int fertility = random.nextInt(100);

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
    static void createChild(@NotNull Cow parent1, @NotNull Cow parent2) {
        parent1.parent = true;
        parent2.parent = true;

        Cow newCow = CowHandler.createCow();
        newCow.self.setAge(1);
        newCow.setScaleX(1.5);
        newCow.setScaleY(1.5);

        Movement.decideAction(newCow);
        newCow.relocate(parent1.getAnimatedX(), parent1.getAnimatedY());
        cowList.add(newCow);

        if (random.nextInt(2000) == 1)
            parent1.kill();
        if (random.nextInt(2000) == 1)
            parent2.kill();
    }

    /**
     * @return If the given cow is fertile or not
     */
    boolean isFertile() {
        return fertility >= 60;
    }

    /**
     * Updates the given cow's fertility if the cow is not fertile.
     */
    public void updateFertility() {
        if (fertility <= 60)
            fertility += 5;
    }
}
