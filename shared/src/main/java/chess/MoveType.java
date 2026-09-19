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

        kingMove(board, piece, position);
        queenMove(board, piece, position);
        bishopMove(board, piece, position);
        rookMove(board, piece, position);
        knightMove(board, piece, position);
        pawnMove(board, piece, position);

        //return switch (piece.getPieceType()) { //gets the pieceType and calls MoveType based on response
//            case KING -> List.of();
//            case QUEEN -> List.of();
//            case BISHOP -> List.of();
//            case ROOK -> rookMove(board, piece, position);
//            case KNIGHT -> List.of();
//            case PAWN -> List.of();
//            default -> //should never be called if everything is working right
//                    List.of();
//        };


    }


    /*
    1, get the piece's position - row and col (set them as a var)
    2, for each calc:
       perform calculation, determine if in array,
       set as endPosition in a new move, append to moveList
    3, return moveList
     */

        //ChessMove maker function!!


        // have conditional for pawn promos
        public ChessMove moveMaker(ChessPosition position, int newRow, int newCol, ChessPiece forPieceType){
            ChessPosition newPos = new ChessPosition(newRow,newCol);


            if (forPieceType.getPieceType()== (ChessPiece.PieceType.PAWN) && (newRow == 8)) {
                //pawn promotion
                return new ChessMove(position, newPos, null);
            }
            else{
                return new ChessMove(position, newPos, null);
            }

        }

        //row and col is -1 in the get piece method to accommodate 0 indexing of the board
        // so final positions must also be 1-9

        //n is how many spaces we are moving, n must be 1-8

        //bishops - bishopMove (diagonal moves)
        //row+-n && col +-n, if neither would take it off the board
        //(r || c) + n <= 8 && (r || c) - n > 0 [not indexed by 0]
        //test looks like it checks (r+n, c+n) then (r-n, c-n)
        public List<ChessMove> bishopMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            List<ChessMove> moveList = new ArrayList<>();
            int col = position.getColumn();
            int row = position.getRow();
            int n = 1;
//            while (n <= 7){ //does all directions
//                //vars for each direction
//                int l = row - n;
//                int r = row + n;
//                int u = col + n;
//                int d = col - n;
//                int[] rowList = {l, r};
//                int[] colList = {u, d};
//                //maybe add them all to a list and loop through them
//                for (int rm : rowList) {
//                    for (int colm : colList) {
//                        if (((1 <= colm) && (colm <= 8) && ((1 <= rm) && (rm <= 8)))) {
//                            //make chess move
//                            ChessMove move = moveMaker(board, position, rm, colm, piece);
//                            //append move to list
//                            moveList.add(move);
//                        }
//                    }
//
//                }
//
//                n++;
//            }


            return moveList;
        }


        //rooks - rookMove (flat moves, horizontal/vertical)
        //row +- n || col +- n if (r||c) + n >=8 or (r||c) - n >=1
        public List<ChessMove> rookMove(ChessBoard board, ChessPiece piece, ChessPosition position){
             List<ChessMove> moveList = new ArrayList<>();
             int col = position.getColumn();
             int row = position.getRow();
             int n = 1;
//             while (n <= 7){
//                 //vars for each direction
//                 int l = row - n;
//                 int r = row + n;
//                 int u = col + n;
//                 int d = col - n;
//                 int[] rowList = {l, r};
//                 int[] colList = {u, d};
//                 //maybe add them all to a list and loop through them
//                 for (int rm : rowList) {
//                     if (((1 <= rm) && (rm <= 8))) {
//                         //make chess move
//                         ChessMove move = moveMaker(board, position, rm, col, piece);
//                         //append move to list
//                         moveList.add(move);
//                     }
//                 }
//                 for (int colm : colList) {
//                     if (((1 <= colm) && (colm <= 8))) {
//                         //make chess move
//                         ChessMove move = moveMaker(board, position, row, colm, piece);
//                         //append move to list
//                         moveList.add(move);
//                     }
//                 }
//
//                n++;
//            }


            return moveList;

        }

        //queen - queenMove
        // bishop + rook moves
        public List<ChessMove> queenMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            List<ChessMove> moveList = new ArrayList<>();
            //moveList = rookMove(board,piece,position) + bishopMove(board,piece,position);

            return moveList;
        }

        //king - kingMove
        //queen moves where n is hard coded as 1

    public List<ChessMove> kingMove(ChessBoard board, ChessPiece piece, ChessPosition position){
        List<ChessMove> moveList = new ArrayList<>();


        return moveList;
    }

        //knights - knightMove
        // [row + 2, col +- 1], [row - 2, col +-1]
        // [col + 2, row +- 1], [col -2, row +-1]
        public List<ChessMove> knightMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            List<ChessMove> moveList = new ArrayList<>();


            return moveList;
        }


        //pawns - pawnMove
        // row + 1 (assuming the teams view their side as row 1)
        // if other team piece at [row+1, col+-1], add to list
        // if row + 1 = 9, get promotion
        public List<ChessMove> pawnMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            List<ChessMove> moveList = new ArrayList<>();


            return moveList;
        }




}
