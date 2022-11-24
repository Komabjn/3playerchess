package com.komabjn.threeplayerchess.rendering.util;

import com.komabjn.threeplayerchess.api.Player;
import java.awt.Point;

/**
 *
 * @author Komabjn
 */
public class ChessboardPoints {

    private final Player player;

    private final Point[][] bottomPoints;
    private final Point[][] rightPoints;
    private final Point[][] leftPoints;

    public ChessboardPoints(Player player, Point[][] bottomPoints, Point[][] rightPoints, Point[][] leftPoints) {
        this.player = player;
        this.bottomPoints = bottomPoints;
        this.rightPoints = rightPoints;
        this.leftPoints = leftPoints;
    }

    public Point[][] getBottomPoints() {
        return bottomPoints;
    }

    public Point[][] getRightPoints() {
        return rightPoints;
    }

    public Point[][] getLeftPoints() {
        return leftPoints;
    }

    // following methods have reversed conditions for player 2 and 3, i have no clue why, but it is required for it to work properly
    public Point[][] getPlayerPoints() {
        switch (player) {
            case PLAYER_2:
                return rightPoints;
            case PLAYER_3:
                return leftPoints;
            case PLAYER_1:
            default:
                return bottomPoints;
        }
    }

    public Point[][] getLeftPlayerPoints() {
        switch (player) {
            case PLAYER_2:
                return bottomPoints;
            case PLAYER_3:
                return rightPoints;
            case PLAYER_1:
            default:
                return leftPoints;
        }
    }

    public Point[][] getRightPlayerPoints() {
        switch (player) {
            case PLAYER_2:
                return leftPoints;
            case PLAYER_3:
                return bottomPoints;
            case PLAYER_1:
            default:
                return rightPoints;
        }
    }

}
