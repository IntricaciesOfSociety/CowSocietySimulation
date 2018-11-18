package cowParts;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and tracks the social interactions between cows. A relation is the ties between two cows, while the relation
 * value is the specific value associated to that relationship as an int.
 */
public class Social {

    //A cow whose index is in relations is at the same index in relationsValues.
    private ArrayList<String> relations = new ArrayList<>();
    private ArrayList<Integer> relationsValues = new ArrayList<>();

    /**
     * Changes a relation value between the the firstCow and the secondCow which are found from the given IDs.
     * @param firstCow One of the cows who's relation with the other given cow is being changed.
     * @param secondCow One of the cows who's relation with the other given cow is being changed.
     * @param modificationDelta The change amount that the relationValue is to change by.
     */
    public static void modifyRelationValue(@NotNull Cow firstCow, @NotNull Cow secondCow, int modificationDelta) {
        Social cow1Socials = firstCow.socialRelations;
        Social cow2Socials = secondCow.socialRelations;

        //The index of the cow in the opposite cows social list
        int indexOfCow1 = cow2Socials.relations.indexOf(firstCow.getId());
        int indexOfCow2 = cow1Socials.relations.indexOf(secondCow.getId());

        cow1Socials.relationsValues.set(indexOfCow2, cow1Socials.relationsValues.get(indexOfCow2 + modificationDelta));
        cow2Socials.relationsValues.set(indexOfCow1, cow2Socials.relationsValues.get(indexOfCow1 + modificationDelta));
    }

    /**
     * Creates a new relationship between the two given cows. Only called if no previous relationships have occured between
     * the two given cows.
     * @param firstCow The first cow to create a relationship for
     * @param secondCow The second cow to create a relationship for
     */
    static void newRelation(@NotNull Cow firstCow, @NotNull Cow secondCow) {
        firstCow.socialRelations.relations.add(secondCow.getId());
        secondCow.socialRelations.relations.add(firstCow.getId());
        firstCow.socialRelations.relationsValues.add(100);
        secondCow.socialRelations.relationsValues.add(100);
    }

    /**
     * @param cowToGetRelationships The cow that is being queried for relationship statuses.
     * @return The list of cows that the given given cow has a relation with.
     */
    @Contract(pure = true)
    public static ArrayList getAllRelations(@NotNull Cow cowToGetRelationships) {
        return cowToGetRelationships.socialRelations.relations;
    }

    /**
     * @return If the two given cows have any relations between each other.
     */
    @Contract(pure = true)
    public static boolean relationExists(@NotNull Cow firstCow, @NotNull Cow secondCow) {
        return firstCow.socialRelations.relations.contains(secondCow.getId());
    }
}
