package sk.tuke.gamestudio.minesweeper.consoleui;

import  sk.tuke.gamestudio.minesweeper.core.Field;

public interface UserInterface {
    boolean newGameStarted(Field field);

    void update();

    String readLine();
    void saveScore(int time, String name);
}
