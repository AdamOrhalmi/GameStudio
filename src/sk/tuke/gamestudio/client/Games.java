package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.kamene.Kamene;
import sk.tuke.gamestudio.minesweeper.Minesweeper;
import sk.tuke.gamestudio.pexeso.Pexeso;

public enum Games {

    Minesweeper(sk.tuke.gamestudio.minesweeper.Minesweeper.class),
    Kamene(sk.tuke.gamestudio.kamene.Kamene.class),
    Pexeso(sk.tuke.gamestudio.pexeso.Pexeso.class);

    private Class<?> gameClass;

    Games(Class<?> gameClass) {
        this.gameClass = gameClass;
    }

    public void startGame(String username) {
        try {
            this.gameClass.getConstructor(String.class).newInstance(username);
        } catch (Exception e) {
            System.err.println("Error starting game "+this.name());
            e.printStackTrace();
        }
    }
    public String getGameName(){
        return gameClass.getSimpleName();
    }

}
