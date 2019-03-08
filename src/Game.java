import java.util.ArrayList;
import java.util.List;

public abstract class Game {

    private static List<GameState> gs = new ArrayList<>();

    public static void initialise() {
        GameState.initialiseGame();

        gs.add(GameState.getState())
    }
}
