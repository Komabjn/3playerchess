package com.komabjn.threeplayerchess.api.chessboard;

/**
 *
 * @author Komabjn
 */
public class Position {
    
    private PositionLetter positionLetter;
    private PositionNumber positionNumber;

    public PositionLetter getPositionLetter() {
        return positionLetter;
    }

    public void setPositionLetter(PositionLetter positionLetter) {
        this.positionLetter = positionLetter;
    }

    public PositionNumber getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(PositionNumber positionNumber) {
        this.positionNumber = positionNumber;
    }
    
}
