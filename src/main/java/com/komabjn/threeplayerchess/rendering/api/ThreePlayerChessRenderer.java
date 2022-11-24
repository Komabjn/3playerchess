package com.komabjn.threeplayerchess.rendering.api;

import com.komabjn.threeplayerchess.api.Player;
import com.komabjn.threeplayerchess.api.chessboard.ChessboardState;

/**
 *
 * @author Komabjn
 */
public interface ThreePlayerChessRenderer {
    
    /**
     * 
     * @param chessboardState current state of chessboard
     * @param player whose perspective we are rendering
     */
    public void render(ChessboardState chessboardState, Player player);
    
    public void setColorModel(ChessboardColorModel colorModel);
    
}
