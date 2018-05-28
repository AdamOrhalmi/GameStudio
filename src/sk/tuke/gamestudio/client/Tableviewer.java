package sk.tuke.gamestudio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Tableviewer {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static RatingRestServiceClient ratingClient = new RatingRestServiceClient();
    private static ScoreRestServiceClient scoreClient = new ScoreRestServiceClient();
    private static CommentRestServiceClient commentClient = new CommentRestServiceClient();


    public static void additionalChoices() {

        System.out.println("What would you like to view?");

        for (TableOption option : TableOption.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option);
        }
        int selection = -1;
        do {
            System.out.println("Option: ");

            selection = Integer.parseInt(readLine());

        } while (selection <= 0 || selection > TableOption.values().length);

        TableOption o = TableOption.values()[selection - 1];

        switch (o) {
            case Score:
                scoreClient.printBestScores(getGameChoice());
                break;
            case Comments:

                if (gamefiltering()) {
                    commentClient.printComments(commentClient.getCommentsByGame(getGameChoice().toLowerCase()));
                } else {
                    commentClient.printComments(commentClient.getAllComments());
                }
                break;
            case Rating:
                System.out.println("--GAME RATING--\n");
                for (Games game : Games.values()) {
                    ratingClient.getAvgRating(game.toString().toLowerCase());
                }
                break;

        }


    }

    private static String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.err.println("wrong input, please try again");
            return null;
        }
    }

    private static boolean gamefiltering() {
        System.out.println("all results(1) or game specific only(2)? ");

        String choice = readLine();
        switch (choice) {
            case "1":
                return false;
            case "2":
                return true;
            default:
                return false;
        }
    }

    private static String getGameChoice() {
        System.out.println("which game do you want to do this for?");

        for (Games game : Games.values()) {
            System.out.printf("%d. %s%n", game.ordinal() + 1, game);
        }

        int selection = -1;
        do {
            System.out.println("Choice: ");

            selection = Integer.parseInt(readLine());

        } while (selection <= 0 || selection > Games.values().length);
        return Games.values()[selection - 1].toString();
    }


}

enum TableOption {

    Score,
    Comments,
    Rating

}