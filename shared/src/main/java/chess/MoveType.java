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


    }


    /*
    1, get the piece's position - row and col (set them as a var)
    2, for each calc:
       perform calculation, determine if in array,
       set as endPosition in a new move, append to moveList
    3, return moveList
     */

        //ChessMove maker function!!

        //filters out what moves (the lists) it can keep
        public ArrayList<ChessMove> moveFilter(ArrayList<ArrayList<int[]>> moveLists, ChessPiece piece, ChessPosition position, ChessBoard board){
           ArrayList<ChessMove> posMoves = new ArrayList<ChessMove>(); //possible moves

            for(ArrayList<int[]> mlist : moveLists){ //mlist for movelist
                for(int[] rc : mlist){ //rc for row-column since these are actual places on the board
                    int row = rc[0];
                    int col = rc[1];
                    ChessPosition pos = new ChessPosition(row, col);
                    ChessPiece potPiece = board.getPiece(pos); //potPiece for potential piece
                    if (potPiece != null){
                        if (potPiece.getTeamColor() != piece.getTeamColor()){
                            posMoves.add(moveMaker(position, row, col, piece));
                        }
                        break;
                    }
                    posMoves.add(moveMaker(position, row, col, piece));

                }

            }

            return posMoves;
        }

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

        //flatMove - row or column
        //does the math for POSSIBLE moves in the horizontal and vertical directions
        public ArrayList<ArrayList<int[]>> flatMove(ChessBoard board, ChessPiece piece, ChessPosition position, int max) {
            ArrayList<ArrayList<int[]>> straightCoords = new ArrayList<ArrayList<int[]>>();
            int col = position.getColumn();
            int row = position.getRow();
            int n = 1;
           ArrayList<int[]> leftCoords = new ArrayList<int[]>();
           ArrayList<int[]> rightCoords = new ArrayList<int[]>();
           ArrayList<int[]> upCoords = new ArrayList<int[]>();
           ArrayList<int[]> downCoords = new ArrayList<int[]>();

            while (n <= max) {
                //vars for each direction
                int l = row - n; //left
                int r = row + n; //right
                int u = col + n; //up
                int d = col - n; //down
                int[] calcVals = {l, r, u, d};

                //left
                if (((1 <= l) && (l <= 8))) {
                    int[] coords = {l, col};
                    leftCoords.add(coords);
                }
                //right
                if (((1 <= r) && (r <= 8))) {
                    int[] coords = {r, col};
                    rightCoords.add(coords);
                }
                //up
                if (((1 <= u) && (u <= 8))) {
                    int[] coords = {row, u};
                    upCoords.add(coords);
                }
                //down
                if (((1 <= d) && (d <= 8))) {
                    int[] coords = {row, d};
                    downCoords.add(coords);
                }

                n++;
            } //add all the lists
                straightCoords.add(leftCoords);
                straightCoords.add(rightCoords);
                straightCoords.add(upCoords);
                straightCoords.add(downCoords);

            return straightCoords;
        }



        //diaMove - diagonal moves




        //row and col is -1 in the get piece method to accommodate 0 indexing of the board
        // so final positions must also be 1-9

        //n is how many spaces we are moving, n must be 1-8

        //bishops - bishopMove (diagonal moves)
        //row+-n && col +-n, if neither would take it off the board
        //(r || c) + n <= 8 && (r || c) - n > 0 [not indexed by 0]
        //test looks like it checks (r+n, c+n) then (r-n, c-n)
        public ArrayList<ChessMove> bishopMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ChessMove> moveList = new ArrayList<>();
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

           ArrayList<ArrayList<int[]>> tryMoves = flatMove(board, piece, position, 7);
           ArrayList<ChessMove> moveList = moveFilter(tryMoves,piece,position,board);

            return moveList;

        }

        //queen - queenMove
        // bishop + rook moves
        public ArrayList<ChessMove> queenMove(ChessBoard board, ChessPiece piece, ChessPosition position){
           ArrayList<ChessMove> moveList = new ArrayList<>();
            //moveList = rookMove(board,piece,position) + bishopMove(board,piece,position);

            return moveList;
        }

        //king - kingMove
        //queen moves where n is hard coded as 1

    public ArrayList<ChessMove> kingMove(ChessBoard board, ChessPiece piece, ChessPosition position){
        ArrayList<ChessMove> moveList = new ArrayList<>();


        return moveList;
    }

        //knights - knightMove
        // [row + 2, col +- 1], [row - 2, col +-1]
        // [col + 2, row +- 1], [col -2, row +-1]
        public ArrayList<ChessMove> knightMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ChessMove> moveList = new ArrayList<>();


            return moveList;
        }


        //pawns - pawnMove
        // row + 1 (assuming the teams view their side as row 1)
        // if other team piece at [row+1, col+-1], add to list
        // if row + 1 = 9, get promotion
        public ArrayList<ChessMove> pawnMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ChessMove> moveList = new ArrayList<>();


            return moveList;
        }




}
