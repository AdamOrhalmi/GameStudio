package sk.tuke.gamestudio.minesweeper;

import java.io.*;

public class Settings implements Serializable {
    private final int rowCount;
    private final int columnCount;
    private final int mineCount;
    public static final Settings BEGINNER = new Settings(9, 9, 10);
    public static final Settings INTERMEDIATE= new Settings(16, 16, 40);
    public static final Settings EXPERT= new Settings(16, 30, 99);

    private static final String SETTING_FILE =System.getProperty("user.home") + System.getProperty("file.separator") + "minesweeper.settings";

    public Settings(int rowCount, int columnCount, int mineCount){
        this.rowCount=rowCount;
        this.columnCount=columnCount;
        this.mineCount=mineCount;
    }


    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getMineCount() {
        return mineCount;
    }

    public boolean equals(Object o){
        if (o instanceof Settings) {
           return(((Settings) o).getRowCount() == this.getRowCount() &&
               ((Settings) o).getColumnCount() == this.getColumnCount()&&
               ((Settings) o).getMineCount() == this.getMineCount());
        }
        return false;
    }

    public int hashCode(){
        return rowCount*columnCount*mineCount;
    }

    public void save(){
        try{
            FileOutputStream os = new FileOutputStream(SETTING_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this);
        }catch(Exception e){

            System.err.println(e.getMessage());
        }
    }

    public static Settings load(){
        try{
            FileInputStream is = new FileInputStream(SETTING_FILE);
            ObjectInputStream ois = new ObjectInputStream(is);
            Settings s = (Settings)ois.readObject();
            if (s!=null) return s;

            else return BEGINNER;
        }catch(Exception e){

            return BEGINNER;
        }

    }


}
