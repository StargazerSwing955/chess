package chess;

import java.util.ArrayList;
import java.util.List;

public class MoveType {
    /*
        performs the calculations of each piece moves, and appends them as the end position in a ChessMove
        returns list of possible moves

        for later:
        return List.of();

     */
    public MoveType(ChessBoard board, ChessPiece piece, ChessPosition position) {
        rookMove(board, piece, position);
    }


    /*
    1, get the piece's position - row and col (set them as a var)
    2, for each calc:
       perform calculation, determine if in array,
       set as endPosition in a new move, append to moveList
    3, return moveList
     */

        //ChessMove function!!
        // have conditional for pawn promos
        public ChessMove moveMaker(ChessPosition position, int newRow, int newCol){
            //ChessPosition newPos = ChessPosition(newRow,newCol);

            return null;
        }

        //row and col is -1 in the get piece method to accommodate 0 indexing of the board
        // so final positions must also be 1-9

        //n is how many spaces we are moving, n must be 1-8

        //bishops - bishopMove (diagonal moves)
        //row+-n && col +-n, if neither would take it off the board
        //(r || c) + n <= 8 && (r || c) - n > 0 [not indexed by 0]
        //test looks like it checks (r+n, c+n) then (r-n, c-n)

        //rooks - rookMove (flat moves, horizontal/vertical)
        //row +- n || col +- n if (r||c) + n >=9 or (r||c) - n >=1
        public List<ChessMove> rookMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            List<ChessMove> moveList = new ArrayList<>();
             int col = position.getColumn();
             int row = position.getRow();
             int n = 1;
             while (n < 8){
                 //adding row
                 int r = row + n;
                 if ((1 <= r) && (r <= 9)){
                     //make chess move
                     ChessMove move = moveMaker(position, r, col);

                     moveList.add(move);
                 }

                n++;
            }

            return moveList;

        }

        //queen - queenMove
        // bishop + rook moves

        //king - kingMove
        //queen moves where n is hard coded as 1

        //knights - knightMove
        // [row + 2, col +- 1], [row - 2, col +-1]
        // [col + 2, row +- 1], [col -2, row +-1]

        //pawns - pawnMove
        // row + 1 (assuming the teams view their side as row 1)
        // if other team piece at [row+1, col+-1], add to list
        // if row + 1 = 9, get promotion




}
