package com.komabjn.threeplayerchess.api.rendering.highlight;

import com.komabjn.threeplayerchess.api.chessboard.Position;

/**
 *
 * @author Komabjn
 */
public class Highlight {

    private final Position highlightedPosition;
    private final HighlightReason highlightReason;

    public Highlight(Position highlightedPosition, HighlightReason highlightReason) {
        this.highlightedPosition = highlightedPosition;
        this.highlightReason = highlightReason;
    }

    public Position getHighlightedPosition() {
        return highlightedPosition;
    }

    public HighlightReason getHighlightReason() {
        return highlightReason;
    }
}
