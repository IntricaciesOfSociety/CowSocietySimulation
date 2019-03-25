package infrastructure.establishments.EstablishmentTypes;

import cowParts.Cow;
import cowParts.actionSystem.action.GenericAction;

import java.util.ArrayList;

public class GovernmentEstablishment extends GenericEstablishment {

    public GovernmentEstablishment(String name, Cow leader) {
        this.name = name;
        this.leader = leader;
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
