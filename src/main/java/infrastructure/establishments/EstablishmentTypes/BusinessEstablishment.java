package infrastructure.establishments.EstablishmentTypes;

import cowParts.Cow;
import cowParts.actionSystem.action.GenericAction;
import infrastructure.buildings.buildingTypes.CommercialBuilding;

import java.util.ArrayList;

public class BusinessEstablishment extends GenericEstablishment {

    public BusinessEstablishment(String businessName, GenericAction workerAction, CommercialBuilding hq, Cow owner) {
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
