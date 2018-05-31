package sk.tuke.gamestudio.pexeso;


import sk.tuke.gamestudio.client.ScoreRestServiceClient;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.kamene.WrongFormatException;

import java.io.*;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI implements Serializable {

    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private Field field;
    private String regex = "([A-Za-z])([\\d]*)?";
    private static final String SAVE_FILE = "savedgame.bin";
    private int timeGameEnded;
    private ScoreRestServiceClient scoreServiceClient = new ScoreRestServiceClient();

    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void newGame(String name) {

        this.field = new Field(6, 6);

        System.out.println("Welcome to Pexeso! Would you like to use custom board?(y/n)");
        String command = this.readLine().toLowerCase();
        if (command.equals("y")) {
            System.out.println("pick a integer number for size of board(minimum 2)");
            command = this.readLine();
            int size = 0;

            while (size == 0) {

                try {
                    size = Integer.parseInt(command);
                } catch (NumberFormatException e) {
                    System.err.println("Wrong input format. Try again.");
                }
            }
            if (size > 1) {
                this.field = new Field(size, size);
            } else {
                System.err.println("wrong size. starting game on default size.");

                this.field = new Field(6, 6);
            }
        } else this.field = new Field(6, 6);

        do {
            update();
            processInput();

        } while (!field.isSolved());
        update();
        timeGameEnded = Pexeso.getPlayingSeconds();
        System.out.println("Congratulations, you have solved the game in " + timeGameEnded + " seconds!");
        Score s = new Score(name, "Pexeso", (313 * field.getRowCount() * field.getColumnCount())
                - timeGameEnded, java.util.Date.from(Instant.now()));

        scoreServiceClient.addScore(s);
    }

    private void update() {
        System.out.println("Time passed: " + Pexeso.getPlayingSeconds());
        System.out.print(" ");
        for (int column = 0; column < field.getColumnCount(); column++) System.out.printf("%3d", column);
        System.out.println();
        //other lines- start with letter of row and write out the tiles
        for (int row = 0; row < field.getRowCount(); row++) {
            char rowChar = (char) (65 + row);
            System.out.print(rowChar + " ");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Tile t = field.getTile(row, column);
                if (!t.isUncovered()) {
                    System.out.print("[ ]");
                } else
                    System.out.print("[" + t.getSign() + "]");
            }
            System.out.println();
        }
    }

    private void processInput() {
        System.out.println("Write coords for tile opening: ");


        String command = this.readLine();
        try {
            handleInput(command);
        } catch (WrongFormatException e) {
            System.err.println(e.getMessage());
            return;
        }
        command = command.toLowerCase();
        if (command.equals("c")) {
            field.solve();
            return;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.matches()) {
            char rowChar = matcher.group(1).charAt(0);
            int row = rowChar - 'a';
            int column = Integer.parseInt(matcher.group(2));

            if (!field.openTile(row, column)) {
                update();
                System.err.println("missed, covering last two.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.err.println("sleep interrupted.");
                }
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
                field.coverLastTwoTiles(row, column);

            }

        } else
            System.err.println("not matched.");

    }

    private void handleInput(String input) throws WrongFormatException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!(matcher.matches())) {
            throw new WrongFormatException("Wrong command format");
        }

    }
}
