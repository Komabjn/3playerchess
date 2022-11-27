package com.komabjn.threeplayerchess.api.rendering.highlight;

/**
 *
 * @author Komabjn
 */
public interface HighlightTypeProvider {

    public HighlightType getHighlightType(HighlightReason highlightReason);

}
