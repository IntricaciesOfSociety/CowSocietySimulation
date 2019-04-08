package cowParts.cowThoughts;

import org.jetbrains.annotations.Contract;

public class Interests {

    @Contract(pure = true)
    public Interests() {

    }

    public static final Interests ENTREPRENEURSHIP = new Interests();
    public static final Interests LEADERSHIP = new Interests();
    public static final Interests EDUCATION = new Interests();
    public static final Interests SPORTS = new Interests();
    public static final Interests PROGRAMMING = new Interests();
}