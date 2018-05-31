package sk.tuke.gamestudio.pexeso;

public class Pexeso{

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


}
