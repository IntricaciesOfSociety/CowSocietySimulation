package societalProductivity;

import cowParts.Cow;

import java.util.Random;

/**
 * TODO: Implement me!
 */
public class Role {

    Random random = new Random();

    /**
     * TEMP
     */
    public Role(Cow cowToCheck) {
        if (random.nextInt(100) < 10)
            cowToCheck.setJob("miningRock");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("choppingWood");
    }
}
