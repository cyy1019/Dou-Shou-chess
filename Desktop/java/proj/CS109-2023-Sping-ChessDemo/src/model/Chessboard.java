package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    //grid是用来放cell的，
    //要改？？？
    //创建一个方法initialpiecesfromfile，把数组里的数字变成棋子（？用什么方式存储棋盘）

    public Chessboard() {//constructor
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//9*7
        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();//让棋盘的每一个格子都是cell这个槽
            }
        }
    }

    public void initPieces() {//initialize,初始化棋盘，等各个棋子的类加上之后重新写
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));//在棋盘grid该位置放一个棋子对象
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
    }

    private void initPiecesFromFile(){//读入文件后初始化棋子，是这里用序列化和反序列化吗

}

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    //在传入的点找到棋盘格，返回棋盘格的cell槽里面的piece

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }//传入一个point，返回在该位置的棋盘的cell槽

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {//传入两个point
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }//计算距离，距离是dy+dx

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }//传入point，移除同时返回棋子，移除同时返回！！！

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    //传入想要放置棋子的坐标和想放置的棋子，把棋子放在那个位置
    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));//把src位置的棋子移除并移到dest位置上
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        removeChessPiece(dest);//从dest位置的槽里面移除piece
        setChessPiece(dest, removeChessPiece(src));
    }//capture：令进攻棋子原本的槽里的piece为null，被进攻棋子槽里的piece换成进攻动物

    public Cell[][] getGrid() {
        return grid;
    }//返回在棋盘格上的槽

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {//还要加上判断棋子能不能走进河里
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;//同时满足：在坐标src处的棋子不是null同时要移动的目标坐标上没有棋子
        }
        if (getChessPieceAt(src).getRank() == 1 ) {
            return calculateDistance(src, dest) == 1;
        } else if (getChessPieceAt(src).getRank() == 6 || getChessPieceAt(src).getRank() == 7) {
            return calculateDistance(src, dest) == 2 || calculateDistance(src, dest) == 3;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {//capture两个位置上的棋子都不是null
            return false;
        }
        if (getChessPieceAt(src).getRank() > getChessPieceAt(dest).getRank()){
            return calculateDistance(src, dest) == 1;//判断是否等于1，等于1则可以capture
        }
        return false;
    }



}
