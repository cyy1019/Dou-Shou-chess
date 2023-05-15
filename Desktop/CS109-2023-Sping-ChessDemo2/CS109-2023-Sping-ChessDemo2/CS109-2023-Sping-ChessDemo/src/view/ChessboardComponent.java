package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private static final Set<ChessboardPoint> riverCell = new HashSet<>();//可序列化的，chessboardpoint的set
    private static final Set<ChessboardPoint> redTrapCell = new HashSet<>();
    private static final Set<ChessboardPoint> blueTrapCell = new HashSet<>();
    private static final Set<ChessboardPoint> redDensCell = new HashSet<>();
    private static final Set<ChessboardPoint> blueDensCell = new HashSet<>();

    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    if (grid[i][j].getPiece().getName().equals("Leopard")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new LeopardChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Elephant")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new ElephantChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Tiger")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new TigerChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Lion")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new LionChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Wolf")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new WolfChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Dog")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new DogChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Cat")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new CatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (grid[i][j].getPiece().getName().equals("Rat")) {
                        ChessPiece chessPiece = grid[i][j].getPiece();
                        System.out.println(chessPiece.getOwner());
                        gridComponents[i][j].add(
                                new RatChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                }
            }
        }

    }

    public void initiateGridComponents() {
        riverCell.add(new ChessboardPoint(3, 1));//在rivercell的坐标集合里加元素
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        redTrapCell.add(new ChessboardPoint(0,2));
        redTrapCell.add(new ChessboardPoint(0,4));
        redTrapCell.add(new ChessboardPoint(1,3));

        blueTrapCell.add(new ChessboardPoint(7,3));
        blueTrapCell.add(new ChessboardPoint(8,2));
        blueTrapCell.add(new ChessboardPoint(8,4));

        redDensCell.add(new ChessboardPoint(0,3));
        blueDensCell.add(new ChessboardPoint(8,3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
                if (riverCell.contains(temp)) {
                    gridComponents[i][j].add(
                            new River());
                }if (redTrapCell.contains(temp)) {
                    gridComponents[i][j].add(
                            new RedTrap());
                }if (blueTrapCell.contains(temp)) {
                    gridComponents[i][j].add(
                            new BlueTrap());
                }if (redDensCell.contains(temp)) {
                    gridComponents[i][j].add(
                            new RedDens());
                }if (blueDensCell.contains(temp)) {
                    gridComponents[i][j].add(
                            new BlueDens());
                }


            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, Component chess) {//添加棋子
        getGridComponentAt(point).add(chess);
    }

    public Component removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
       Component chess = (Component) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }

    @Override
    protected void processMouseEvent(MouseEvent e) {//对于棋子的监听器？？
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {//点了空的棋盘格
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {//点到了棋子
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (Component) clickedComponent.getComponents()[0]);
            }/*TODO:River，Trap，Den都不能强制转换为component，这里会报错。所以要不要改成继承component，
           TODO：然后重写component父类，再写一个chesscomponent作为棋子的父类 */
        }
    }

    public static Set<ChessboardPoint> getRiverCell() {
        return riverCell;
    }

    public static Set<ChessboardPoint> getBlueDensCell() {
        return blueDensCell;
    }

    public static Set<ChessboardPoint> getBlueTrapCell() {
        return blueTrapCell;
    }

    public static Set<ChessboardPoint> getRedDensCell() {
        return redDensCell;
    }

    public static Set<ChessboardPoint> getRedTrapCell() {
        return redTrapCell;
    }
}
