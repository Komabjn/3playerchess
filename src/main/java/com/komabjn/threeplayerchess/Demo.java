package com.komabjn.threeplayerchess;

import com.komabjn.threeplayerchess.api.ChessPiece;
import com.komabjn.threeplayerchess.api.ChessPieceType;
import com.komabjn.threeplayerchess.api.Player;
import com.komabjn.threeplayerchess.api.chessboard.ChessboardState;
import com.komabjn.threeplayerchess.api.chessboard.Position;
import com.komabjn.threeplayerchess.rendering.ThreePlayerChessboardRendererFrame;
import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightReason;
import com.komabjn.threeplayerchess.api.rendering.highlight.Highlight;
import com.komabjn.threeplayerchess.util.PositionUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author Komabjn
 */
public class Demo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Set<ChessPiece> figures = new HashSet<>();
            figures.add(new ChessFigureImpl(Player.PLAYER_1, new Position("E", "1"), ChessPieceType.QUEEN));
            figures.add(new ChessFigureImpl(Player.PLAYER_1, new Position("D", "1"), ChessPieceType.KING));
            figures.add(new ChessFigureImpl(Player.PLAYER_2, new Position("B", "2"), ChessPieceType.KNIGHT));
            figures.add(new ChessFigureImpl(Player.PLAYER_3, new Position("I", "12"), ChessPieceType.BISHOP));
            figures.add(new ChessFigureImpl(Player.PLAYER_3, new Position("E", "12"), ChessPieceType.ROOK));

            List<Highlight> highlights = new ArrayList<>();
            highlights.add(new Highlight(new Position("E", "2"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "3"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "4"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "9"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "10"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "11"), HighlightReason.POSSIBLE_MOVE));
            highlights.add(new Highlight(new Position("E", "12"), HighlightReason.POSSIBLE_CAPTURE));

            highlights.add(new Highlight(new Position("E", "12"), HighlightReason.OPONENT_PIECE_POSITION_CHANGE));
            highlights.add(new Highlight(new Position("F", "12"), HighlightReason.OPONENT_PIECE_POSITION_CHANGE));

            highlights.add(new Highlight(new Position("A", "4"), HighlightReason.OPONENT_PIECE_POSITION_CHANGE));
            highlights.add(new Highlight(new Position("B", "2"), HighlightReason.OPONENT_PIECE_POSITION_CHANGE));

            highlights.add(new Highlight(new Position("E", "1"), HighlightReason.SELECTED_PIECE));

            highlights.add(new Highlight(new Position("D", "1"), HighlightReason.CHECK));
//            highlights.add(new Highlight(new Position(PositionLetter.B, PositionNumber.NUMBER_2), FieldHighlightType.CHECK));
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_1.name());
                gameFrame.setLocation(0, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);

                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_1, highlights);
                gameFrame.getUserInputListenerSupport().registerListener((pos) -> {
                    System.out.println("User clicked on: " + PositionUtil.extractPositionLetter(pos) + PositionUtil.extractPositionNumber(pos));
                });
            }
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_2.name());
                gameFrame.setLocation(1500, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);

                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_2, highlights);
            }
            {
                ThreePlayerChessboardRendererFrame gameFrame = new ThreePlayerChessboardRendererFrame();
                gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                gameFrame.setTitle(Player.PLAYER_3.name());
                gameFrame.setLocation(3000, 0);
                gameFrame.pack();
                gameFrame.setVisible(true);

                gameFrame.getRenderer().render(new ChessboardState(figures, Player.PLAYER_1), Player.PLAYER_3, highlights);
            }
        }
        );
    }

    private static class ChessFigureImpl extends ChessPiece {

        public ChessFigureImpl(Player owner, Position position, ChessPieceType pieceType) {
            this.owner = owner;
            this.position = position;
            this.pieceType = pieceType;
        }

    }
}
