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


    }


    /*
    1, get the piece's position - row and col (set them as a var)
    2, for each calc:
       perform calculation, determine if in array,
       set as endPosition in a new move, append to moveList
    3, return moveList
     */

    //does the logic for moves with iterable possible locations
    public ArrayList<ChessMove> moveLine (ChessBoard board, ChessPiece piece, ChessPosition position, int xOffSet, int yOffSet){
        ArrayList<ChessMove> movesInLine = new ArrayList<>();
        ChessPosition pos = position.offSet(xOffSet,yOffSet);

        while (pos.isValid()) {
            ChessPiece potentialPiece = board.getPiece(pos);

            if (potentialPiece == null){
                movesInLine.add(new ChessMove(position,pos, null));
            }
            else if (potentialPiece.getTeamColor()!=piece.getTeamColor()) {
                movesInLine.add(new ChessMove(position,pos, null));
                break;
            }
            else{
                break;
            }

            pos = pos.offSet(xOffSet,yOffSet);

        }


        return movesInLine;
    }

    //does the logic for moves with a set list of possible moving locations
    public ArrayList<ChessMove> moveKingAndHorse(ChessBoard board, ChessPiece piece, ChessPosition position, int[][] offSetList){
        ArrayList<ChessMove> moveStatic = new ArrayList<>();

        for(int[] offset : offSetList){
            //offset[0] row
            //offset[1] col
            ChessPosition newPos = position.offSet(offset[0],offset[1]);
            if (newPos.isValid()) {
                ChessPiece potentialPiece = board.getPiece(newPos);
                if (potentialPiece == null) {
                    moveStatic.add(new ChessMove(position, newPos, null));
                } else if (potentialPiece.getTeamColor() != piece.getTeamColor()) {
                    moveStatic.add(new ChessMove(position, newPos, null));
                }
            }

        }
        return moveStatic;
    }


    public ArrayList<ChessMove> genericPawn(ChessBoard board, ChessPiece piece, ChessPosition position, int direction, int startRow, ChessPiece.PieceType promoType){
        ArrayList<ChessMove> pawnLegalMoves = new ArrayList<>();

        //straight ahead move
        ChessPosition pos = new ChessPosition(position.getRow()+direction, position.getColumn());
        if (pos.isValid()){
            ChessPiece blockPiece = board.getPiece(pos);
            if (blockPiece == null){
                pawnLegalMoves.add(new ChessMove(position, pos, promoType));

                //pawn jump
                if (position.getRow() == startRow){
                    ChessPosition jumpPos = new ChessPosition(pos.getRow()+direction, position.getColumn());

                    ChessPiece jumpPiece = board.getPiece(jumpPos);
                    if (jumpPiece == null) {
                        pawnLegalMoves.add(new ChessMove(position, jumpPos, promoType));
                    }

                }
            }

        }

        //corners
        List<ChessPosition> corners = List.of(new ChessPosition(position.getRow()+direction, position.getColumn()+1),new ChessPosition(position.getRow()+direction, position.getColumn()-1));
        for (ChessPosition corner: corners){
            if (corner.isValid()){
                ChessPiece cornerPiece = board.getPiece(corner);
                if (cornerPiece != null && cornerPiece.getTeamColor() != piece.getTeamColor()){
                    pawnLegalMoves.add(new ChessMove(position, corner, promoType));
                }

            }
        }




        return pawnLegalMoves;

    }


    public ArrayList<ChessMove> bishopMove(ChessBoard board, ChessPiece piece, ChessPosition position) {
        ArrayList<ChessMove> potentialMoves = moveLine(board, piece, position, 1,1 );
        potentialMoves.addAll(moveLine(board, piece, position, -1,-1 ));
        potentialMoves.addAll(moveLine(board, piece, position, 1,-1 ));
        potentialMoves.addAll(moveLine(board, piece, position, -1,1 ));




        return potentialMoves;
    }

    public ArrayList<ChessMove> rookMove(ChessBoard board, ChessPiece piece, ChessPosition position) {
        ArrayList<ChessMove> potentialMoves = moveLine(board, piece, position, 1,0 );
        potentialMoves.addAll(moveLine(board, piece, position, -1,0 ));
        potentialMoves.addAll(moveLine(board, piece, position, 0,-1 ));
        potentialMoves.addAll(moveLine(board, piece, position, 0,1 ));



        return potentialMoves;
    }

    public ArrayList<ChessMove> queenMove(ChessBoard board, ChessPiece piece, ChessPosition position) {
        ArrayList<ChessMove> potentialMoves = bishopMove(board, piece,position);
        potentialMoves.addAll(rookMove(board,piece,position));

        return potentialMoves;

    }

    public ArrayList<ChessMove> kingMove(ChessBoard board, ChessPiece piece, ChessPosition position){
        int[][] moveOffSets = {{0,-1},{1, -1}, {1,0}, {1,1}, {0, 1}, {-1,1},{-1,0}, {-1,-1}};

        return moveKingAndHorse(board, piece, position, moveOffSets);
    }

    public ArrayList<ChessMove> knightMove(ChessBoard board, ChessPiece piece, ChessPosition position){
        int[][] moveOffSets = {{2,1},{2, -1}, {-2,-1}, {-2,1}, {1, 2}, {-1,2},{1,-2}, {-1,-2}};

        return moveKingAndHorse(board, piece, position, moveOffSets);
    }

    public ArrayList<ChessMove> pawnMove(ChessBoard board, ChessPiece piece, ChessPosition position){
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();
        boolean isWhite = piece.getTeamColor().equals(ChessGame.TeamColor.WHITE);
        int startingRow = isWhite? 2 : 7;
        int promoRow = isWhite? 8 : 1;
        int direction = isWhite? 1 : -1; //always affects row

        /*pawn logic:
        White: starts at 2, moves +1, jumps 2, promotes at 8
        Black: starts at 7, moves -1, jumps -2, promotes at 1
         */
        List<ChessPiece.PieceType> promoPieces = List.of(ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK);
        if (position.getRow()+direction == promoRow){
            for (ChessPiece.PieceType type : promoPieces){
                pawnMoves.addAll(genericPawn(board, piece,position, direction, startingRow,type));
            }
        }
        else {
            pawnMoves.addAll(genericPawn(board, piece, position, direction, startingRow, null));
        }


        return pawnMoves;
    }






}
