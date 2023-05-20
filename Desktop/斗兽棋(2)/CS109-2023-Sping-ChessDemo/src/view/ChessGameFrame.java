package view;

import controller.GameController;
import controller.Loading;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private GameController gameController;
    private JLabel statusLabel;

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
        setTitle("斗兽棋"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setVisible(true);


        addChessboard();
        addLabel();
        addRestartButton();
        addLoadButton();
        savejButton();
        addUndoButton();
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


    public GameController getGameController() {
        return gameController;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> {
            int select = JOptionPane.showConfirmDialog(this, "是否重新开始游戏？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                gameController.initialize();
                statusLabel.setText("Round: 1");
                gameController.setGameRound(1);
            }

        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addUndoButton() {
        JButton button = new JButton("Undo");
        button.addActionListener((e) -> {
            int select = JOptionPane.showConfirmDialog(this, "要悔棋吗？", "确认", JOptionPane.YES_NO_OPTION);
            if (select == 0) {
                gameController.undo();
            }
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
                Chessboard.grid = Loading.deserializeCell("C:\\Users\\陈彦妤\\Desktop\\pro\\Cell.txt");
                Chessboard.stepSet = Loading.deserializeStep("C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt");
                //把读入并返回的棋盘赋值给chessboard
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            //TODO：序列化的实际是cell，里面包括了棋子，从开始界面重新加载棋盘进来
            //TODO：把cell的二维数组读入，初始化棋子以外的组件（cell，traps，dens），然后按照读入的cell数组的信息画出棋子的组件
            //TODO：加载实需要重新画棋盘，但不需要初始化棋子，

        });
    }

    //保存
    private void savejButton() {
        JButton savejButton = new JButton("Save");
        savejButton.setLocation(HEIGTH, HEIGTH / 10 + 480);
        savejButton.setSize(200, 60);
        savejButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(savejButton);
        savejButton.addActionListener(e -> {
            try {//尝试可以代码
                Loading.serializeCell(Chessboard.grid, "C:\\Users\\陈彦妤\\Desktop\\pro\\Cell.txt");//可疑代码
                Loading.serializeStep(Chessboard.stepSet, "C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt");
            } catch (IOException ex) {//如果发生错误则进入该代码段
                throw new RuntimeException(ex);
            } finally {//无论报不报错都执行这段代码
                //TODO:回到开始界面，要写开始界面
            }
        });

    }


}


