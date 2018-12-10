package resourcesManagement;

import org.jetbrains.annotations.NotNull;

/**
 * Defines the amount of resources needed to construct the given structure.
 */
public class ResourceRequirement {

    private int rock;
    private int wood;
    private int power;

    /**
     * Creates a resourceRequirement
     * @param rock The rock needed
     * @param wood The wood needed
     * @param power The power needed
     */
    public ResourceRequirement(int rock, int wood, int power) {
        this.rock = rock;
        this.wood = wood;
        this.power = power;
    }

    /**
     * Changes the requirement based off the given resource and its amount.
     * @param resourceToModify The resource to modify
     * @param modificationAmount The amount of the resource to modify
     */
    void modifyRequirement(@NotNull String resourceToModify, int modificationAmount) {
        switch (resourceToModify) {
            case "rock":
                rock += (rock + modificationAmount >= 0) ? modificationAmount : -rock; break;
            case "wood":
                wood += (wood + modificationAmount >= 0) ? modificationAmount : -wood; break;
            case "power":
                power += (power + modificationAmount >= 0) ? modificationAmount : -power; break;
        }
    }

    /**
     * @return If all requirement elements are at 0
     */
    public boolean passesRequirements() {
        return rock == 0 && wood == 0 && power == 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "Needs " + "Rock:" + rock + " Wood:" + wood + " Power:" + power;
    }
}
