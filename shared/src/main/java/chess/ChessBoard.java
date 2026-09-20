package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    //8x8 of 'Pieces', which are more like positions passing hats
    final ChessPiece[][] squares = new ChessPiece[8][8]; //INDEXED AT 0

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
     squares[position.getRow()-1][position.getColumn()-1] = piece;
     //-1 because the positions those functions get start at 1, not 0

    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        /*
            [[r],[n],[b],[q],[k],[b],[n],[r]],
            [[p],[p],[p],[p],[p],[p],[p],[p]],
            [[],[],[],[],[],[],[],[]],
            [[],[],[],[],[],[],[],[]],
            [[],[],[],[],[],[],[],[]],
            [[],[],[],[],[],[],[],[]],
            [[P],[P],[P],[P],[P],[P],[P],[P]],
            [[R],[N],[B],[Q],[K],[B],[N],[R]],
        */
        //black
        //r n b q k b n r 8
        //p p p p p p p p 7

        //white
        //P P P P P P P P 2 ArrayList<ChessPieces> = {P, P, P, P, P, P, P}
        //R N B Q K B N R 1

        ArrayList<ChessPiece> pieceList= new ArrayList<>();
//        int x = 0;
//        while(x<=7){
//            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
//            pieceList.add(piece);
//            x++;
//        }
        //clear board
//        for(ChessPiece[] square: squares){
//            for(ChessPiece piece: square) {
//                addPiece();
//            }
//        }

        for (int lineRow =1; lineRow <= 8; lineRow++){
           for (int lineCol =1; lineCol <= 8; lineCol++){
              if (lineRow == 1){
                  ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;// white special
                  switch (lineCol){
                      case 1, 8 ->
                          addPiece(new ChessPosition(1, lineCol), new ChessPiece(white, ChessPiece.PieceType.ROOK));
                      case 2, 7 ->
                          addPiece(new ChessPosition(1, lineCol), new ChessPiece(white, ChessPiece.PieceType.KNIGHT));
                      case 3, 6 ->
                          addPiece(new ChessPosition(1, lineCol), new ChessPiece(white, ChessPiece.PieceType.BISHOP));
                      case 4 ->
                          addPiece(new ChessPosition(1, lineCol), new ChessPiece(white, ChessPiece.PieceType.QUEEN));
                      case 5 ->
                          addPiece(new ChessPosition(1, lineCol), new ChessPiece(white, ChessPiece.PieceType.KING));
                      //should never be called
                      default -> throw new IllegalStateException("Unexpected value: " + lineCol);
                  }
              }
              if (lineRow==2){
                  ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;//white pawns
                  addPiece(new ChessPosition(lineRow,lineCol),new ChessPiece(white, ChessPiece.PieceType.PAWN));

              }

              if (lineRow == 7){ // black pawns
                  ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
                  addPiece(new ChessPosition(lineRow,lineCol),new ChessPiece(black, ChessPiece.PieceType.PAWN));

              }
              if (lineRow == 8){ // black special
                  ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
                  switch (lineCol){
                      case 1, 8 ->
                              addPiece(new ChessPosition(1, lineCol), new ChessPiece(black, ChessPiece.PieceType.ROOK));
                      case 2, 7 ->
                              addPiece(new ChessPosition(1, lineCol), new ChessPiece(black, ChessPiece.PieceType.KNIGHT));
                      case 3, 6 ->
                              addPiece(new ChessPosition(1, lineCol), new ChessPiece(black, ChessPiece.PieceType.BISHOP));
                      case 4 ->
                              addPiece(new ChessPosition(1, lineCol), new ChessPiece(black, ChessPiece.PieceType.QUEEN));
                      case 5 ->
                              addPiece(new ChessPosition(1, lineCol), new ChessPiece(black, ChessPiece.PieceType.KING));
                      //should never be called
                      default -> throw new IllegalStateException("Unexpected value: " + lineCol);
                  }
              }


           }
        }

        //for piece in pieceList
        //(row, pieceList.index(piece)+1)



    }
}
