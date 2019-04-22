package infrastructure.establishments.EstablishmentTypes;

import cowParts.creation.Cow;
import cowParts.actionSystem.action.GenericAction;
import infrastructure.terrain.Tile;

import java.util.ArrayList;

public abstract class GenericEstablishment {

    Object leader;
    ArrayList<Cow> members = new ArrayList<>();

    GenericAction mainMemberAction;

    Object headquarters;
    ArrayList<Tile> locations = new ArrayList<>();

    String name;

    abstract public ArrayList<Cow> getMembers();

    abstract GenericAction getMainMemberAction();

    abstract String getName();

    abstract Object getLeader();

    abstract Object getHeadquarters();
}