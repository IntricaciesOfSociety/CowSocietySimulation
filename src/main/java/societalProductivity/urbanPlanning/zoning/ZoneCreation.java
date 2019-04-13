package societalProductivity.urbanPlanning.zoning;

import societalProductivity.urbanPlanning.zoning.implementations.*;

public class ZoneCreation {

    public static ZoningSector createAgriculturalSector() {
        return new AgriculturalSector();
    }

    public static ZoningSector createCommercialSector() {
        return new CommercialSector();
    }

    public static ZoningSector createGovernmentalSector() {
        return new GovernmentalSector();
    }

    public static ZoningSector createIndustrialSector() {
        return new IndustrialSector();
    }

    public static ZoningSector createResidentaialSector() {
        return new ResidentialSector();
    }
}
