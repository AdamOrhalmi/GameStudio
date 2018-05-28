package sk.tuke.gamestudio.pexeso;

import sk.tuke.gamestudio.client.Game;

public class Pexeso extends Game {

    private static long startMillis;
    static ConsoleUI cui;

    public Pexeso(String name) {
        cui = new ConsoleUI();
        startMillis = System.currentTimeMillis();

        cui.newGame( name);
    }

    public static int getPlayingSeconds() {
        return (int) (System.currentTimeMillis() - startMillis) / 1000;
    }

    @Override
    public String getGameName() {
        return "pexeso";
    }
}
