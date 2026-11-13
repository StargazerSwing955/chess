package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.*;
import ui.EscapeSequences;

import java.util.Scanner;

public class ClientFunctions {
    //4 pre-login: register, login, help, quit/exit

    Scanner scanner = new Scanner(System.in);
    ServerFacade facade = new ServerFacade("http://localhost:8080");
    boolean isSignedIn = false;
    String auToken = null;
    ListGamesResult gameList;

    public void run() {
        System.out.println(" Welcome to the chess. Sign in to start.");

        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                String msg = e.getMessage();
                ErrorResponse errorMessage = new Gson().fromJson(msg, ErrorResponse.class);
                System.out.print(errorMessage.message()+"\n");
            }
        }
        System.out.println();
    }

    public String eval(String input) {
        //try {
        String[] tokens = input.toLowerCase().split(" ");
        String cmd = (tokens.length > 0) ? tokens[0] : "help";
        if(!isSignedIn) {
            return switch (cmd) {
                case "register" -> {
                    if(tokens.length ==4) {
                        yield register(tokens[1], tokens[2], tokens[3]);
                    }else{
                        yield "To register please give username, password, and email\n";
                    }
                }
                case "login" -> {
                    if(tokens.length ==3) {
                        yield login(tokens[1], tokens[2]);
                    }else{
                        yield "To login please give username and password\n";
                    }
                }
                case "help" -> help();
                case "quit" -> "quit";
                default -> "Invalid input. Please type 'help' for list of inputs \n";
            };
        } else {
            return switch (cmd) {
                case "logout"-> logout();
                case "list" -> listGames();
                case "create" -> {
                    if(tokens.length ==2) {
                        yield createGame(tokens[1]);
                    }else{
                        yield "Please give the game name";
                    }
                }
                case "join" -> {
                    if (tokens.length == 3) {
                        yield joinGame(tokens[1], tokens[2]);
                    }else{
                        yield "Please give the name then the game id";
                    }
                }
                case "help" -> help();
                case "observe" -> {
                    if (tokens.length == 2) {
                        yield joinAsObserver(tokens[1]);
                    } else{
                        yield "Please give game id";
                    }
                }
                default -> "Invalid input. Please type 'help' for list of inputs \n";
            };
        }
    }

    //help
    public String help(){
        //gives list of commands
        String printMessage;
        if(isSignedIn){
            printMessage = """
                    Options:\s
                    - logout\s
                    - list\s
                    - create - please include the game name\s
                    - join - please include which color to join as and the game id \s
                    - observe\s
                    """;
        } else {
            printMessage = """
                    Options:\s
                    - register - please include username password and email\s
                    - login - please include username and password\s
                    - quit\s
                    """;
        }
        return printMessage;
    }


    //register
    public String register(String username, String password, String email){
        UserData registerData = new UserData(username, password, email);
        AuthData signedInData;
        signedInData = facade.registerFacade(registerData);
        auToken = signedInData.authToken();

        isSignedIn = true;

        return String.format("%s registered\n", signedInData.username());
    }

    //login
    public String login(String username, String password){
        UserData loginData = new UserData(username, password, null);
        AuthData signedInData;
        signedInData = facade.loginFacade(loginData);
        auToken = signedInData.authToken();

        isSignedIn = true;

        return String.format("%s logged in \n", signedInData.username());
    }

    //logout
    public String logout(){
        facade.logoutFacade(new AuthData(auToken, null));
        isSignedIn = false;
        auToken = null;

        return "Successfully logged out\n";
    }

    //create game
    public String createGame(String gameName){
        CreateGameRequest passingVals = new CreateGameRequest(auToken, gameName);
        GameData createdGame = facade.createGameFacade(passingVals);

        return String.format("Game created with id %d\n",  createdGame.gameID());
    }

    //list games
    public String listGames(){
        String printList = "";
        AuthData passingVals = new AuthData(auToken, null);
        gameList = facade.gameListFacade(passingVals);
        for(GameData game: gameList.games()){
            printList += String.format("%d. Game Name: %s, White: %s, Black: %s \n", game.gameID(), game.gameName(),
                    game.whiteUsername(), game.blackUsername());
        }

        return "games:\n"+ printList;
    }

    //join game
    public String joinGame(String joinColor, String gameID){
        ChessGame.TeamColor color;
        Integer id;
        try {
            id = Integer.valueOf(gameID);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Game Id must be a number");
        }
        gameList = facade.gameListFacade(new AuthData(auToken, null));
        if(gameList.games().size()>= id){
            if (joinColor.equalsIgnoreCase("black")){
                color = ChessGame.TeamColor.BLACK;
            } else if (joinColor.equalsIgnoreCase("white")) {
                color = ChessGame.TeamColor.WHITE;
            } else {
                return "Team color can only be black or white\n";
            }

            JoinRequest joinReq = new JoinRequest(color, id);
            facade.joinFacade(joinReq,auToken);

            //get game and print it
            ChessGame joinedGame = gameList.games().get(id-1).game();
            printGame(joinedGame, color);

            return "Joined game as "+joinColor+"\n";
        }else{
            return "Game does not exist";
        }

    }

    //observe game
    public String joinAsObserver(String gameID){
        Integer id = Integer.valueOf(gameID);
        ChessGame joinedGame = gameList.games().get(id-1).game();
        printGame(joinedGame, ChessGame.TeamColor.WHITE);

        return "Joined game as observer\n";
    }







    public static void printGame(ChessGame game, ChessGame.TeamColor colorPOV){
        ChessBoard board = game.getBoard();
        int startVal;
        int endVal;
        int iterator;
        String letterLabel;
        if (colorPOV== ChessGame.TeamColor.WHITE) {
            startVal = 1;
            endVal = 9;
            iterator = 1;
            letterLabel = "  A   B   C   D   E   F   G   H  ";
        }else {
            startVal = 8;
            endVal = 0;
            iterator = -1;
            letterLabel = "  H   G   F   E   D   C   B   A  ";
        }

        System.out.println(letterLabel);
        for (int row = startVal; row != endVal; row+=iterator) {
            System.out.print((9-row) + " ");
            for (int col = startVal; col != endVal; col+=iterator) {
                if ((row + col) % 2 == 0) { // colors
                    System.out.print(EscapeSequences.SET_BG_COLOR_LIGHT_GREY);
                } else {
                    System.out.print(EscapeSequences.SET_BG_COLOR_DARK_GREY);
                }

                ChessPiece squarePiece = board.getPiece(new ChessPosition(row,col));
                if (squarePiece == null){
                    System.out.print(EscapeSequences.EMPTY);
                }else if(squarePiece.getTeamColor() == ChessGame.TeamColor.BLACK){
                    switch (squarePiece.getPieceType()){
                        case ROOK -> System.out.print(EscapeSequences.BLACK_ROOK);
                        case KNIGHT -> System.out.print(EscapeSequences.BLACK_KNIGHT);
                        case BISHOP -> System.out.print(EscapeSequences.BLACK_BISHOP);
                        case KING -> System.out.print(EscapeSequences.BLACK_KING);
                        case QUEEN -> System.out.print(EscapeSequences.BLACK_QUEEN);
                        case PAWN -> System.out.print(EscapeSequences.BLACK_PAWN);
                    }
                } else{
                    switch (squarePiece.getPieceType()){
                        case ROOK -> System.out.print(EscapeSequences.WHITE_ROOK);
                        case KNIGHT -> System.out.print(EscapeSequences.WHITE_KNIGHT);
                        case BISHOP -> System.out.print(EscapeSequences.WHITE_BISHOP);
                        case KING -> System.out.print(EscapeSequences.WHITE_KING);
                        case QUEEN -> System.out.print(EscapeSequences.WHITE_QUEEN);
                        case PAWN -> System.out.print(EscapeSequences.WHITE_PAWN);
                    }
                }
                System.out.print("\u001b[0m"); //reset
            }
            System.out.print(" "+(9-row));
            System.out.println();
        }
        System.out.println(letterLabel);

    }


}
