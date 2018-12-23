package societalProductivity;

import cowParts.Cow;

import java.util.Random;

/**
 * TODO: Implement me!
 * Assigns the job to a cow. If no new job is assigned, the default job is to spin.
 */
public class Role {

    Random random = new Random();

    /**
     * Assigns a role based off of random chance.
     */
    public Role(Cow cowToCheck) {
        if (random.nextInt(100) < 10)
            cowToCheck.setJob("woodworking");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("choppingWood");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("miningRock");
        /*else if (!Government.hasLeader())
            Government.setLeader(cowToCheck);*/
    }
}
