import constructions.*;

import java.util.*;

public class Goal {
    private static HashMap<String, ArrayList<Construction>> goal1 = new LinkedHashMap<>();
    private static HashMap<String, Integer> goal2;
    private static HashMap<String, Integer> goal3;

    public static HashMap<String, ArrayList<Construction>> getGoal1() {
        return goal1;
    }

    public static void setGoals() {
        ArrayList<Construction> marines = new ArrayList<>();
        marines.add(new Marine());
        marines.add(new Marine());
        marines.add(new Marine());
        marines.add(new Marine());
        marines.add(new Marine());
        marines.add(new Marine());

        goal1.put(Marine.IDENT, marines);

    }
}
