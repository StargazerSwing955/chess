package chess;

public class MoveType {
    /*
    hold the calculations of each piece
     */
    public MoveType(){

    }

    //get position's rows and columns
    //row and col is -1 in the get piece method to accomodate 0 indexing of the board, so final position must be 1-9
    //n is how many spaces we are moving

    //bishops
    //row+-n && col +-n, if neither would take it off the board
    //(r || c) + n <= 8 && (r || c) - n > 0 [not indexed by 0]

    //rooks
    //row +- n || col +- n if (r||n) + n >=9 or (r||n) - n < 0

    //queen
    // bishop + rook moves

    //king
    //queen moves where n is hard coded as 1

    //knight
    // row -+ 2, col +- 1
    // col +- 2, row +- 1

    //pawn
    // row + 1 (assuming the teams view their side as row 1)
    // if piece at [row+1, col+-1]




}
