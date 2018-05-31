package sk.tuke.gamestudio.minesweeper;

import  sk.tuke.gamestudio.minesweeper.consoleui.ConsoleUI;
import  sk.tuke.gamestudio.minesweeper.consoleui.UserInterface;
import  sk.tuke.gamestudio.minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper{
    /** User interface. */
    private UserInterface userInterface;
    private static long startMillis;

    private static Minesweeper instance;
    private Settings settings ;

 
    /**
     * Constructor.
     */

    public Minesweeper(String name) {

        instance = this;
        userInterface = new ConsoleUI(name);
        startMillis = System.currentTimeMillis();

        System.out.println("Welcome to minefield! Choose your difficulty: ");
        System.out.println("1. Beginner (9x9, 10 mines)");
        System.out.println("2. Intermadiate (16x16, 40 mines)");
        System.out.println("3. Expert (16x30, 99 mines)");
        String input = userInterface.readLine();
        switch(input){
            case "1":
                settings = Settings.BEGINNER;
                break;
            case "2":
                settings = Settings.INTERMEDIATE;
                break;
            case "3":
                settings = Settings.EXPERT;
                break;
            default:
                settings = Settings.load();
        }

        Field field = new Field(settings.getRowCount(), settings.getColumnCount(), settings.getMineCount());

        if(userInterface.newGameStarted(field)){

            int currentMillis =getPlayingSeconds();
            userInterface.saveScore(currentMillis,name);
        }

    }

    public static int getPlayingSeconds(){
        return (int)(System.currentTimeMillis() - startMillis)/1000;
    }



}
