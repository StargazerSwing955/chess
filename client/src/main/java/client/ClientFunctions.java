package client;

import java.util.Scanner;

public class ClientFunctions {
    //4 pre-login: register, login, help, quit/exit
    Scanner scanner = new Scanner(System.in);

    boolean isSignedIn = false;

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
                case "quit" -> "quit";
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
            printMessage = "Options: \n" + "- logout \n" + "- list \n" + "- create \n" + "- join \n" + "- quit \n";
        } else {
            printMessage = "Options: \n" + "- register \n" + "- login \n" + "- quit \n";
        }
        return printMessage;
    }


    //register
    public String register(String username, String password, String email){
        isSignedIn = true;

        return "registered\n";
    }

    //login
    public String login(String username, String password){
        isSignedIn = true;

        return "logged in\n";
    }

    //logout
    public String logout(){
        isSignedIn = false;

        return "logged out\n";
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

}
