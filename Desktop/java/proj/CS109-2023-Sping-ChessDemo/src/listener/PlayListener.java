package listener;

import model.Chessboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import view.CellComponent;
import view.ElephantChessComponent;
import view.ChessboardComponent;
public class PlayListener extends WindowAdapter implements ActionListener {
    private ChessboardComponent view;


    @Override
    public void actionPerformed(ActionEvent e) {
        Chessboard chessboard = new Chessboard();
        chessboard.initPieces();
        view.repaint();
    }
}
