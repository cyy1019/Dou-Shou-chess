package model;

import view.ChessboardComponent;

import java.io.Serializable;

public class Step implements Serializable {
    private ChessPiece chessPiece;
    private ChessboardPoint chessboardPointStart;
    private ChessboardPoint chessboardPointEnd;
    public Step(ChessPiece chessPiece,ChessboardPoint start,ChessboardPoint end){
        chessPiece = this.chessPiece;
        start = chessboardPointStart;
        end = chessboardPointEnd;
    }
}
