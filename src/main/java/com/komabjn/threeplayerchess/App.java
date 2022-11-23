package com.komabjn.threeplayerchess;

import com.komabjn.threeplayerchess.api.ChessFigure;
import com.komabjn.threeplayerchess.api.ChessPieceType;
import com.komabjn.threeplayerchess.api.Player;
import com.komabjn.threeplayerchess.api.chessboard.ChessboardState;
import com.komabjn.threeplayerchess.api.chessboard.Position;
import com.komabjn.threeplayerchess.api.chessboard.PositionLetter;
import com.komabjn.threeplayerchess.api.chessboard.PositionNumber;
import com.komabjn.threeplayerchess.rendering.ThreePlayerChessboardRendererFrame;
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Komabjn
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SwingUtilities.invokeLater(() -> {
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_1.name());
                gameFrame.setLocation(0, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);
                Set<ChessFigure> figures = new HashSet<>();
                figures.add(new ChessFigureImpl(Player.PLAYER_1, new Position(PositionLetter.E, PositionNumber.NUMBER_1), ChessPieceType.QUEEN));
                figures.add(new ChessFigureImpl(Player.PLAYER_2, new Position(PositionLetter.I, PositionNumber.NUMBER_8), ChessPieceType.KNIGHT));
                figures.add(new ChessFigureImpl(Player.PLAYER_3, new Position(PositionLetter.I, PositionNumber.NUMBER_12), ChessPieceType.BISHOP));
                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_1);
            }
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_2.name());
                gameFrame.setLocation(1500, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);
                Set<ChessFigure> figures = new HashSet<>();
                figures.add(new ChessFigureImpl(Player.PLAYER_1, new Position(PositionLetter.E, PositionNumber.NUMBER_1), ChessPieceType.QUEEN));
                figures.add(new ChessFigureImpl(Player.PLAYER_2, new Position(PositionLetter.I, PositionNumber.NUMBER_8), ChessPieceType.KNIGHT));
                figures.add(new ChessFigureImpl(Player.PLAYER_3, new Position(PositionLetter.I, PositionNumber.NUMBER_12), ChessPieceType.BISHOP));
                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_2);
            }
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_3.name());
                gameFrame.setLocation(3000, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);
                Set<ChessFigure> figures = new HashSet<>();
                figures.add(new ChessFigureImpl(Player.PLAYER_1, new Position(PositionLetter.E, PositionNumber.NUMBER_1), ChessPieceType.QUEEN));
                figures.add(new ChessFigureImpl(Player.PLAYER_2, new Position(PositionLetter.I, PositionNumber.NUMBER_8), ChessPieceType.KNIGHT));
                figures.add(new ChessFigureImpl(Player.PLAYER_3, new Position(PositionLetter.I, PositionNumber.NUMBER_12), ChessPieceType.BISHOP));
                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_3);
            }
        });
    }

    private static class ChessFigureImpl extends ChessFigure {

        public ChessFigureImpl(Player owner, Position position, ChessPieceType pieceType) {
            this.owner = owner;
            this.position = position;
            this.pieceType = pieceType;
        }

    }
}
