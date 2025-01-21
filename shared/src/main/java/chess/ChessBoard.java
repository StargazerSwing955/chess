package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    //remember to override equals() and hashcode()
    public ChessBoard() {
        // where to create chess board
        int[][] chessBoard = new int[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //places Piece on the board based on the Position to add it and its type
        //no return?
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //states what is on that Position
        //returns piece or null
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //resets board; wow, exactly what's name says!
        throw new RuntimeException("Not implemented");
        //8 pawns
        //Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook
        //Q and King lined up across the board
    }
}
