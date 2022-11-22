package com.komabjn.threeplayerchess.util;

import com.komabjn.threeplayerchess.api.chessboard.Position;
import com.komabjn.threeplayerchess.api.chessboard.PositionLetter;
import com.komabjn.threeplayerchess.api.chessboard.PositionNumber;

/**
 *
 * @author Komabjn
 */
public class PositionsUtil {

    /**
     * Check whether given position is valid, meaning there is a corresponding
     * field on the chessboard.
     *
     * @param position
     * @return
     */
    public static boolean isPositionValid(Position position) {
        if (position.getPositionLetter().ordinal() < 4) {
            return position.getPositionNumber().ordinal() < 8;
        } else if (position.getPositionLetter().ordinal() < 8) {
            return position.getPositionNumber().ordinal() < 4 || position.getPositionNumber().ordinal() >= 8;
        } else {
            return position.getPositionNumber().ordinal() >= 4;
        }
    }
    
    public static boolean isPositionAlongLetterBorder(Position position){
        return position.getPositionNumber() == PositionNumber.NUMBER_1 || 
                position.getPositionNumber() == PositionNumber.NUMBER_8 ||
                position.getPositionNumber() == PositionNumber.NUMBER_12;
    }
    
    public static boolean isPositionAlongNumberBorder(Position position){
        return position.getPositionLetter() == PositionLetter.A || 
                position.getPositionLetter() == PositionLetter.H ||
                position.getPositionLetter() == PositionLetter.L;
    }

}
