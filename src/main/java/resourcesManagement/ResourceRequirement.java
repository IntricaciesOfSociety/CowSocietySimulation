package resourcesManagement;

public class ResourceRequirement {

    private int rock;
    private int wood;
    private int power;

    public ResourceRequirement(int rock, int wood, int power) {
        this.rock = rock;
        this.wood = wood;
        this.power = power;
    }

    void modifyRequirement(String resourceToModify, int modificationAmount) {
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
     * TODO: Implement
     * @return
     */
    public boolean passesRequirements() {
        return true;
    }

    @Override
    public String toString() {
        return "Needs " + "Rock:" + rock + " Wood:" + wood + " Power:" + power;
    }
}
