package com.komabjn.threeplayerchess.api.chessboard;

import com.komabjn.threeplayerchess.api.ChessPiece;
import com.komabjn.threeplayerchess.api.Player;
import java.util.Set;

/**
 *
 * @author Komabjn
 */
public class ChessboardState {

    private final Set<ChessPiece> pieces;
    private final Player nextPlayer;

    public ChessboardState(Set<ChessPiece> pieces, Player nextPlayer) {
        this.pieces = pieces;
        this.nextPlayer = nextPlayer;
    }

    public Set<ChessPiece> getFigures() {
        return pieces;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

}
