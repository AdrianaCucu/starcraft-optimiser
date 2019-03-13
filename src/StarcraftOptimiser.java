import com.sun.corba.se.spi.orbutil.threadpool.Work;
import constructions.*;

public class StarcraftOptimiser {

    public static void main(String[] args) {

        boolean goalAchieved = false;
        GameState.initialise();
        System.out.println(GameState.getCompletedConstructions().get(Worker.IDENT));

        //Game.optimise();

    }
}
