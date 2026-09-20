package chess;

import java.util.ArrayList;
import java.util.List;

import static chess.ChessPiece.*;

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
                            posMoves.add(moveMaker(position, row, col, piece, null));
                        }
                        if(piece.getPieceType() == PieceType.KNIGHT){
                            continue;
                        }
                        else{
                            break;
                        }
                    }
                    posMoves.add(moveMaker(position, row, col, piece, null));
                }
            }
            return posMoves;
        }

        // have conditional for pawn promos
        public ChessMove moveMaker(ChessPosition position, int newRow, int newCol, ChessPiece forPieceType, PieceType promoType){
            ChessPosition newPos = new ChessPosition(newRow,newCol);

            int promoRow = 0;
            switch (forPieceType.getTeamColor()){
                case BLACK -> promoRow = 1;
                case WHITE -> promoRow = 8;
            }

            if (forPieceType.getPieceType()== (PieceType.PAWN) && (newRow == promoRow)) {
                //pawn promotion
                return new ChessMove(position, newPos, promoType);
            }
            else{
                return new ChessMove(position, newPos, null);
            }

        }

        public ArrayList<ChessMove> pPromoMove(ChessBoard board, ChessPiece piece, ChessPosition position){

            int row = position.getRow();
            int col = position.getColumn();

            ArrayList<ChessMove> pnMove = new ArrayList<ChessMove>();
            int newRow = 0;

            switch (piece.getTeamColor()){
                case BLACK -> newRow = row - 1;
                case WHITE -> newRow = row + 1;
            }

            PieceType[] types = new PieceType[]{PieceType.QUEEN,
                    PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT};

            for(PieceType type: types) {

                int leftCol = col - 1;
                int rightCol = col + 1;

                if ((1 <= newRow) && (newRow <= 8)) {
                    ChessPiece potPiece = board.getPiece(new ChessPosition(newRow, col)); //potPiece for potential piece

                    if (potPiece == null) {
                        pnMove.add(moveMaker(position, newRow, col, piece, type));
                    }
                }

                if (((1 <= leftCol) && (leftCol <= 8)) && ((1 <= newRow) && (newRow <= 8))) {
                    ChessPiece cL = board.getPiece(new ChessPosition(newRow, leftCol));

                    if ((cL != null) && (cL.getTeamColor() != piece.getTeamColor())) {
                        pnMove.add(moveMaker(position, newRow, leftCol, piece, type));
                    }
                }
                if (((1 <= rightCol) && (rightCol <= 8)) && ((1 <= newRow) && (newRow <= 8))) {
                    ChessPiece cR = board.getPiece(new ChessPosition(newRow, rightCol));

                    if ((cR != null) && (cR.getTeamColor() != piece.getTeamColor())) {
                        pnMove.add(moveMaker(position, newRow, rightCol, piece, type));
                    }
                }
            }

            return pnMove;
        }

        //unpromoted moves
        public ArrayList<ChessMove> pMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            int row = position.getRow();
            int col = position.getColumn();

            ArrayList<ChessMove> pnMove = new ArrayList<ChessMove>();
            int newRow = 0;
            int startRowJump = 0;
            int jumpRow = 0;
            int promoRow = 0;

            switch (piece.getTeamColor()){
                case BLACK -> {
                    newRow = row - 1;
                    startRowJump = row - 2;
                    jumpRow = 7;
                    promoRow = 1;
                }
                case WHITE -> {
                    newRow = row + 1;
                    startRowJump = row + 2;
                    jumpRow = 2;
                    promoRow = 8;
                }
            }
            if (newRow == promoRow){
                return pPromoMove(board, piece, position);
            }

                int leftCol = col - 1;
                int rightCol = col + 1;


                if ((1 <= newRow) && (newRow <= 8)) {
                    ChessPiece potPiece = board.getPiece(new ChessPosition(newRow, col)); //potPiece for potential piece

                    if (potPiece == null) {
                        pnMove.add(moveMaker(position, newRow, col, piece, null));
                    }
                }
                if (row == jumpRow) {
                    ChessPiece potPiece = board.getPiece(new ChessPosition(startRowJump, col)); //potPiece for potential piece

                    if (potPiece == null) {
                        pnMove.add(moveMaker(position, startRowJump, col, piece, null));
                    }
                }

                if (((1 <= leftCol) && (leftCol <= 8)) && ((1 <= newRow) && (newRow <= 8))) {
                    ChessPiece cL = board.getPiece(new ChessPosition(newRow, leftCol));

                    if ((cL != null) && (cL.getTeamColor() != piece.getTeamColor())) {
                        pnMove.add(moveMaker(position, newRow, leftCol, piece, null));
                    }
                }
                if (((1 <= rightCol) && (rightCol <= 8)) && ((1 <= newRow) && (newRow <= 8))) {
                    ChessPiece cR = board.getPiece(new ChessPosition(newRow, rightCol));

                    if ((cR != null) && (cR.getTeamColor() != piece.getTeamColor())) {
                        pnMove.add(moveMaker(position, newRow, rightCol, piece, null));
                    }
                }

            return pnMove;
        }



        //flatMove - row or column
        //does the math for POSSIBLE moves in the horizontal and vertical directions
        public ArrayList<ArrayList<int[]>> flatMove(ChessPosition position, int max) {
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
                int u = row - n; //up
                int d = row + n; //down
                int r = col + n; //right
                int l = col - n; //left

                //up
                if (((1 <= u) && (u <= 8))) {
                    int[] coords = {u, col};
                    leftCoords.add(coords);
                }
                //down
                if (((1 <= d) && (d <= 8))) {
                    int[] coords = {d, col};
                    rightCoords.add(coords);
                }
                //right
                if (((1 <= r) && (r <= 8))) {
                    int[] coords = {row, r};
                    upCoords.add(coords);
                }
                //left
                if (((1 <= l) && (l <= 8))) {
                    int[] coords = {row, l};
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


        //diagonalMove - diagonal moves
        public ArrayList<ArrayList<int[]>> diagonalMove(ChessPosition position, int max) {
            ArrayList<ArrayList<int[]>> xCoords = new ArrayList<ArrayList<int[]>>();
            int col = position.getColumn();
            int row = position.getRow();
            int n = 1;
            ArrayList<int[]> upLeftCoords = new ArrayList<int[]>(); // row + n , col + n
            ArrayList<int[]> upRightCoords = new ArrayList<int[]>(); //row + n, col - n
            ArrayList<int[]> downLeftCoords = new ArrayList<int[]>(); // row - n, col + n
            ArrayList<int[]> downRightCoords = new ArrayList<int[]>(); //row - n, col - n
            while (n <= max) {
                //vars for each direction
                int u = row - n; //down
                int d = row + n; //up ; I know they're switched, I'm too tired to fix them
                int r = col + n; //right
                int l = col - n; //left

                //up left
                if (((1 <= u) && (u <= 8)) && ((1 <= l) && (l <= 8))) {
                    int[] coords = {u, l};
                    upLeftCoords.add(coords);
                }
                //up right
                if (((1 <= u) && (u <= 8)) && ((1 <= r) && (r <= 8))) {
                    int[] coords = {u, r};
                    upRightCoords.add(coords);
                }
                //down left
                if (((1 <= d) && (d <= 8)) && ((1 <= l) && (l <= 8))) {
                    int[] coords = {d, l};
                    downLeftCoords.add(coords);
                }
                //down right
                if (((1 <= d) && (d <= 8)) && ((1 <= r) && (r <= 8))) {
                    int[] coords = {d, r};
                    downRightCoords.add(coords);
                }

              n++;
            } //add all the lists
            xCoords.add(upLeftCoords);
            xCoords.add(upRightCoords);
            xCoords.add(downLeftCoords);
            xCoords.add(downRightCoords);


            return xCoords;
        }

        //horseMove - move for the knight
        public ArrayList<ArrayList<int[]>> horseMove(ChessPosition position) {
            ArrayList<ArrayList<int[]>> LCoords = new ArrayList<ArrayList<int[]>>();
            int col = position.getColumn();
            int row = position.getRow();
            ArrayList<int[]> leftCoords = new ArrayList<int[]>(); // L to the left
            ArrayList<int[]> rightCoords = new ArrayList<int[]>(); // L to the right
            ArrayList<int[]> upCoords = new ArrayList<int[]>(); // L ahead
            ArrayList<int[]> downCoords = new ArrayList<int[]>(); // L behind
            int uStem = row + 2; //up
            int dStem = row - 2; //down
            int rStem = col + 2; //right
            int lStem = col - 2; //left
            int uTail = row + 1; //up
            int dTail = row - 1; //down
            int rTail = col + 1; //right
            int lTail = col - 1; //left

            //upwards L (so T)
            if ((1 <= uStem) && (uStem <= 8)){
                if ((1 <= rTail) && (rTail <= 8)) {
                    int[] coords = {uStem, rTail};
                    upCoords.add(coords);
                }
                if((1 <= lTail) && (lTail <= 8)){
                    int[] coords = {uStem, lTail};
                    upCoords.add(coords);
                }
            }
            //downwards L
            if ((1 <= dStem) && (dStem <= 8)){
                if ((1 <= rTail) && (rTail <= 8)) {
                    int[] coords = {dStem, rTail};
                    downCoords.add(coords);
                }
                if((1 <= lTail) && (lTail <= 8)){
                    int[] coords = {dStem, lTail};
                    downCoords.add(coords);
                }
            }
            //rightward L
            if ((1 <= rStem) && (rStem <=8)){
                if((1 <= uTail) && (uTail <= 8)){
                    int[] coords = {uTail, rStem};
                    rightCoords.add(coords);
                }
                if((1 <= dTail) && (dTail <= 8)){
                    int[] coords = {dTail, rStem};
                    rightCoords.add(coords);
                }
            }
            //leftward L
            if ((1 <= lStem) && (lStem <=8)){
                if((1 <= uTail) && (uTail <= 8)){
                    int[] coords = {uTail, lStem};
                    rightCoords.add(coords);
                }
                if((1 <= dTail) && (dTail <= 8)){
                    int[] coords = {dTail, lStem};
                    rightCoords.add(coords);
                }
            }


            //add all the lists
            LCoords.add(leftCoords);
            LCoords.add(rightCoords);
            LCoords.add(upCoords);
            LCoords.add(downCoords);

            return LCoords;
        }




        //row and col is -1 in the get piece method to accommodate 0 indexing of the board
        // so final positions must also be 1-9

        //n is how many spaces we are moving, n must be 1-8

        //bishops - bishopMove (diagonal moves)
        //row+-n && col +-n, if neither would take it off the board
        //(r || c) + n <= 8 && (r || c) - n > 0 [not indexed by 0]
        //test looks like it checks (r+n, c+n) then (r-n, c-n)
        public ArrayList<ChessMove> bishopMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ArrayList<int[]>> tryMoves = diagonalMove(position, 7);

            return moveFilter(tryMoves,piece,position,board);
        }


        //rooks - rookMove (flat moves, horizontal/vertical)
        //row +- n || col +- n if (r||c) + n >=8 or (r||c) - n >=1
        public List<ChessMove> rookMove(ChessBoard board, ChessPiece piece, ChessPosition position){
           ArrayList<ArrayList<int[]>> tryMoves = flatMove(position, 7);

            return moveFilter(tryMoves,piece,position,board);

        }

        //queen - queenMove
        // bishop + rook moves
        public ArrayList<ChessMove> queenMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ArrayList<int[]>> tryMoves = flatMove(position, 7);
            ArrayList<ChessMove> moveList = moveFilter(tryMoves,piece,position,board);
            tryMoves = diagonalMove(position, 7);
            ArrayList<ChessMove> diaList = moveFilter(tryMoves,piece,position,board);
            moveList.addAll(diaList);


            return moveList;
        }

        //king - kingMove
        //queen moves where n is hard coded as 1

        public ArrayList<ChessMove> kingMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ArrayList<int[]>> tryMoves = flatMove(position, 1);
            ArrayList<ChessMove> moveList = moveFilter(tryMoves,piece,position,board);
            tryMoves = diagonalMove(position, 1);
            ArrayList<ChessMove> diaList = moveFilter(tryMoves,piece,position,board);
            moveList.addAll(diaList);

            return moveList;
        }

        //knights - knightMove
        // [row + 2, col +- 1], [row - 2, col +-1]
        // [col + 2, row +- 1], [col -2, row +-1]
        public ArrayList<ChessMove> knightMove(ChessBoard board, ChessPiece piece, ChessPosition position){
            ArrayList<ArrayList<int[]>> tryMoves = horseMove(position);

            return moveFilter(tryMoves,piece,position,board);
        }


        //pawns - pawnMove
        // row + 1 (assuming the teams view their side as row 1)
        // if other team piece at [row+1, col+-1], add to list
        // if row + 1 = 9, get promotion
        public ArrayList<ChessMove> pawnMove(ChessBoard board, ChessPiece piece, ChessPosition position){

            return pMove(board,piece,position);
        }




}
