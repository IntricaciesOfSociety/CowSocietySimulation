package societalProductivity.government;

import cowParts.Cow;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;
import org.jetbrains.annotations.Contract;

/**
 * TODO: Implement me!
 * Creates and handles the structure for division and enforcement of political and economical power within the city.
 */
public class GovernmentExecution extends GovernmentEstablishment {

    private Election electionProcess;
    private Platform currentPlatform;

    public GovernmentExecution(String name, Cow leader) {
        super(name, leader);
    }

    public void startGovernment() {
        setNewPlatform();
        setElectionProcess();
    }

    private void setElectionProcess() {
        electionProcess = new Election();
    }

    private void setNewPlatform() {
        currentPlatform = new Platform();
    }

    @Contract(pure = true)
    public Platform getCurrentPlatform() {
        return currentPlatform;
    }
}