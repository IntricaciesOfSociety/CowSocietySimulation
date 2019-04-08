package societalProductivity.jobs;

import cowParts.creation.Cow;

import java.util.Random;

public class JobHandler {

    private static Random random = new Random();

    public static void assignRandomJob(Cow cowToAssign) {
        switch (random.nextInt(4)) {
            case 0: cowToAssign.setJob(JobAssignments.assignCarpenter(cowToAssign)); break;
            case 1: cowToAssign.setJob(JobAssignments.assignLumberjack(cowToAssign)); break;
            case 2: cowToAssign.setJob(JobAssignments.assignMason(cowToAssign)); break;
            case 3: cowToAssign.setJob(JobAssignments.assignMiner(cowToAssign)); break;
        }
    }
}
