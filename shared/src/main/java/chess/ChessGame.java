package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    public ChessGame() {
        board.resetBoard();
    }
    //variables
     private TeamColor turnColor = TeamColor.WHITE;
     private ChessBoard board = new ChessBoard();



    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        //get the color of whose turn it is
            return turnColor;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        //set whose turn it is
        turnColor = team;
    }

    public void teamTurnSwap(){
        if (turnColor == TeamColor.WHITE){
            turnColor = TeamColor.BLACK;
        } else if (turnColor == TeamColor.BLACK) {
            turnColor = TeamColor.WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //get the piece and then all its valid moves
        //account for Check
        Collection<ChessMove> allMoves = new ArrayList<>();
        ChessPiece piece = board.getPiece(startPosition);
        if(piece != null){
            allMoves = piece.pieceMoves(board,startPosition);
            if (allMoves.isEmpty()){
                return allMoves;
            }else{
                for (ChessMove move : allMoves){
                    ChessBoard testboard = board.clone(); //copies the board
                    //performs the move on the hypothetical board
                        if(move.getPromotionPiece() != null){ //pawn promo
                            testboard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
                            testboard.addPiece(move.getStartPosition(), null);
                        }else{
                            testboard.addPiece(move.getEndPosition(), piece);
                            testboard.addPiece(move.getStartPosition(), null);
                        }
                    if (checkBoardForCheck(testboard, piece.getTeamColor())){
                        allMoves.remove(move);
                    }
                }
            }
        }
        return allMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //attempt to make the move, but throw invalid if the move is not possible
        //so likely look at move, check if on validMoves, throw if not, and move piece if it is
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());
        if (movingPiece!= null && validMoves(move.getStartPosition()).contains(move) && movingPiece.getTeamColor() == getTeamTurn()){
            if(move.getPromotionPiece() != null){ //pawn promo
                board.addPiece(move.getEndPosition(), new ChessPiece(movingPiece.getTeamColor(), move.getPromotionPiece()));
                board.addPiece(move.getStartPosition(), null);
            }else{
                board.addPiece(move.getEndPosition(), movingPiece);
                board.addPiece(move.getStartPosition(), null);
            }
            teamTurnSwap();
        }
        else{
            throw new InvalidMoveException();
        }
    }


    //locates the king for check
    public ChessPosition findKing(ChessBoard passedBoard, TeamColor color){
       ChessPosition kingPos = new ChessPosition(1,1);
       for (int r = 1; r <=8; r++){
           for (int c = 1; c <=8; c++){
               ChessPosition testPos = new ChessPosition(r,c);
               ChessPiece testPiece = passedBoard.getPiece(testPos);
               if (testPiece != null && testPiece.getPieceType() == ChessPiece.PieceType.KING && testPiece.getTeamColor() == color){
                   kingPos = testPos;
                   break;
               }
           }
       }
       return kingPos;
    }

    //checks if king is in check
    public boolean checkBoardForCheck(ChessBoard passedBoard, TeamColor color){
        boolean isChecked = false;
        ChessPosition kingPos = findKing(passedBoard, color);
        //get enemy pieces
        HashMap<ChessPiece, ChessPosition> enemyPieceList = new HashMap<>();
        for (int r = 1; r <=8; r++){
            for (int c = 1; c <=8; c++){
                ChessPosition testPos = new ChessPosition(r,c);
                ChessPiece testPiece = passedBoard.getPiece(testPos);
                if (testPiece != null && testPiece.getTeamColor() != color){
                    enemyPieceList.put(testPiece, testPos);
                }
            }
        }
        //check if pieces put king in check
        for (ChessPiece enemyPiece : enemyPieceList.keySet()){
            Collection<ChessMove> enemyMove = enemyPiece.pieceMoves(passedBoard,enemyPieceList.get(enemyPiece));
            for (ChessMove move : enemyMove){
                ChessPosition kingComparePosition = move.getEndPosition();
                int kingRow = kingPos.getRow();
                int kingCol = kingPos.getColumn();
                int compRow = kingComparePosition.getRow();
                int compCol = kingComparePosition.getColumn();
                if ((compRow == kingRow) && (compCol == kingCol)) {
                    isChecked = true;
                    break;
                }
            }
        }

        return isChecked;
    }

    //checks if there are moves for all pieces of color
    public boolean canMove(ChessBoard board, TeamColor color){
        boolean movePossible = true;
        for (int r = 1; r <=8; r++){
            for (int c = 1; c <=8; c++){
                ChessPosition testPos = new ChessPosition(r,c);
                ChessPiece testPiece = board.getPiece(testPos);
                if ((testPiece != null && testPiece.getTeamColor() == color) && !validMoves(testPos).isEmpty()){
                   movePossible = false;
                }
            }
        }
        return movePossible;
    }



    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //checks if King is in check; check true and has validMoves
        return (checkBoardForCheck(board, teamColor) && canMove(board, teamColor));
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //checks if game someone won
        //check if king in check and no escape; validMoves is null and check true
        return (checkBoardForCheck(board, teamColor) && !canMove(board, teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //checks if it's a draw
        //validMoves is null and King NOT in check
        return !checkBoardForCheck(board, teamColor) && !canMove(board, teamColor);
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        //set the board for each turn
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        //gets current chessboard
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return turnColor == chessGame.turnColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(turnColor, board);
    }
}
