package com.komabjn.threeplayerchess.util;

import com.komabjn.threeplayerchess.api.chessboard.Position;
import com.komabjn.threeplayerchess.api.rendering.ChessboardColorModel;
import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightReason;
import java.awt.Color;

/**
 *
 * @author Komabjn
 */
public class ColorUtil {

    public static ChessboardColorModel getDefaultColorModel() {
        return new ChessboardColorModel() {
            @Override
            public Color getChessboardColorMain() {
                return Color.WHITE;
            }

            @Override
            public Color getChessboardColorAlternate() {
                return Color.LIGHT_GRAY;
            }

            @Override
            public Color getContourColor() {
                return Color.BLACK;
            }

            @Override
            public Color getLabelColor() {
                return Color.BLACK;
            }

            @Override
            public Color getHighlightColor(HighlightReason highlightReason) {
                switch (highlightReason) {
                    case SELECTED_PIECE:
                    case POSSIBLE_MOVE:
                    case POSSIBLE_CAPTURE:
                        return Color.GREEN;
                    case CHECK:
                        return Color.PINK;
                    case OWN_PIECE_POSITION_CHANGE:
                    case OPONENT_PIECE_POSITION_CHANGE:
                        return Color.BLUE;
                    default:
                        return Color.MAGENTA;
                }
            }
        };
    }

    public static boolean isAlternateColorField(Position position) {
        // only fields in this part dont fit general rule SMH
        if (position.getPositionLetter().ordinal() >= 8 && position.getPositionNumber().ordinal() >= 8) {
            return (position.getPositionLetter().ordinal() % 2 == 0 && position.getPositionNumber().ordinal() % 2 == 1)
                    || (position.getPositionLetter().ordinal() % 2 == 1 && position.getPositionNumber().ordinal() % 2 == 0);
        }
        return (position.getPositionLetter().ordinal() % 2 == 0 && position.getPositionNumber().ordinal() % 2 == 0)
                || (position.getPositionLetter().ordinal() % 2 == 1 && position.getPositionNumber().ordinal() % 2 == 1);
    }

    /**
     * source:
     * http://www.java2s.com/Code/Java/2D-Graphics-GUI/Blendtwocolors.htm
     *
     * @param c0
     * @param c1
     * @return
     */
    public static Color blend(Color c0, Color c1) {
        double totalAlpha = c0.getAlpha() + c1.getAlpha();
        double weight0 = c0.getAlpha() / totalAlpha;
        double weight1 = c1.getAlpha() / totalAlpha;

        double r = weight0 * c0.getRed() + weight1 * c1.getRed();
        double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
        double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
        double a = Math.max(c0.getAlpha(), c1.getAlpha());

        return new Color((int) r, (int) g, (int) b, (int) a);
    }
    
    public static Color transparentize(Color c){
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() / 2);
    }

}
