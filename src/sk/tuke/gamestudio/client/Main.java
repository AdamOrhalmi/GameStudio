package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.weather.WeatherMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class Main {
    private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    private static RatingRestServiceClient ratingService = new RatingRestServiceClient();
    private static CommentRestServiceClient commentService = new CommentRestServiceClient();
    private static ScoreRestServiceClient scoreService = new ScoreRestServiceClient();
    private static boolean gamePlayed = false;

    private static TableViewer tb = new TableViewer();
    private static WeatherRestServiceClient weatherService = new WeatherRestServiceClient();

    public static void main(String[] args) {
//               commentService.addComment(new Comment("jano", "kamene", "fsdf"));
//
//        Comment c1 = commentService.getComment(1);
//        c1.setComment(String.valueOf(System.currentTimeMillis()));
//        System.out.println("fffffff " + c1);
//        commentService.editComment(c1);

        System.out.println("welcome to GameStudio!");
        System.out.println("enter your name: ");
        String username = readLine();

        if (username.equals("")) {
            username = System.getProperty("user.name");
        }

       tb.setUsername(username);

        System.out.println("what would you like to play?");

        while (true) {
            int gamesCount = 1;
            for (Games g : Games.values()) {
                System.out.println((g.ordinal()+1) + ". " + g.toString());
                gamesCount++;
            }

            System.out.println(gamesCount + ". View tables\nOther number: Exit");

            int switchInput=0;
            while (switchInput == 0) {

                try {
                    switchInput= Integer.parseInt(readLine());
                } catch (NumberFormatException e) {
                    System.err.println("Wrong input format. Try again.");
                }
            }
            String chosenGame="GAME NOT FOUND";
            for (Games g : Games.values()) {
                if(switchInput==g.ordinal()+1){
                    chosenGame=g.getGameName();
                    g.startGame(username);
                    gamePlayed =true;
                }
            }if(!gamePlayed){
                if(switchInput==gamesCount){
                    tb.additionalChoices();
                }else { WeatherMap weather = weatherService.getWeather("Kosice");
                    weatherService.printWeather(weather);

                    System.out.println("See you some other time!");
                    System.exit(0);}

            }
            if (gamePlayed) {
                System.out.println("Thank you for playing! would you like to leave us a comment? y/n");
                String input = readLine();
                if (input.equals("y")) {
                    System.out.println("write your comment here: ");
                    input = readLine();
                    Comment comment = new Comment(username, chosenGame, input);
                    commentService.addComment(comment);
                }
                System.out.println("Would you like to rate our game? y/n");
                input = readLine();

                if (input.equals("y")) {
                    System.out.println("enter your rating (1-10) : ");
                    input = readLine();
                    int rate = Integer.parseInt(input);
                    Rating rating = new Rating(username, chosenGame, rate);

                    ratingService.addRating(rating);
                }
            }

            gamePlayed = false;

            System.out.println("Thank you! What would you like to play now?");
        }

    }

    private static String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }


}
