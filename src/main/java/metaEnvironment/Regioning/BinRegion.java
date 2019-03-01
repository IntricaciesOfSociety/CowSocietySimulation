package metaEnvironment.Regioning;

import infrastructure.buildingTypes.*;

import java.util.ArrayList;

public abstract class BinRegion {

    private ArrayList<AgriculturalBuilding> agriculturalBuildings = new ArrayList<>();
    private ArrayList<CommercialBuilding> commercialBuildings = new ArrayList<>();
    private ArrayList<GovernmentalBuilding> governmentalBuildings = new ArrayList<>();
    private ArrayList<IndustrialBuilding> industrialBuildings = new ArrayList<>();
    private ArrayList<ResidentialBuilding> residentialBuildings = new ArrayList<>();

    public void addAgriculturalBuilding(AgriculturalBuilding agriculturalBuilding) {
        agriculturalBuildings.add(agriculturalBuilding);
    }

    public void addCommericialBuilding(CommercialBuilding commercialBuilding) {
        commercialBuildings.add(commercialBuilding);
    }

    public void addGovernmentalBuilding(GovernmentalBuilding governmentalBuilding) {
        governmentalBuildings.add(governmentalBuilding);
    }

    public void addIndustrialBuilding(IndustrialBuilding industrialBuilding) {
        industrialBuildings.add(industrialBuilding);
    }

    public void addResidentialBuilding(ResidentialBuilding residentialBuilding) {
        residentialBuildings.add(residentialBuilding);
    }

    public abstract ArrayList<GenericBuilding> getAllBuildings();
}
