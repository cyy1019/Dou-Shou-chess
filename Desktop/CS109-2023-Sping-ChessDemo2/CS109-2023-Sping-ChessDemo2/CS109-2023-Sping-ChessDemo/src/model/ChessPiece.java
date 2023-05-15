package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {//棋子的类，用于创建棋子对象
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if(this.rank >= target.getRank()){
            if(this.rank == 8 || target.getRank()==1){
                return false;
            }
            if(this.rank == 1 || target.rank == 8){
                return true;
            }else {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
