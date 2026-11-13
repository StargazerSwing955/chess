package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.AuthData;
import model.UserData;
import serverfacade.ServerFacade;
import ui.EscapeSequences;

import java.io.IOException;
import java.util.Scanner;

public class ClientFunctions {
    //4 pre-login: register, login, help, quit/exit
    Scanner scanner = new Scanner(System.in);
    ServerFacade facade = new ServerFacade("http://localhost:8080");
    boolean isSignedIn = false;
    String auToken = null;

    public void run() {
        System.out.println(" Welcome to the chess. Sign in to start.");

        var result = "";
        while (!result.equals("quit")) {
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
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
                case "register" -> register(tokens[1], tokens[2], tokens[3]);
                case "login" -> login(tokens[1], tokens[2]);
                case "help" -> help();
                case "quit" -> "quit";
                default -> "Invalid input. Please type 'help' for list of inputs \n";
            };
        } else {
            return switch (cmd) {
                case "logout"-> logout();
                case "list" -> listGames();
                case "create" -> createGame();
                case "join" -> joinGame();
                case "help" -> help();
                case "observe" -> joinAsObserver();
                default -> "Invalid input. Please type 'help' for list of inputs \n";
            };
        }
//        } catch (ResponseException ex) {
//            return ex.getMessage();
//        }
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
                    - create\s
                    - join\s
                    - observe\s
                    """;
        } else {
            printMessage = """
                    Options:\s
                    - register - please include username password and email\s
                    - login - please include username password\s
                    - quit\s
                    """;
        }
        return printMessage;
    }


    //register
    public String register(String username, String password, String email){
        UserData registerData = new UserData(username, password, email);
        AuthData signedInData;
        try {
            signedInData = facade.registerFacade(registerData);
            auToken = signedInData.authToken();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        isSignedIn = true;

        return String.format("%s registered at %s\n", signedInData.username(), signedInData.authToken());
    }

    //login
    public String login(String username, String password){
        UserData loginData = new UserData(username, password, null);
        AuthData signedInData;
        signedInData = facade.loginFacade(loginData);
        auToken = signedInData.authToken();

        isSignedIn = true;

        return String.format("%s logged in at %s\n", signedInData.username(), signedInData.authToken());
    }

    //logout
    public String logout(){
        facade.logoutFacade(new AuthData(auToken, null));
        isSignedIn = false;

        return "Successfully logged out\n";
    }

    //create game
    public String createGame(){

        return "made game\n";
    }

    //list games
    public String listGames(){

        return "games: \n";
    }

    //join game
    public String joinGame(){

        return "joined game\n";
    }

    //observe game
    public String joinAsObserver(){

        return "joined game as observer\n";
    }

    public static void printGame(ChessGame game){
        ChessBoard board = game.getBoard();

        System.out.println("  A   B   C   D   E   F   G   H  ");
        for (int row = 1; row <= 8; row++) {
            System.out.print(row + " ");
            for (int col = 1; col <= 8; col++) {
                if ((row + col) % 2 == 0) { // colors
                    System.out.print("\u001b[47m"); // Light gray background
                } else {
                    System.out.print("\u001b[100m"); // Dark gray background
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
            System.out.print(" "+row);
            System.out.println(); // New line for next row
        }
        System.out.println("  A   B   C   D   E   F   G   H  ");

    }


}
