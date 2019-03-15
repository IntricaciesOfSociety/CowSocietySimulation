package infrastructure.establishments;

import infrastructure.establishments.EstablishmentTypes.BusinessEstablishment;
import infrastructure.establishments.EstablishmentTypes.FollowingEstablishment;
import infrastructure.establishments.EstablishmentTypes.GenericEstablishment;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;

import java.util.ArrayList;

class EstablishmentHandler {

    private static ArrayList<BusinessEstablishment> businesses = new ArrayList<>();
    private static ArrayList<GovernmentEstablishment> governments = new ArrayList<>();
    private static ArrayList<FollowingEstablishment> followings = new ArrayList<>();

    static void establishNewEstablishment(GenericEstablishment newEstablishment) {
        if (newEstablishment instanceof BusinessEstablishment)
            businesses.add((BusinessEstablishment) newEstablishment);
        else if (newEstablishment instanceof GovernmentEstablishment)
            governments.add((GovernmentEstablishment) newEstablishment);
        else if (newEstablishment instanceof FollowingEstablishment)
            followings.add((FollowingEstablishment) newEstablishment);
    }

    public ArrayList<BusinessEstablishment> getBusinesses() {
        return businesses;
    }

    public ArrayList<GovernmentEstablishment> getGovernments() {
        return governments;
    }

    public ArrayList<FollowingEstablishment> getFollowings() {
        return followings;
    }

}
