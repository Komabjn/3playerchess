package com.komabjn.threeplayerchess.api.rendering;

import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightReason;
import java.awt.Color;

/**
 *
 * @author Komabjn
 */
public interface ChessboardColorModel {

    public Color getChessboardMainColor();

    public Color getChessboardAlternateColor();

    public Color getContourColor();

    public Color getLabelColor();

    public Color getHighlightColor(HighlightReason highlightType);

}
