import java.util.*;
import java.text.ParseException;

import constructions.units.*;

public class Goal {

    public static int precedence = 1;

    public static boolean goalAchieved = false;

     // goals are given as:
     // 16 Marines, 8 Hellions, 3 Medivacs
     public static HashMap<String, Integer> setGoal(String[] args) {

        HashMap<String, Integer> goal = new HashMap<>();
        goal.put(args[1].toLowerCase(), Integer.valueOf(args[0]));

        switch (args[1].toLowerCase()) {
            case "worker": {
                Worker.setPrecedence(precedence);
                break;
            }
            case "hellion": {
                Hellion.setPrecedence(precedence);
                break;
            }
            case "marine": {
                Marine.setPrecedence(precedence);
                break;
            }
            case "medivac": {
                Medivac.setPrecedence(precedence);
                break;
            }
            case "viking": {
                Viking.setPrecedence(precedence);
                break;
            }
        }

        return goal;
    }
}
