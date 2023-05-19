
    package model;

import view.ChessboardComponent;

import java.io.Serializable;

    public class Step implements Serializable {
        private ChessPiece chessPiece;
        private ChessPiece chessPieceEaten;
        private ChessboardPoint chessboardPointStart;
        private ChessboardPoint chessboardPointEnd;
        public Step(ChessPiece chessPiece,ChessboardPoint start,ChessboardPoint end){
          this.chessPiece =chessPiece ;
          this.chessboardPointStart=start;
          this.chessboardPointEnd= end;
        }
        public Step(ChessPiece chessPiece,ChessPiece chessPieceEaten,ChessboardPoint start,ChessboardPoint end){
            this.chessPiece = chessPiece;
            this.chessPieceEaten = chessPieceEaten;
            this.chessboardPointStart = start;
            this.chessboardPointEnd = end;
        }
    }


