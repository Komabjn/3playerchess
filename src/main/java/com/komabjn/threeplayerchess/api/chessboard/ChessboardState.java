package com.komabjn.threeplayerchess.api.chessboard;

import com.komabjn.threeplayerchess.api.ChessFigure;
import com.komabjn.threeplayerchess.api.Player;
import java.util.Set;

/**
 *
 * @author Komabjn
 */
public class ChessboardState {
    
    private Set<ChessFigure> figure;
    private Player nextPlayer;

    public Set<ChessFigure> getFigure() {
        return figure;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }
    
}
