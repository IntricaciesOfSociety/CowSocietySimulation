package infrastructure.establishments.EstablishmentTypes;

import cowParts.creation.Cow;
import cowParts.actionSystem.action.GenericAction;
import societyProduction.government.territory.Territory;

import java.util.ArrayList;

public class GovernmentEstablishment extends GenericEstablishment {

    private ArrayList<Territory> territories = new ArrayList<>();

    public GovernmentEstablishment(String name, Cow leader, Territory initialTerritory) {
        this.name = name;
        this.leader = leader;
    }

    public void addTerritory(Territory newTerritory) {
        territories.add(newTerritory);
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    @Override
    public ArrayList<Cow> getMembers() {
        return null;
    }

    @Override
    GenericAction getMainMemberAction() {
        return null;
    }

    @Override
    String getName() {
        return null;
    }

    @Override
    Object getLeader() {
        return null;
    }

    @Override
    Object getHeadquarters() {
        return null;
    }
}
