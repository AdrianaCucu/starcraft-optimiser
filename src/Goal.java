import java.util.*;
import java.text.ParseException;

import constructions.units.*;

public class Goal {

    public static boolean goalAchieved = false;

    public static int maxScore = 10;

     // goals are given as:
     // 16 Marines, 8 Hellions, 3 Medivacs
     public static HashMap<String, Integer> setGoal(String[] args) {

        HashMap<String, Integer> goal = new HashMap<>();
        goal.put(args[1].toLowerCase(), Integer.valueOf(args[0]));

        switch (args[1]) {
            case "worker": {
                Worker.setScore(maxScore);
                break;
            }
            case "marine": {
                Marine.setScore(maxScore);
                break;
            }
            case "hellion": {
                Hellion.setScore(maxScore);
                break;
            }
            case "medivac": {
                Medivac.setScore(maxScore);
                break;
            }
            case "viking": {
                Viking.setScore(maxScore);
                break;
            }
            default: {
                System.out.println("Invalid goal.");
                System.exit(1);
            }
        }

        return goal;
    }
}
