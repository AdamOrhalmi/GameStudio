package sk.tuke.gamestudio.kamene;

import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;

import java.io.*;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements Serializable {

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private Field field;
    private String regex = "([wasdc]|up|down|left|right|save)";
    private static final String SAVE_FILE = "savedgame.bin";
    private int timeGameEnded;
    ScoreRestServiceClient scoreServiceClient = new ScoreRestServiceClient();
    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGame( String name) {

        this.field = new Field();


        System.out.println("Welcome to Kamene! Would you like to load a previous board?(y/n)");
        String command = this.readLine().toLowerCase();
        if (command.equals("y")) {
            load();
        } else {
        System.out.println(" Would you like to use custom board?(y/n)");
         command = this.readLine();
        if (command.equals("y")) {
            System.out.println("pick a integer number for size of board(minimum 2)");
            command = this.readLine();
            if(command!=null){
            int size = Integer.parseInt(command);
            if (size > 1) {
                this.field = new Field(size, size);
            }
            else System.err.println("wrong size. starting game on default size.");
            }
            else System.err.println("input error. starting game on default size.");
        }
        else this.field = new Field();
    }
        do {
            update();
            processInput();
        } while (!field.isSolved());
        update();
        timeGameEnded = Kamene.getPlayingSeconds();
        System.out.println("congratulations, you have solved the game in " + timeGameEnded + " seconds!");
        Score s = new Score(name, "Kamene", (313*field.getRows()*field.getColumns())-timeGameEnded, java.util.Date.from(Instant.now()));
        scoreServiceClient.addScore(s);
        }

    private void update() {
        System.out.println("Time passed: " + Kamene.getPlayingSeconds());
        for (int row = 0; row < field.getRows(); row++) {
            for (int column = 0; column < field.getColumns(); column++) {
                Stone s = field.getStone(row, column);
                if (s.getValue() != field.getStonesNr()) {

                    System.out.print(s.getValue() + " ");
                    if (s.getValue() < 10) {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print("[] ");
                }
            }
            System.out.println();
        }
    }

    private void processInput() {
        String command = this.readLine().toLowerCase();

        try {
            handleInput(command);
        } catch (WrongFormatException e) {

            System.err.println(e.getMessage());
            return;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        boolean moved = false;
        if (matcher.matches()) {
            switch (command) {
                case "up":
                case "w":
                    moved = field.switchNullStoneWith(-1, 0);
                    break;
                case "down":
                case "s":
                    moved = field.switchNullStoneWith(1, 0);
                    break;
                case "left":
                case "a":
                    moved = field.switchNullStoneWith(0, -1);
                    break;
                case "right":
                case "d":
                    moved = field.switchNullStoneWith(0, 1);
                    break;
                case "c":
                    field.solve();
                    return;
                case "save":
                    save();
                    return;
            }
            if (!moved)
                System.err.println("can't move there!");
        }
    }

    private void handleInput(String input) throws WrongFormatException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!(matcher.matches())) {
            throw new WrongFormatException("Wrong command format");
        }

    }

    private void save() {

        try {
            FileOutputStream os = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this.field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            FileInputStream is = new FileInputStream(SAVE_FILE);
            ObjectInputStream ois = new ObjectInputStream(is);
            Field newField = (Field) ois.readObject();
            this.field = newField;

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }


}




