import java.util.*;
import java.text.ParseException;

import constructions.units.*;

public class Goal {

    public static boolean goalAchieved = false;

    public static int maxScore = 10;

     // goals are given as:
     // 16 Marines, 8 Hellions, 3 Medivacs
     public static HashMap<String, Integer> setGoal(String[] args) {

        String unit = args[1].toLowerCase();

        HashMap<String, Integer> goal = new HashMap<>();

        goal.put(Marine.IDENT, 0);
        goal.put(Hellion.IDENT, 0);
        goal.put(Medivac.IDENT, 0);
        goal.put(Viking.IDENT, 0);

        goal.put(unit, Integer.valueOf(args[0]));

        if (!unit.equals(Marine.IDENT) && !unit.equals(Hellion.IDENT) && !unit.equals(Medivac.IDENT) && !unit.equals(Viking.IDENT)) {
            System.out.println("Invalid goal.");
            System.exit(1);
        }

        return goal;
    }
}
