package com.komabjn.threeplayerchess.api.chessboard;

import com.komabjn.threeplayerchess.api.ChessFigure;
import com.komabjn.threeplayerchess.api.Player;
import java.util.Set;

/**
 *
 * @author Komabjn
 */
public class ChessboardState {
    
    private final Set<ChessFigure> figures;
    private final Player nextPlayer;

    public ChessboardState(Set<ChessFigure> figures, Player nextPlayer) {
        this.figures = figures;
        this.nextPlayer = nextPlayer;
    }
    
    public Set<ChessFigure> getFigures() {
        return figures;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }
    
}
