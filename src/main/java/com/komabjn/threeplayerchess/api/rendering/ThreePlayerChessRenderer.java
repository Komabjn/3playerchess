package com.komabjn.threeplayerchess.api.rendering;

import com.komabjn.threeplayerchess.api.rendering.highlight.Highlight;
import com.komabjn.threeplayerchess.api.Player;
import com.komabjn.threeplayerchess.api.chessboard.ChessboardState;
import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightTypeProvider;
import java.util.List;

/**
 *
 * @author Komabjn
 */
public interface ThreePlayerChessRenderer {
    
    /**
     * 
     * @param chessboardState current state of chessboard
     * @param player whose perspective we are rendering
     * @param highlights list of highlights on the checkboard
     */
    public void render(ChessboardState chessboardState, Player player, List<Highlight> highlights);
    
    public void setColorModel(ChessboardColorModel colorModel);
    
    public void setHighlightTypeProvider(HighlightTypeProvider highlightTypeProvider);
    
}
