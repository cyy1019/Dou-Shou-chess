package model;

import view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public Cell[][] grid;
    //grid是用来放cell的，
    //要改？？？
    //创建一个方法initialpiecesfromfile，把数组里的数字变成棋子（？用什么方式存储棋盘）
    public int numberOfBlue = 8;
    public int numberOfRed = 8;
    public List<Step> stepSet = new ArrayList<>();
    public PlayerColor currentSide;


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

    private void initPieces() {//initialize
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant", 8));//在棋盘grid该位置放一个棋子对象
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant", 8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf", 4));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf", 4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard", 5));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard", 5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat", 1));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat", 1));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat", 2));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat", 2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog", 3));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog", 3));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion", 7));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion", 7));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger", 6));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger", 6));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    //在传入的点找到棋盘格，返回棋盘格的cell槽里面的piece

    public Cell getGridAt(ChessboardPoint point) {
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
        Step s = new Step(getChessPieceAt(src), src, dest);//每挪一步创建一个step
        stepSet.add(s);//把这个新的step加进stepSet里
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        removeChessPiece(dest);
        moveChessPiece(src, dest);
        if (getChessPieceAt(dest).getOwner() == PlayerColor.BLUE) {
            numberOfBlue--;
        }
        if (getChessPieceAt(dest).getOwner() == PlayerColor.RED) {
            numberOfRed--;
        }
    }//capture：令进攻棋子原本的槽里的piece为null，被进攻棋子槽里的piece换成进攻动物

    public Cell[][] getGrid() {
        return grid;
    }//返回在棋盘格上的槽Zz

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;//同时满足：在坐标src处的棋子不是null同时要移动的目标坐标上没有棋子
        }
        if (ChessboardComponent.getBlueDensCell().contains(dest) && getChessPieceAt(src).getOwner().equals(PlayerColor.RED)) {
            return true;
        }
        if (ChessboardComponent.getRedDensCell().contains(dest) && getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)) {
            return true;
        }
        if (ChessboardComponent.getRiverCell().contains(dest)) {
            if (getChessPieceAt(src).getRank() == 1) {
                return true;
            }
            return false;
        }
        if (getChessPieceAt(src).getRank() == 7 || getChessPieceAt(src).getRank() == 6) {
            ChessboardPoint p = new ChessboardPoint((src.getRow() + dest.getRow()) / 2, src.getCol());
            ChessboardPoint p2 = new ChessboardPoint(src.getRow(), (src.getCol() + dest.getCol() + 1) / 2);
            if (ChessboardComponent.getRiverCell().contains(p)) {
                ChessboardPoint p3 = new ChessboardPoint(p.getRow() + 1, p.getCol());
                ChessboardPoint p4 = new ChessboardPoint(p.getRow() - 1, p.getCol());
                if (getChessPieceAt(p3) != null || getChessPieceAt(p4) != null || getChessPieceAt(p) != null) {
                    return false;
                }
                return calculateDistance(src, dest) == 3;
            }
            if (ChessboardComponent.getRiverCell().contains(p2)) {
                ChessboardPoint p3 = new ChessboardPoint(p2.getRow(), p2.getCol() - 1);
                if (getChessPieceAt(p2) != null || getChessPieceAt(p3) != null) {
                    return false;
                }
                return calculateDistance(src, dest) == 2;
            } else {
                return calculateDistance(src, dest) == 1;
            }
        }
        return calculateDistance(src, dest) == 1;
    }//移动方法不同


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) == null) {//capture两个位置上的棋子都不是null
            return false;
        }
        if (getChessPieceAt(src).getRank() == 8 && getChessPieceAt(dest).getRank() == 1) {
            return false;
        }
        if (getChessPieceAt(src).getRank() == 1 && getChessPieceAt(dest).getRank() == 8) {
            if (getGridAt(src).getClass().equals(River.class)) {//rat is in the water
                return false;
            }
            return calculateDistance(src, dest) == 1;
        }
        if (getChessPieceAt(src).getRank() >= getChessPieceAt(dest).getRank()) {
            if (getChessPieceAt(src).getRank() == 1 && getChessPieceAt(dest).getRank() == 1) {//all are rats
                if (ChessboardComponent.getRiverCell().contains(dest)) {//the one being captured is in the river
                    return false;//cannot be captured
                }
            }
            return calculateDistance(src, dest) == 1;
        }
        return false;//判断是否等于1，等于1则可以capture
    }

    public int getNumberOfBlue() {
        return numberOfBlue;
    }

    public int getNumberOfRed() {
        return numberOfRed;
    }

    public PlayerColor getCurrentSide() {
        if (stepSet.size() % 2 == 1) {
            return PlayerColor.RED;
        }
        return PlayerColor.BLUE;
    }
}