package infrastructure.establishments;

import cowParts.Cow;
import cowParts.actionSystem.action.GenericAction;
import infrastructure.buildings.buildingTypes.CommercialBuilding;
import infrastructure.establishments.EstablishmentTypes.BusinessEstablishment;
import infrastructure.establishments.EstablishmentTypes.FollowingEstablishment;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;

public class EstablishmentCreation {

    public static BusinessEstablishment createBusiness(String businessName, GenericAction workerAction, CommercialBuilding hq, Cow owner) {
        BusinessEstablishment newBusiness = new BusinessEstablishment(businessName, workerAction, hq, owner);
        EstablishmentHandler.establishNewEstablishment(newBusiness);
        return newBusiness;
    }

    public static FollowingEstablishment createFollowing(String followingName, GenericAction memberAction, Cow followingLeader) {
        FollowingEstablishment newFollowing = new FollowingEstablishment(followingName, memberAction, followingLeader);
        EstablishmentHandler.establishNewEstablishment(newFollowing);
        return newFollowing;
    }

    //TODO: Implement
    public static GovernmentEstablishment createGovernment(FollowingEstablishment following) {
        GovernmentEstablishment newGovernment = new GovernmentEstablishment(following.getName(), (Cow) following.getLeader());
        EstablishmentHandler.establishNewEstablishment(newGovernment);
        return newGovernment;
    }
}
