package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.EscapeSequences;

public class ClientMain {
    public static void main(String[] args) {
        //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        //System.out.println("♕ 240 Chess Client: " + piece);

        try {
             new ClientFunctions().run();

        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }

    }


}
