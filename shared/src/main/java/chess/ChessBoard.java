package chess;

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

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //r n b q k b n r
        //p p p p p p p p

        //p p p p p p p p
        //r n b q k b n r

    }
}
