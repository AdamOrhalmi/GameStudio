package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TableViewer {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static RatingRestServiceClient ratingClient = new RatingRestServiceClient();
    private static ScoreRestServiceClient scoreClient = new ScoreRestServiceClient();
    private static CommentRestServiceClient commentClient = new CommentRestServiceClient();
    private String username;

    public void setUsername(String name) {
        this.username = name;
    }

    public void additionalChoices() {

        System.out.println("What would you like to view?");

        for (TableOption option : TableOption.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option);

        }
        System.out.println("Option: ");
        int selection = -1;
        do {
            try {
                selection = Integer.parseInt(readLine());
            } catch (NumberFormatException e) {
                System.err.println("Please write numeric choice.");
            }
            if( selection <= 0 || selection > TableOption.values().length){
                System.err.println("Wrong input. try again.");
            }

        } while (selection <= 0 || selection > TableOption.values().length);

        TableOption o = TableOption.values()[selection - 1];

        switch (o) {
            case Score:
                System.out.println("What would you like to do? \n" +
                        "1. View scores\n" +
                        "2. Remove my score");
                try {
                    selection = Integer.parseInt(readLine());
                } catch (NumberFormatException e) {
                    System.err.println("Please write numeric choice.");
                }
                switch (selection) {
                    case 1:
                        scoreClient.printBestScores(getGameChoice());
                        break;
                    case 2:
                        removeScore();
                        break;
                }

                break;

            case Comments:
                System.out.println("What would you like to do? \n" +
                        "1. View comments\n" +
                        "2. Modify my comments");
                try {
                    selection = Integer.parseInt(readLine());
                } catch (NumberFormatException e) {
                    System.err.println("Please write numeric choice.");
                }
                switch (selection) {
                    case 1:
                        if (filterGames()) {
                            commentClient.printComments(commentClient.getCommentsByGame(getGameChoice().toLowerCase()));
                        } else {
                            commentClient.printComments(commentClient.getAllComments());
                        }
                        break;
                    case 2:
                        modifyComments();
                        break;
                }
                break;
            case Rating:
                System.out.println("--GAME RATING--\n");
                for (Games game : Games.values()) {
                    ratingClient.getAvgRating(game.toString());
                }
                break;
        }
    }

    private String readLine() {
        try {
            String s = input.readLine();
            if (s == null) {
                s = "1";
            }
            return s;
        } catch (IOException e) {
            System.err.println("wrong input, please try again");
            return null;
        }
    }

    private boolean filterGames() {
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

    private String getGameChoice() {
        System.out.println("which game do you want to do this for?");
        for (Games game : Games.values()) {
            System.out.printf("%d. %s%n", game.ordinal() + 1, game);
        }
        int selection = 0;
        do {
            System.out.println("Choice: ");

            selection = Integer.parseInt(readLine());

        } while (selection <1 || selection > Games.values().length);
        return Games.values()[selection - 1].toString();
    }

    private void modifyComments() {
        List<Comment> commentList = commentClient.getCommentsByUser(username);
        commentClient.printComments(commentList);
        System.out.println("Select comment to modify: ");
        int index = Integer.parseInt(readLine()) - 1;
        Comment c = commentList.get(index);

        System.out.println("What would you like to do with this comment?\n" +
                "1. Edit comment\n" +
                "2. Delete comment");
        int selection=0;
        while(selection==0){
        try {
           selection = Integer.parseInt(readLine());
        } catch (NumberFormatException e) {
            System.err.println("Please write numeric choice.");
        }
        }
        switch (selection) {
            case 1:
                System.out.println("Please enter edited comment:");
                String newComment = readLine();
                c.setComment(newComment);
                commentClient.editComment(c);
                break;
            case 2:
                System.out.println("are you sure you want to delete this comment? (y/n)");
                String decision = readLine();
                if (decision.equals("y")) commentClient.deleteComment(c);
                break;
            default:
                break;
        }

    }

    private void removeScore(){
        List<Score> scoreList = scoreClient.getScoresByUser(username);
        if(scoreList.isEmpty()){
            System.out.println("You have no scores saved yet.");
            return;
        }
        scoreClient.printBestScores(scoreList);
        System.out.println("Select comment to delete: ");
        int index =-2;
        while(index==-2)
        try {
           index = Integer.parseInt(readLine())-1;
        } catch (NumberFormatException e) {
            System.err.println("Please write numeric choice.");
        }

        Score score;
        try {
            score = scoreList.get(index);
        } catch (Exception e) {
            System.err.println("Score not found.");
            return;
        }
        System.out.println("are you sure you want to delete this score? (y/n)");
        String decision = readLine();
        if (decision.equals("y")) scoreClient.removeScore(score.getIdent());
        }

    }



enum TableOption {

    Score,
    Comments,
    Rating

}