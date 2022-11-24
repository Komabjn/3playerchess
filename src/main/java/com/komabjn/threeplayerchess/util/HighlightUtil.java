package com.komabjn.threeplayerchess.util;

import com.komabjn.threeplayerchess.rendering.api.highlight.HighlightReason;
import com.komabjn.threeplayerchess.rendering.api.highlight.HighlightType;
import com.komabjn.threeplayerchess.rendering.api.highlight.HighlightTypeProvider;

/**
 *
 * @author Komabjn
 */
public class HighlightUtil {

    public static HighlightTypeProvider getDefaultHighlightTypeProvider() {
        return new HighlightTypeProvider() {
            @Override
            public HighlightType getHighlightType(HighlightReason highlightReason) {
                switch (highlightReason) {
                    case SELECTED_PIECE:
                        return HighlightType.FIELD;
                    case POSSIBLE_MOVE:
                        return HighlightType.DOT;
                    case POSSIBLE_CAPTURE:
                        return HighlightType.DOT_AND_BORDER;
                    case CHECK:
                        return HighlightType.BORDER;
                    case OWN_PIECE_POSITION_CHANGE:
                    case OPONENT_PIECE_POSITION_CHANGE:
                        return HighlightType.FIELD;
                    default:
                        return HighlightType.FIELD;
                }
            }
        };
    }

}
