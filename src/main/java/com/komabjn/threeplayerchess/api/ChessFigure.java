package com.komabjn.threeplayerchess.api;

import com.komabjn.threeplayerchess.api.chessboard.Position;

/**
 *
 * @author Komabjn
 */
public abstract class ChessFigure {

    private Player owner;
    private Position position;
    private ChessPieceType pieceType;

    public Player getOwner() {
        return owner;
    }

    public Position getPosition() {
        return position;
    }

    public ChessPieceType getPieceType() {
        return pieceType;
    }

}
