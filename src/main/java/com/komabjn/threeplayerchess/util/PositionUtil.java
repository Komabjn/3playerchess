package com.komabjn.threeplayerchess.util;

import static com.komabjn.threeplayerchess.api.GameConsts.CHESS_BOARD_SIZE;
import com.komabjn.threeplayerchess.api.chessboard.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Komabjn
 */
public class PositionUtil {

    /**
     * Check whether given position is valid, meaning there is a corresponding
     * field on the chessboard.
     *
     * @param position
     * @return
     */
    public static boolean isPositionValid(Position position) {
        if (position.getPositionLetter() < 4) {
            return position.getPositionNumber() < 8;
        } else if (position.getPositionLetter() < 8) {
            return position.getPositionNumber() < 4 || position.getPositionNumber() >= 8;
        } else {
            return position.getPositionNumber() >= 4;
        }
    }

    public static boolean isPositionAlongLetterBorder(Position position) {
        return position.getPositionNumber() == getPositionNumberFromString("1")
                || position.getPositionNumber() == getPositionNumberFromString("8")
                || position.getPositionNumber() == getPositionNumberFromString("12");
    }

    public static boolean isPositionAlongNumberBorder(Position position) {
        return position.getPositionLetter() == getPositionLetterFromString("A")
                || position.getPositionLetter() == getPositionLetterFromString("H")
                || position.getPositionLetter() == getPositionLetterFromString("L");
    }

    public static List<Position> getAllValidPositionsOnChessboard() {
        List<Position> allPositions = new ArrayList<>(CHESS_BOARD_SIZE * CHESS_BOARD_SIZE);
        for (int letter = 0; letter < (int) (CHESS_BOARD_SIZE * 1.5); letter++) {
            for (int number = 0; number < (int) (CHESS_BOARD_SIZE * 1.5); number++) {
                allPositions.add(new Position(letter, number));
            }
        }
        return allPositions.stream().filter((pos) -> PositionUtil.isPositionValid(pos)).collect(Collectors.toList());
    }

    public static int getPositionNumberFromString(String number) {
        return Integer.parseInt(number) - 1;
    }

    public static int getPositionLetterFromString(String letter) {
        return letter.toUpperCase().charAt(0) - 'A';
    }

    public static String extractPositionLetter(Position position) {
        return "" + (char) (position.getPositionLetter() + 'A');
    }

    public static String extractPositionNumber(Position position) {
        return "" + (position.getPositionNumber() + 1);
    }

}
