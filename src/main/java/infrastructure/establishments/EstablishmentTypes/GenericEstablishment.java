package infrastructure.establishments.EstablishmentTypes;

import cowParts.Cow;
import cowParts.actionSystem.action.GenericAction;
import terrain.Tile;

import java.util.ArrayList;

public abstract class GenericEstablishment {

    private Object leader;
    private ArrayList<Cow> members = new ArrayList<>();

    private GenericAction mainMemberAction;

    private Object headquarters;
    ArrayList<Tile> locations = new ArrayList<>();

    private String name;

    abstract public ArrayList<Cow> getMembers();

    abstract GenericAction getMainMemberAction();

    abstract String getName();

    abstract Object getLeader();

    abstract Object getHeadquarters();
}