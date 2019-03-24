import java.util.*;
import java.text.ParseException;

import constructions.units.*;

public class Goal {

    public static boolean goalAchieved = false;

     // goals are given as:
     // 16 Marines, 8 Hellions, 3 Medivacs
     public static HashMap<String, Integer> setGoal(String[] args) {

        HashMap<String, Integer> goal = new HashMap<>();
        goal.put(args[1].toLowerCase(), Integer.valueOf(args[0]));

        return goal;
    }
}
