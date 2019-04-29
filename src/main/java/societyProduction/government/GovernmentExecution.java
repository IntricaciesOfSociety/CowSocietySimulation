package societyProduction.government;

import cowParts.creation.Cow;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;
import org.jetbrains.annotations.Contract;
import societyProduction.government.territory.Territory;

/**
 * TODO: Implement me!
 * Creates and handles the structure for division and enforcement of political and economical power within the city.
 */
public class GovernmentExecution extends GovernmentEstablishment {

    private Election electionProcess;
    private Platform currentPlatform;

    public GovernmentExecution(String name, Cow leader, Territory initialTerritory) {
        super(name, leader, initialTerritory);
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