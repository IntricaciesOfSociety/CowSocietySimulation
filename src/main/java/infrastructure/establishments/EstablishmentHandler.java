package infrastructure.establishments;

import infrastructure.establishments.EstablishmentTypes.BusinessEstablishment;
import infrastructure.establishments.EstablishmentTypes.FollowingEstablishment;
import infrastructure.establishments.EstablishmentTypes.GenericEstablishment;
import infrastructure.establishments.EstablishmentTypes.GovernmentEstablishment;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class EstablishmentHandler {

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

    @Contract(pure = true)
    public static ArrayList<BusinessEstablishment> getBusinesses() {
        return businesses;
    }

    @Contract(pure = true)
    public static ArrayList<GovernmentEstablishment> getGovernments() {
        return governments;
    }

    @Contract(pure = true)
    public static ArrayList<FollowingEstablishment> getFollowings() {
        return followings;
    }

}
