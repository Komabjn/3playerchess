package com.komabjn.threeplayerchess.api.chessboard;

import com.komabjn.threeplayerchess.util.PositionUtil;

/**
 *
 * @author Komabjn
 */
public class Position {

    private int positionLetter;
    private int positionNumber;

    public Position(int positionLetter, int positionNumber) {
        this.positionLetter = positionLetter;
        this.positionNumber = positionNumber;
    }
    
    public Position(String positionLetter, String positionNumber) {
        this.positionLetter = PositionUtil.getPositionLetterFromString(positionLetter);
        this.positionNumber = PositionUtil.getPositionNumberFromString(positionNumber);
    }

    public int getPositionLetter() {
        return positionLetter;
    }

    public void setPositionLetter(int positionLetter) {
        this.positionLetter = positionLetter;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

}
