package infrastructure.establishments.EstablishmentTypes;

import cowParts.creation.Cow;
import cowParts.actionSystem.action.GenericAction;

import java.util.ArrayList;

public class FollowingEstablishment extends GenericEstablishment {

    public FollowingEstablishment(String followingName, GenericAction memberAction, Cow followingLeader) {
        this.name = followingName;
        this.mainMemberAction = memberAction;
        this.leader = followingLeader;
    }

    @Override
    public ArrayList<Cow> getMembers() {
        return null;
    }

    @Override
    public GenericAction getMainMemberAction() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getLeader() {
        return null;
    }

    @Override
    public Object getHeadquarters() {
        return null;
    }
}
