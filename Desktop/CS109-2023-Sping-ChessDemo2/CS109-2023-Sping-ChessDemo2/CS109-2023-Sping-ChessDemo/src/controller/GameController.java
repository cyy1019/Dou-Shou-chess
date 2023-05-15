package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 * 前后端连在一块的地方
 */
public class GameController implements GameListener {
    //TODO：给各个组件写监听器，事件发生则调用监听器的代码，先完成需要被调用的方法
    //TODO：需要给各个组件写监听器（？？？），在监听器的代码里更新前端
    //TODO:找到棋子的监听器代码看看
    //controller类里面会调用model的方法

    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before，即已经被选择的点
    private ChessboardPoint selectedPoint;
    public PlayerColor winner;

    public GameController(ChessboardComponent view, Chessboard model) {//constructor
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        view.registerController(this);
        initialize();//在new一个gamecontroller的时候会初始化，初始化啥？？？
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {//要自己写吗，初始化什么？棋盘吗？
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                //TODO:写啥？？？
            }
        }
    }

    // after a valid move swap the player一次移动后交换玩家
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean win() {//检查是否有人赢了的方法，看看判定为赢的条件？
        ChessboardPoint den1 = new ChessboardPoint(0, 3);
        ChessboardPoint den2 = new ChessboardPoint(8, 3);
        if (model.getChessPieceAt(den1) != null && model.getChessPieceAt(den1).getOwner().equals(PlayerColor.BLUE)) {
            winner = PlayerColor.BLUE;
        }
        if (model.getChessPieceAt(den2) != null && model.getChessPieceAt(den2).getOwner().equals(PlayerColor.RED)) {
            winner = PlayerColor.RED;
        }
        if (model.getNumberOfBlue() == 0) {
            winner = PlayerColor.RED;
        }
        if (model.getNumberOfRed() == 0) {
            winner = PlayerColor.BLUE;
        }
        return false;
    }//TODO:要完成前端的胜利动画


    /*点到空棋盘格时调用的方法，监听器在ChessboardComponent
     * 实际是移动棋子并在前端画出的方法
     * 包括棋子移进dens和traps*/
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {//点了空棋盘格,传入的是被点的坐标和组件
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            //如果原来已经选中的点不是null,可以保证已经被选中的点都是棋子（？？）
            if (ChessboardComponent.getBlueTrapCell().contains(point) &&
                    model.getChessPieceAt(selectedPoint).getOwner().equals(PlayerColor.RED)) {
                model.getChessPieceAt(selectedPoint).setRank(0);
            }
            if (ChessboardComponent.getRedTrapCell().contains(point) &&
                    model.getChessPieceAt(selectedPoint).getOwner().equals(PlayerColor.BLUE)) {
                model.getChessPieceAt(selectedPoint).setRank(0);
            }
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//只在内存里改变了棋子的位置
            selectedPoint = null;//重置已选中的点
            swapColor();//交换玩家
            view.repaint();//一定要重画，不然前端不显示
            win();//每次动都检查有没有赢家
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, Component component) {//传入被点的点和组件
        if (selectedPoint == null) {//如果没有已经被选择的点
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {//选中的棋子的玩家是当前玩家
                selectedPoint = point;//传入的point成为被选择的点
                component.setSelected(true);//让选中的那个棋子selected状态设置为true，各棋子的类都是component的子类
                component.repaint();//组件重画，打圈？？？
            }
        } else if (selectedPoint.equals(point)) {//玩家在之前选择的点与本次点击选择的点相同
            selectedPoint = null;//使之前选择的点为null
            component.setSelected(false);//本次点击的棋子实际上没有被点击
            component.repaint();//重画，把打的圈去掉
        } else if (selectedPoint != null && model.getChessPieceAt(selectedPoint) != null) {
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));//只在内存里改变了棋子的位置
                selectedPoint = null;//重置已选中的点
                swapColor();//交换玩家
                view.repaint();//一定要重画，不然前端不显示
                win();//每次动都检查有没有赢家
            }
        }
    }

    public void loadGameFromFile(String path) {//从文件中读入游戏棋盘，输入的时存放文件的路径，在ide里？？？
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String line : lines
            ) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//存储棋盘状态用的，棋盘上的棋子对象/cell对象用序列化恢复吗？？？
    //看看SA的tips
//好像存在这里不太好，看看SA的tips
}
