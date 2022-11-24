package com.komabjn.threeplayerchess.rendering.api.highlight;

/**
 *
 * @author Komabjn
 */
public interface HighlightTypeProvider {

    public HighlightType getHighlightType(HighlightReason highlightReason);
    
}
