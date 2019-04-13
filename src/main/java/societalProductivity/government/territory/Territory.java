package societalProductivity.government.territory;

public class Territory {

    private int minXBinRegion;
    private int maxXBinRegion;
    private int minYBinRegion;
    private int maxYBinRegion;

    public Territory(int minXBinRegion, int maxXBinRegion, int minYBinRegion, int maxYBinRegion) {
        this.minXBinRegion = minXBinRegion;
        this.maxXBinRegion = maxXBinRegion;
        this.minYBinRegion = minYBinRegion;
        this.maxYBinRegion = maxYBinRegion;
    }
}
