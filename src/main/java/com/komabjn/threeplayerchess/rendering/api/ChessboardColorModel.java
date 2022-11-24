package com.komabjn.threeplayerchess.rendering.api;

import com.komabjn.threeplayerchess.rendering.api.highlight.HighlightReason;
import java.awt.Color;

/**
 *
 * @author Komabjn
 */
public interface ChessboardColorModel {

    /**
     * curently unsupported
     *
     * @return
     */
    public Color getChessboardColorMain();

    public Color getChessboardColorAlternate();

    public Color getContourColor();

    public Color getLabelColor();

    public Color getHighlightColor(HighlightReason highlightType);
    
}
