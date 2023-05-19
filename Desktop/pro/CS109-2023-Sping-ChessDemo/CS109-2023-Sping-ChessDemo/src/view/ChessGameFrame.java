package view;

import controller.GameController;
import controller.Loading;
import model.Cell;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    private static GameController gameController;
    private JLabel statusLabel;

    private static JLabel presentPlayer = new JLabel();

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        addChessboard();
        addLabel();
        addRestartButton();
        addLoadButton();
        savejButton();
        addUndoButton();
        addPresentPlayer();
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Round: 1");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    public static GameController getGameController() {
        return gameController;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "是否重新开始游戏？");
            gameController.initialize();
            statusLabel.setText("Round: 1");
            gameController.setGameRound(1);
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addUndoButton() {
        JButton button = new JButton("Undo");
        button.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "要悔棋吗？");
            gameController.undo();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
//通过点击当前button可触发的事件都写到括号内
        button.addActionListener(e -> {// 直接在按钮这里写监听器
            try {
                Loading.deserializeCell("C:\\Users\\陈彦妤\\Desktop\\pro\\Cell.txt");
                Loading.deserializeCell("C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        //TODO:要把反序列化的棋盘给重新画出来，注意gameRound，currentPlayer之类的变量有没有被一起序列化（？？？）传回来之后能用吗
    }

    //保存
    private void savejButton() {
        JButton savejButton = new JButton("Save");
        savejButton.setLocation(HEIGTH, HEIGTH / 10 + 480);
        savejButton.setSize(200, 60);
        savejButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(savejButton);
        savejButton.addActionListener(e -> {
            try {
                Loading.serializeCell(getGameController().getModel().getGrid(), "C:\\Users\\陈彦妤\\Desktop\\pro\\Cell.txt");
                Loading.serializeCell(getGameController().getModel().getGrid(),"C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            gameController.initialize();//点save之后，直接初始化棋盘

        });
    }

    private void addPresentPlayer() {//初始化棋盘的时候用的方法
        presentPlayer.setText("Player: BLUE");
        presentPlayer.setLocation(HEIGTH, HEIGTH / 20);
        presentPlayer.setSize(200, 60);
        presentPlayer.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(presentPlayer);
    }

    //TODO:当前玩家随轮数改变？？
    public static void changeCurrentPlayer() {
        if (Chessboard.getCurrentSide() == PlayerColor.BLUE) {
            presentPlayer.setText("Player: BLUE");
        }
        if (Chessboard.getCurrentSide() == PlayerColor.RED)
            presentPlayer.setText("Player: RED");
    }

}
