package sk.tuke.gamestudio.pexeso;

public class Tile {
    private char sign;
    private boolean isUncovered =false;
    public Tile(char sign){
        this.setSign(sign);
    }
    public Tile(){
        this.setSign('/');
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public boolean isUncovered() {
        return isUncovered;
    }

    public boolean open() {
        if(!isUncovered){
            isUncovered=true;
        }
        return true;
    }

    public boolean close() {
        if(isUncovered){
            isUncovered=false;
        }
        return false;
    }

}
