package com.komabjn.threeplayerchess.util;

import com.komabjn.threeplayerchess.rendering.api.ChessboardColorModel;
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
        };
    }

}
