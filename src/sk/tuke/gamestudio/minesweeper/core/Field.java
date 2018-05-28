package sk.tuke.gamestudio.minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    private final int rowCount;

    private final int columnCount;

    private final int mineCount;

    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
            if(tile instanceof Clue){
                if(((Clue) tile).getValue()==0){
                    this.openAdjacentTiles(row,column);
                }
            }

            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }
    public void solve(){
        for (int i=0; i<this.getRowCount(); i++){
            for( int j=0;j<this.getColumnCount(); j++){
                Tile tile = tiles[i][j];
                if(!(tile instanceof Mine)){
                    tile.setState(Tile.State.OPEN);
                }
            }
        }
    }
    private int getNumberOf(Tile.State state){
        int countedTiles = 0;
        for (int i=0; i<this.getRowCount(); i++){
            for (int j=0; j<this.getColumnCount(); j++){
                if (tiles[i][j].getState()==state){
                    countedTiles ++;
                }
            }
        }

        return countedTiles;
    }
    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.MARKED);
        }else if (tile.getState() == Tile.State.MARKED) {
            tile.setState(Tile.State.CLOSED);
        }
        if (isSolved()) {
            state = GameState.SOLVED;
            return;
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        //greeting
        System.out.println("Good luck!");
        //mines placement
        Random random = new Random();
        int minesPlaced = 0;

        while(minesPlaced<mineCount) {
            int row = random.nextInt(this.getRowCount());
            int column = random.nextInt(this.getColumnCount());
            if(tiles[row][column]==null){
                tiles[row][column] = new Mine();
                minesPlaced++;
            }

        }
        //filling the rest of tiles
        for (int row=0; row<this.getRowCount(); row++){
            for (int column = 0; column<this.getColumnCount(); column++){
                if(tiles[row][column]==null){
                    tiles[row][column] = new Clue(this.countAdjacentMines(row, column));

                }

            }
        }

    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    public boolean isSolved() {


       return(this.getColumnCount()*this.getRowCount() - getNumberOf(Tile.State.OPEN) == mineCount);

    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private void openAdjacentTiles(int row, int column){

       for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Clue) {
                            this.openTile(actRow,actColumn);
                        }
                    }
                }
            }
        }

    }

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

    /**
     * Mine count.
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * Game state.
     */
    public GameState getState() {
        return state;
    }

    public Tile getTile(int row, int column){
        return tiles[row][column];
    }

    public int getRemainingMineCount(){

        int count =getMineCount()-getNumberOf(Tile.State.MARKED);

        return count;
    }
}
