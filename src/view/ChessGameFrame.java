package view;

import controller.GameController;
import controller.Loading;
import model.*;

import javax.swing.*;
import java.awt.*;
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
        Music player = new Music();
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
            try {//重新传入grid
                getGameController().getModel().setStepSet(Loading.deserializeStep("C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt"));
                getGameController().getModel().setGrid(Loading.deserializeCell("C:\\Users\\陈彦妤\\Desktop\\pro\\Cell.txt"));
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {
                        ChessboardPoint point = new ChessboardPoint(i, j);
                       if (getGameController().getModel().getChessPieceAt(point) != null) {
                            Cell[][] grid = getGameController().getModel().getGrid();//不一定每一个cell里都有棋子
                            //TODO:把棋子组件加回去
                            if (grid[i][j].getPiece().getName().equals("Leopard")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new LeopardChessComponent(
                                                chessPiece.getOwner(),ONE_CHESS_SIZE
                                                ));
                            }
                            if (grid[i][j].getPiece().getName().equals("Elephant")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new ElephantChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Tiger")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new TigerChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Lion")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new LionChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Wolf")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new WolfChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Dog")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new DogChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Cat")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new CatChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                            if (grid[i][j].getPiece().getName().equals("Rat")) {
                                ChessPiece chessPiece = grid[i][j].getPiece();
                                System.out.println(chessPiece.getOwner());
                                getChessboardComponent().getGridComponents()[i][j].add(
                                        new RatChessComponent(
                                                chessPiece.getOwner(),
                                                getChessboardComponent().getCHESS_SIZE()));
                            }
                               getChessboardComponent().repaint();
                            changeCurrentPlayer();
                            //TODO:重新setgameround
                        }
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 7; j++) {

                    } System.out.println();

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        //TODO:注意gameRound有问题，棋子刚开始变小了，但可以正常点击，有时候currentplayer有问题
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
                Loading.serializeStep(getGameController().getModel().getStepSet(), "C:\\Users\\陈彦妤\\Desktop\\pro\\Step.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            getChessboardComponent().removeAllComponent();//前端把所有棋子组件移除了
            getGameController().getModel().removeAll();//在后端chessboard上把所有棋子移走了
            gameController.setGameRound(1);//TODO:改不掉！！！
            repaint();
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
