package cowParts;

import cowMovement.Movement;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Random;

import static cowParts.Cow.cowList;

public class BirthEvent {

    static Random random = new Random();

    private String parent1;
    private String parent2;

    private Date birthDay;
    private int fertility = random.nextInt(100);

    void setBirthday(Date date) {
        birthDay = date;
    }

    static void createChild(@NotNull Cow parent1, @NotNull Cow parent2) {
        parent1.parent = true;
        parent2.parent = true;

        Cow newCow = new Cow();
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

    boolean isFertile() {
        return fertility >= 60;
    }

    public void updateFertility() {
        if (fertility < 100)
            fertility += 5;
    }
}
