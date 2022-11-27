package com.komabjn.threeplayerchess.api.input;

import com.komabjn.threeplayerchess.api.chessboard.Position;

/**
 *
 * @author Komabjn
 */
public interface UserInputListener {

    public void positionSelected(Position position);
    
}
