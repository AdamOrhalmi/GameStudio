package sk.tuke.gamestudio.kamene;

import java.io.Serializable;

public class Kamene implements Serializable {
    private static long startMillis;
    static ConsoleUI cui;

    public Kamene(String name) {
        cui = new ConsoleUI();
        startMillis = System.currentTimeMillis();

        cui.newGame( name);
    }

    public static int getPlayingSeconds() {
        return (int) (System.currentTimeMillis() - startMillis) / 1000;
    }


}
