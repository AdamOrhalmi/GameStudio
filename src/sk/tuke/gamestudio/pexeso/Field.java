package sk.tuke.gamestudio.pexeso;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    private final int rowCount;

    private final int columnCount;


    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     */
    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    private boolean oddTurn=true;
    private int savedRow=0;
    private int savedCol=0;

    public boolean openTile(int row, int column) {
    //returns whether the last two tiles need to be covered
        Tile tile = tiles[row][column];
        if(tile.isUncovered()){
            System.err.println("This tile is uncovered already!");
            return true;
        }
        tile.open();
        if(oddTurn){
            savedRow=row;
            savedCol=column;

        }
        //otvori policko. ak je to parny tah,
        // tak ulozi to co otvoril. ak je neparny, a neodhalil obidve, zakryje toto a predosle.

        int signcount=0;
        for (int x = 0; x < this.getRowCount(); x++) {
            for (int y = 0; y < this.getColumnCount(); y++) {
                if (tiles[x][y].getSign()==tile.getSign()&&tiles[x][y].isUncovered()) {
                    signcount++;
                }
            }
        }

        if(!oddTurn&&signcount!=2){
            oddTurn=!oddTurn;
            return false;
//
        }
        oddTurn=!oddTurn;
        return true;
    }
    public void coverLastTwoTiles(int row, int column){
        tiles[row][column].close();
        tiles[savedRow][savedCol].close();
    }

    public void solve() {

        Arrays.stream(tiles).forEach(a -> Arrays.stream(a).forEach(Tile::open));

    }




    private void generate() {
        //greeting

        System.out.println("Welcome, " + System.getProperty("user.name") + "! Good luck!");
        //mines placement
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                tiles[row][column] = new Tile('/');
            }
        }

        Random random = new Random();

        List<Character> usedSigns = new ArrayList<>();

        int sameTileCount = 0;
        char sign;

        while (!isFilled()) {
//tu sa to dostane
            sign = getRandomSign();
            while (usedSigns.contains(sign)) {

                sign = getRandomSign();
            }
            usedSigns.add(sign);

            while (sameTileCount < 2) {
                int row = random.nextInt(this.getRowCount());
                int column = random.nextInt(this.getColumnCount());

                if (tiles[row][column].getSign()=='/') {

                    tiles[row][column].setSign(sign);
                    sameTileCount++;

                }
            }
            if (sameTileCount == 2) {
                sameTileCount = 0;
            }
        }
    }
    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    public boolean isSolved() {
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                if (!tiles[row][column].isUncovered()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isFilled() {
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                if (tiles[row][column].getSign() == '/') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */


    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    public int getColumnCount() {
        return columnCount;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    private char getRandomSign() {
        Random random = new Random();
        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789*+~<>?!@#$%";
        return abc.charAt(random.nextInt(abc.length()));
    }



}
