package controller;


import listener.GameListener;
import model.Constant;
import model.PlayerColor;
import model.Chessboard;
import model.ChessboardPoint;
import view.CellComponent;
import view.ElephantChessComponent;
import view.ChessboardComponent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *前后端连在一块的地方
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before，即已经被选择的点
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {//constructor
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {//要自己写吗，初始化什么？棋盘吗？
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player一次移动后交换玩家
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    private boolean win() {//检查是否有人赢了的方法，看看判定为赢的条件？
        // TODO: Check the board if there is a winner
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    //当玩家点击棋盘上的棋子时，调用该方法，输入被点击的棋子所在棋盘格的坐标，和被点击的棋子的对象
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {
        if (selectedPoint == null) {//如果没有已经被选择的点
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {//选中的棋子的玩家是当前玩家
                selectedPoint = point;//传入的point成为被选择的点
                component.setSelected(true);//让选中的那个棋子selected状态设置为true，各棋子的类都是component的子类
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {//玩家在之前选择的点与本次点击选择的点相同
            selectedPoint = null;//使之前选择的点为null
            component.setSelected(false);//本次点击的棋子实际上没有被点击
            component.repaint();//重画
        }
        // TODO: Implement capture function
    }

    public void loadGameFromFile(String path) {//从文件中读入游戏棋盘，输入的时存放文件的路径，在ide里？？？
        try {
            List<String> lines = Files.readAllLines(Path.of(path));
            for (String line:lines
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
