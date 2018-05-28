package sk.tuke.gamestudio.client;

public class Test {
    public static void main(String[] args) {


        ScoreRestServiceClient client = new ScoreRestServiceClient();
        System.out.println(client.getBestScoresForGame("Minesweeper"));
    }
}
