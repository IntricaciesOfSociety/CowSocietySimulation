package societyProduction.government;

import cowParts.cowThoughts.Cognition;
import cowParts.creation.Cow;
import metaControl.metaEnvironment.logging.EventLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Handles every money/trade transaction.
 */
public class Economy {

    /**
     * Gives a given cow a given amount of money to add to their finance cognition.
     * @param cowToGiveMoney The cow to give money to
     * @param amountToGive The amount of money to give the cow
     */
    public static void giveMoney(@NotNull Cow cowToGiveMoney, int amountToGive) {
        Cognition cowFinances = cowToGiveMoney.self;

        cowFinances.setIncome(-100);
        cowFinances.setIncome(10);
        EventLogger.createLoggedEvent(cowToGiveMoney, "New Income", 0, "income", amountToGive);

        if (cowFinances.getDebt() == 0) {
            cowFinances.setSavings(cowFinances.getSavings() + amountToGive);
            EventLogger.createLoggedEvent(cowToGiveMoney, "Payment", 0, "savings", amountToGive);
        }

        else {
            cowFinances.setDebt(-amountToGive);
            EventLogger.createLoggedEvent(cowToGiveMoney, "Payment", 0, "debt", -amountToGive);
        }
    }
}
