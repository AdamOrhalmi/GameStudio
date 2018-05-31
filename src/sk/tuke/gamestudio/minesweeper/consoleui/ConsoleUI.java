package sk.tuke.gamestudio.minesweeper.consoleui;

import sk.tuke.gamestudio.client.ExitException;
import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;
import  sk.tuke.gamestudio.minesweeper.Minesweeper;
import  sk.tuke.gamestudio.minesweeper.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static  sk.tuke.gamestudio.minesweeper.core.Tile.State.*;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
   private Field field;
   private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   private ScoreRestServiceClient scoreServiceClient = new ScoreRestServiceClient();
   private String name;
   private Pattern pattern = Pattern.compile("([XMOC])(([A-Z])([\\d]+))?");


public ConsoleUI(String name){
    this.name = name;
}
    public String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Starts the game.
     * @param field field of mines and clues
     */
    @Override
    public boolean newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            if(field.getState() == GameState.SOLVED ){
                update();
                System.out.println("Congratulations, "+name+", you have solved this puzzle!");

               return true;
            } else if (field.getState() == GameState.FAILED ){
                update();
                System.out.println("Sorry, you lost!");
               return false;

            }
        } while(true);


    }
    
    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        //unmarked mines left:
        System.out.println(" Unmarked mines left: "+ field.getRemainingMineCount() + "  Time passsed: "+Minesweeper.getPlayingSeconds());

        // numbers of columns
        System.out.print("  ");

        for (int column = 0; column<field.getColumnCount(); column++) System.out.printf("%3d", column);
        System.out.println();
        //other lines- start with letter of row and write out the tiles
        for (int row = 0; row<field.getRowCount(); row++){
            char rowChar = (char) (65 + row);
            System.out.print(rowChar +" ");


            for (int column = 0; column<field.getColumnCount(); column++){
                if (field.getTile(row, column).getState() == (CLOSED)){
                    System.out.print( "  -");

                }
                if (field.getTile(row, column).getState() == (OPEN)){
                    if (field.getTile(row, column) instanceof Mine){
                        System.out.print("  X");
                    }
                    if (field.getTile(row, column) instanceof Clue){
                        System.out.print("  " +((Clue) field.getTile(row, column)).getValue());
                    }


                }
                if (field.getTile(row, column).getState() == (MARKED)){
                    System.out.print("  M");

                }
            }

            System.out.println();


        }
    }
    
    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Choose your action: X- exit game, MA1- mark A1 tile, OB4- open B4");
        String command = this.readLine().toUpperCase();

       try{
           handleInput(command);
       } catch (WrongFormatException e){

           System.err.println(e.getMessage());
           return;
       }


        Matcher matcher = pattern.matcher(command);


        if(matcher.matches()) {
            if (command.equals("C")) {
                field.solve();
                return;
            }

            char commandChar = matcher.group(1).charAt(0);
            if (commandChar == 'X') {
                System.out.println("Goodbye, " + name + ".");
                throw new ExitException();
            }
            try{

            char rowChar = matcher.group(2).charAt(0);
            int row = rowChar - 'A';
            int column = Integer.parseInt(matcher.group(2).substring(1));


            switch (commandChar) {

                case 'M':
                    field.markTile(row, column);
                    break;
                case 'O':
                    field.openTile(row, column);
                    break;

            }
        }catch(Exception e){
                System.out.println("Wrong input, please try again.");
                processInput();
            }

        }else {
            System.out.println("Wrong input, please try again.");
            processInput();
        }
    }

    private void handleInput (String input)throws WrongFormatException{

        Matcher matcher = pattern.matcher(input);
        if(!(matcher.matches())){
           throw new WrongFormatException("Wrong command format");
        }

    }
    public void saveScore (int time, String name){

        int points = field.getMineCount()*field.getRowCount()*field.getColumnCount();
        Score s = new Score(name, "Minesweeper", points-time, java.util.Date.from(Instant.now()));
        scoreServiceClient.addScore(s);
    }
}
