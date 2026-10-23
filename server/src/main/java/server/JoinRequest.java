package server;

import chess.ChessGame;

public record JoinRequest(ChessGame.TeamColor teamColor, Integer gameID) {
}
