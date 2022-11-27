package com.komabjn.threeplayerchess.rendering;

import com.komabjn.threeplayerchess.api.ChessFigure;
import com.komabjn.threeplayerchess.api.Player;
import com.komabjn.threeplayerchess.api.chessboard.ChessboardState;
import com.komabjn.threeplayerchess.api.chessboard.Position;
import com.komabjn.threeplayerchess.api.chessboard.PositionLetter;
import com.komabjn.threeplayerchess.api.chessboard.PositionNumber;
import com.komabjn.threeplayerchess.api.rendering.ThreePlayerChessRenderer;
import com.komabjn.threeplayerchess.rendering.util.ChessboardPoints;
import com.komabjn.threeplayerchess.util.PositionsUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.komabjn.threeplayerchess.api.rendering.ChessboardColorModel;
import com.komabjn.threeplayerchess.api.rendering.highlight.Highlight;
import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightType;
import com.komabjn.threeplayerchess.api.rendering.highlight.HighlightTypeProvider;
import com.komabjn.threeplayerchess.util.ColorUtil;
import com.komabjn.threeplayerchess.util.HighlightUtil;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * To avoid concurrency problems, all public methods should internally implement
 * EDT dispatching. This way EDT will be the only thread to have access to all
 * fields in this class;
 *
 * @author Komabjn
 */
public class ThreePlayerChessboardRendererImpl extends JPanel implements ThreePlayerChessRenderer {

    private static final int CHESS_BOARD_MARGIN = 50;
    private static final int CHESS_BOARD_SIZE = 8;

    private final ChesspiecesGraphicsRepository graphicsRepository = ChesspiecesGraphicsRepository.getInstance();

    private ChessboardState chessboardState;
    private Player player;
    private List<Highlight> highlights;

    private ChessboardPoints chessBoardPoints;
    private ChessboardColorModel colorModel;
    private HighlightTypeProvider highlightTypeProvider;

    public ThreePlayerChessboardRendererImpl() {
        initComponents();
        // placeholder to avoid NPX in paintComponent untill render method is called for the first time
        player = Player.PLAYER_1;
        chessboardState = new ChessboardState(new HashSet<>(), player);
        colorModel = ColorUtil.getDefaultColorModel();
        highlights = new ArrayList<>();
        highlightTypeProvider = HighlightUtil.getDefaultHighlightTypeProvider();
    }

    @Override
    public void render(ChessboardState chessboardState, Player player, List<Highlight> highlights) {
        SwingUtilities.invokeLater(() -> {
            this.chessboardState = chessboardState;
            this.player = player;
            this.highlights = highlights;
            repaint();
        });
    }

    @Override
    public void setColorModel(ChessboardColorModel colorModel) {
        SwingUtilities.invokeLater(() -> {
            this.colorModel = colorModel;
            repaint();
        });
    }
    
    @Override
    public void setHighlightTypeProvider(HighlightTypeProvider highlightTypeProvider){
        SwingUtilities.invokeLater(() -> {
            this.highlightTypeProvider = highlightTypeProvider;
            repaint();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = this.getSize();
        drawPanelBorder(g2d, size, Color.CYAN);

        // chessboard context
        int width = 1500;
        double coef = Math.sqrt(3) / 2;
        BufferedImage image = new BufferedImage(width, (int) (width * coef), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dchessBoard = image.createGraphics();

        drawChessboard(g2dchessBoard, new Dimension(image.getWidth(), image.getHeight()));

        g2d.drawImage(image, null, 10, 10);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    private void drawPanelBorder(Graphics2D g2d, Dimension panelDimension, Color color) {
        Color previousColor = g2d.getColor();
        g2d.setColor(color);
        g2d.drawRect(2, 2, panelDimension.width - 4, panelDimension.height - 4);
        g2d.setColor(previousColor);
    }

    private void drawChessboard(Graphics2D g2d, Dimension panelDimension) {
        createChessBoardPoints(panelDimension);

        int pointCountLetters = 4 + (CHESS_BOARD_SIZE - 1);
        int pointCountNumbers = 2 + (CHESS_BOARD_SIZE / 2);

        List<Position> allPositions = new ArrayList<>(CHESS_BOARD_SIZE * CHESS_BOARD_SIZE);
        for (PositionLetter letter : PositionLetter.values()) {
            for (PositionNumber number : PositionNumber.values()) {
                allPositions.add(new Position(letter, number));
            }
        }
        List<Position> allValidPositions = allPositions.stream().filter((pos) -> PositionsUtil.isPositionValid(pos)).collect(Collectors.toList());

        // fill alternating chessboard fields
        for (Position p : allValidPositions) {
            Point[] ps = getFieldBoundaries(p, chessBoardPoints, TranslateMode.CHESS_FIELD);
            if (ColorUtil.isAlternateColorField(p)) {
                g2d.setColor(colorModel.getChessboardColorAlternate());
            } else {
                g2d.setColor(colorModel.getChessboardColorMain());
            }
            fillPolygon(g2d, ps);
        }

        // fill highlightedFields
        Map<HighlightType, List<Highlight>> highligtsByType = new HashMap<>();
        Arrays.stream(HighlightType.values()).forEach((h) -> {
            highligtsByType.put(h, new ArrayList<>());
        });
        highlights.forEach((h) -> {
            highligtsByType.get(highlightTypeProvider.getHighlightType(h.getHighlightReason())).add(h);
        });
        highligtsByType.get(HighlightType.DOT).addAll(highligtsByType.get(HighlightType.DOT_AND_BORDER));
        highligtsByType.get(HighlightType.BORDER).addAll(highligtsByType.get(HighlightType.DOT_AND_BORDER));
        for (Highlight highlight : highligtsByType.get(HighlightType.FIELD)) {
            if (ColorUtil.isAlternateColorField(highlight.getHighlightedPosition())) {
                g2d.setColor(ColorUtil.blend(colorModel.getHighlightColor(highlight.getHighlightReason()), colorModel.getChessboardColorAlternate()));
            } else {
                g2d.setColor(colorModel.getHighlightColor(highlight.getHighlightReason()));
            }
            Point[] ps = getFieldBoundaries(highlight.getHighlightedPosition(), chessBoardPoints, TranslateMode.CHESS_FIELD);
            fillPolygon(g2d, ps);
        }
        
        // border highlights
        Stroke originalStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(10));
        for (Highlight highlight : highligtsByType.get(HighlightType.BORDER)) {
            if (ColorUtil.isAlternateColorField(highlight.getHighlightedPosition())) {
                g2d.setColor(ColorUtil.blend(colorModel.getHighlightColor(highlight.getHighlightReason()), colorModel.getChessboardColorAlternate()));
            } else {
                g2d.setColor(colorModel.getHighlightColor(highlight.getHighlightReason()));
            }
            Point[] ps = getFieldBoundaries(highlight.getHighlightedPosition(), chessBoardPoints, TranslateMode.CHESS_FIELD);
            borderPolygon(g2d, 0, ps);
        }
        g2d.setStroke(originalStroke);

        //draw fields contours
        g2d.setColor(colorModel.getContourColor());
        boolean simplifiedLines = false; // debug option, draw lines across the board rather than between each point 
        if (!simplifiedLines) {
            for (int i = 0; i < pointCountLetters; i++) {
                for (int j = 0; j < pointCountNumbers - 1; j++) {
                    drawLine(g2d, chessBoardPoints.getBottomPoints()[i][j], chessBoardPoints.getBottomPoints()[i][j + 1]);
                    drawLine(g2d, chessBoardPoints.getRightPoints()[i][j], chessBoardPoints.getRightPoints()[i][j + 1]);
                    drawLine(g2d, chessBoardPoints.getLeftPoints()[i][j], chessBoardPoints.getLeftPoints()[i][j + 1]);
                }
            }
            for (int i = 0; i < pointCountNumbers; i++) {
                for (int j = 0; j < pointCountLetters - 1; j++) {
                    drawLine(g2d, chessBoardPoints.getBottomPoints()[j][i], chessBoardPoints.getBottomPoints()[j + 1][i]);
                    drawLine(g2d, chessBoardPoints.getRightPoints()[j][i], chessBoardPoints.getRightPoints()[j + 1][i]);
                    drawLine(g2d, chessBoardPoints.getLeftPoints()[j][i], chessBoardPoints.getLeftPoints()[j + 1][i]);
                }
            }
        } else {
            for (int i = 0; i < chessBoardPoints.getBottomPoints().length; i++) {
                drawLine(g2d, chessBoardPoints.getBottomPoints()[i][pointCountNumbers - 1], chessBoardPoints.getBottomPoints()[i][1]);
                drawLine(g2d, chessBoardPoints.getBottomPoints()[i][1], chessBoardPoints.getBottomPoints()[i][0]);

                drawLine(g2d, chessBoardPoints.getRightPoints()[i][pointCountNumbers - 1], chessBoardPoints.getRightPoints()[i][1]);
                drawLine(g2d, chessBoardPoints.getRightPoints()[i][1], chessBoardPoints.getRightPoints()[i][0]);

                drawLine(g2d, chessBoardPoints.getLeftPoints()[i][pointCountNumbers - 1], chessBoardPoints.getLeftPoints()[i][1]);
                drawLine(g2d, chessBoardPoints.getLeftPoints()[i][1], chessBoardPoints.getLeftPoints()[i][0]);
            }
            int middlePointIndex = pointCountLetters / 2;
            for (int i = 0; i < pointCountNumbers; i++) {
                drawLine(g2d, chessBoardPoints.getBottomPoints()[0][i], chessBoardPoints.getBottomPoints()[1][i]);
                drawLine(g2d, chessBoardPoints.getBottomPoints()[1][i], chessBoardPoints.getBottomPoints()[middlePointIndex][i]);
                drawLine(g2d, chessBoardPoints.getBottomPoints()[middlePointIndex][i], chessBoardPoints.getBottomPoints()[pointCountLetters - 2][i]);
                drawLine(g2d, chessBoardPoints.getBottomPoints()[pointCountLetters - 2][i], chessBoardPoints.getBottomPoints()[pointCountLetters - 1][i]);

                drawLine(g2d, chessBoardPoints.getRightPoints()[0][i], chessBoardPoints.getRightPoints()[1][i]);
                drawLine(g2d, chessBoardPoints.getRightPoints()[1][i], chessBoardPoints.getRightPoints()[middlePointIndex][i]);
                drawLine(g2d, chessBoardPoints.getRightPoints()[middlePointIndex][i], chessBoardPoints.getRightPoints()[pointCountLetters - 2][i]);
                drawLine(g2d, chessBoardPoints.getRightPoints()[pointCountLetters - 2][i], chessBoardPoints.getRightPoints()[pointCountLetters - 1][i]);

                drawLine(g2d, chessBoardPoints.getLeftPoints()[0][i], chessBoardPoints.getLeftPoints()[1][i]);
                drawLine(g2d, chessBoardPoints.getLeftPoints()[1][i], chessBoardPoints.getLeftPoints()[middlePointIndex][i]);
                drawLine(g2d, chessBoardPoints.getLeftPoints()[middlePointIndex][i], chessBoardPoints.getLeftPoints()[pointCountLetters - 2][i]);
                drawLine(g2d, chessBoardPoints.getLeftPoints()[pointCountLetters - 2][i], chessBoardPoints.getLeftPoints()[pointCountLetters - 1][i]);
            }
        }

//        { //debug
//            g2d.setColor(Color.RED);
//            for (PositionLetter letter : PositionLetter.values()) {
//                for (PositionNumber number : PositionNumber.values()) {
//                    Point trans = translatePosition(new Position(letter, number), player, chessBoardPoints, TranslateMode.CHESS_FIELD);
//                    if (trans != null) {
//                        drawPoint(g2d, trans, 4);
//                        String text = "" + letter + " " + (number.ordinal() + 1);
//                        char[] arr = text.toCharArray();
//                        g2d.drawChars(arr, 0, arr.length, trans.x, trans.y);
//                    }
//                }
//            }
//        }
        // border letters and numbers
        g2d.setColor(colorModel.getLabelColor());
        int fontSize = 20;
        Font font = new Font("TimesRoman", Font.PLAIN, fontSize);
        g2d.setFont(font);

        allValidPositions.stream().filter((pos) -> PositionsUtil.isPositionAlongLetterBorder(pos)).forEach((pos) -> {
            Point p = translatePosition(pos, chessBoardPoints, TranslateMode.LETTER_LABEL);
            String text = "" + pos.getPositionLetter();
            char[] arr = text.toCharArray();
            int textWidth = g2d.getFontMetrics().charsWidth(arr, 0, arr.length);
            g2d.drawChars(arr, 0, arr.length, p.x - textWidth / 2, p.y + fontSize / 2);
        });
        allValidPositions.stream().filter((pos) -> PositionsUtil.isPositionAlongNumberBorder(pos)).forEach((pos) -> {
            Point p = translatePosition(pos, chessBoardPoints, TranslateMode.NUMBER_LABEL);
            String text = "" + (pos.getPositionNumber().ordinal() + 1);
            char[] arr = text.toCharArray();
            int textWidth = g2d.getFontMetrics().charsWidth(arr, 0, arr.length);
            g2d.drawChars(arr, 0, arr.length, p.x - textWidth / 2, p.y + fontSize / 2);
        });

        // figures
        if (chessboardState != null) {
            for (ChessFigure figure : chessboardState.getFigures()) {
                BufferedImage img = graphicsRepository.getChessGraphics(figure.getPieceType(), figure.getOwner());
                Point p = translatePosition(figure.getPosition(), chessBoardPoints, TranslateMode.CHESS_FIELD);
                g2d.drawImage(img, null, p.x - (img.getWidth() / 2), p.y - (img.getHeight() / 2));
            }
        }
        
        // highlight dot
        for (Highlight highlight : highligtsByType.get(HighlightType.DOT)) {
            if (ColorUtil.isAlternateColorField(highlight.getHighlightedPosition())) {
                g2d.setColor(ColorUtil.transparentize(ColorUtil.blend(colorModel.getHighlightColor(highlight.getHighlightReason()), colorModel.getChessboardColorAlternate())));
            } else {
                g2d.setColor(ColorUtil.transparentize(colorModel.getHighlightColor(highlight.getHighlightReason())));
            }
            Point ps = translatePosition(highlight.getHighlightedPosition(), chessBoardPoints, TranslateMode.CHESS_FIELD);
            drawPoint(g2d, ps, 20);
        }
    }

    private void createChessBoardPoints(Dimension panelDimension) {
        Point chessBoardMiddlePoint = new Point(panelDimension.width / 2, panelDimension.height / 2);

        int halfWallLength = (int) (panelDimension.height / 2 / Math.sqrt(3));

        //top points
        Point topMiddlePoint = new Point(panelDimension.width / 2, 0);
        Point topLeftPoint = new Point(topMiddlePoint.x - halfWallLength, 0);
        Point topRightPoint = new Point(topMiddlePoint.x + halfWallLength, 0);

        //bottom points
        Point bottomMiddlePoint = new Point(panelDimension.width / 2, panelDimension.height - 1);
        Point bottomLeftPoint = new Point(bottomMiddlePoint.x - halfWallLength, panelDimension.height - 1);
        Point bottomRightPoint = new Point(bottomMiddlePoint.x + halfWallLength, panelDimension.height - 1);

        int pointCountLetters = 4 + (CHESS_BOARD_SIZE - 1);
        int pointCountNumbers = 2 + (CHESS_BOARD_SIZE / 2);

        //left points
        Point leftPoint = new Point(0, panelDimension.height / 2);
        Point leftLowerPoint = calculateMiddlePoint(leftPoint, bottomLeftPoint);

        //right points
        Point rightPoint = new Point(panelDimension.width - 1, panelDimension.height / 2);
        Point rightLowerPoint = calculateMiddlePoint(rightPoint, bottomRightPoint);

        Point[][] bottomPoints = createPointsArray(
                pointCountLetters,
                pointCountNumbers,
                chessBoardMiddlePoint,
                bottomLeftPoint,
                leftLowerPoint,
                bottomRightPoint,
                rightLowerPoint
        );

        Point[][] rightPoints = createPointsArray(
                pointCountLetters,
                pointCountNumbers,
                chessBoardMiddlePoint,
                rightPoint,
                rightLowerPoint,
                topRightPoint,
                topMiddlePoint
        );

        Point[][] leftPoints = createPointsArray(
                pointCountLetters,
                pointCountNumbers,
                chessBoardMiddlePoint,
                topLeftPoint,
                topMiddlePoint,
                leftPoint,
                leftLowerPoint
        );

        chessBoardPoints = new ChessboardPoints(player, bottomPoints, rightPoints, leftPoints);
    }

    private Point[][] createPointsArray(int pointCountLetters, int pointCountNumbers, Point chessBoardMiddlePoint, Point leftPoint, Point leftUpperPoint, Point rightPoint, Point rightUpperPoint) {
        Point[][] points = new Point[pointCountLetters][pointCountNumbers];

        int middlePointIndex = pointCountLetters / 2;

        // start with furthest points from left to right  
        // fill already provided points
        points[0][pointCountNumbers - 1] = leftUpperPoint;
        points[middlePointIndex][pointCountNumbers - 1] = chessBoardMiddlePoint;
        points[pointCountLetters - 1][pointCountNumbers - 1] = rightUpperPoint;
        {
            Point upperLeftAfterMargin = calculatePoint(leftUpperPoint, chessBoardMiddlePoint, CHESS_BOARD_MARGIN);
            points[1][pointCountNumbers - 1] = upperLeftAfterMargin;
            double distanceToMiddle = calculateDistance(upperLeftAfterMargin, chessBoardMiddlePoint);
            int distancePerRow = (int) (distanceToMiddle / (CHESS_BOARD_SIZE / 2));
            for (int i = 2; i < middlePointIndex; i++) {
                points[i][pointCountNumbers - 1] = calculatePoint(points[i - 1][pointCountNumbers - 1], chessBoardMiddlePoint, distancePerRow);
            }
            Point upperRightAfterMargin = calculatePoint(rightUpperPoint, chessBoardMiddlePoint, CHESS_BOARD_MARGIN);
            points[pointCountLetters - 2][pointCountNumbers - 1] = upperRightAfterMargin;
            for (int i = pointCountLetters - 3; i > middlePointIndex; i--) {
                points[i][pointCountNumbers - 1] = calculatePoint(points[i + 1][pointCountNumbers - 1], chessBoardMiddlePoint, distancePerRow);
            }
        }

        points[0][0] = leftPoint;
        points[pointCountLetters - 1][0] = rightPoint;
        {
            Point middlePoint = calculateMiddlePoint(leftPoint, rightPoint);
            Point leftAfterMargin = calculatePoint(leftPoint, middlePoint, CHESS_BOARD_MARGIN);
            points[1][0] = leftAfterMargin;
            double distanceToMiddle = calculateDistance(leftAfterMargin, middlePoint);
            int distancePerRow = (int) (distanceToMiddle / (CHESS_BOARD_SIZE / 2));
            for (int i = 2; i < middlePointIndex; i++) {
                points[i][0] = calculatePoint(points[i - 1][0], middlePoint, distancePerRow);
            }
            points[middlePointIndex][0] = middlePoint;
            Point rightAfterMargin = calculatePoint(rightPoint, middlePoint, CHESS_BOARD_MARGIN);
            points[pointCountLetters - 2][0] = rightAfterMargin;
            for (int i = pointCountLetters - 3; i > middlePointIndex; i--) {
                points[i][0] = calculatePoint(points[i + 1][0], middlePoint, distancePerRow);
            }
        }

        double bottomFactor = 2 / Math.sqrt(3);

        points[0][1] = calculatePoint(points[0][0], points[0][pointCountNumbers - 1], (int) (CHESS_BOARD_MARGIN * bottomFactor));
        points[pointCountLetters - 1][1] = calculatePoint(points[pointCountLetters - 1][0], points[pointCountLetters - 1][pointCountNumbers - 1], (int) (CHESS_BOARD_MARGIN * bottomFactor));
        {
            Point middlePoint = calculateMiddlePoint(points[0][1], points[pointCountLetters - 1][1]);
            Point leftAfterMargin = calculatePoint(points[0][1], middlePoint, (int) (CHESS_BOARD_MARGIN * bottomFactor));
            points[1][1] = leftAfterMargin;
            double distanceToMiddle = calculateDistance(leftAfterMargin, middlePoint);
            int distancePerRow = (int) (distanceToMiddle / (CHESS_BOARD_SIZE / 2));
            for (int i = 2; i < middlePointIndex; i++) {
                points[i][1] = calculatePoint(points[i - 1][1], middlePoint, distancePerRow);
            }
            points[middlePointIndex][1] = middlePoint;
            Point rightAfterMargin = calculatePoint(points[pointCountLetters - 1][1], middlePoint, (int) (CHESS_BOARD_MARGIN * bottomFactor));
            points[pointCountLetters - 2][1] = rightAfterMargin;
            for (int i = pointCountLetters - 3; i > middlePointIndex; i--) {
                points[i][1] = calculatePoint(points[i + 1][1], middlePoint, distancePerRow);
            }
        }

        // now all other, from left to right from closests to furthest
        for (int i = 0; i < pointCountLetters; i++) {
            double distance = calculateDistance(points[i][1], points[i][pointCountNumbers - 1]);
            int distancePerRow = (int) (distance / (CHESS_BOARD_SIZE / 2));
            for (int j = 2; j < pointCountNumbers - 1; j++) {
                points[i][j] = calculatePoint(points[i][j - 1], points[i][pointCountNumbers - 1], distancePerRow);
            }
        }
        return points;
    }

    private void drawLine(Graphics2D g2d, Point a, Point b) {
        g2d.drawLine(a.x, a.y, b.x, b.y);
    }

    private void drawPoint(Graphics2D g2d, Point a, int size) {
        g2d.fillOval(a.x - size / 2, a.y - size / 2, size, size);
    }

    private void fillPolygon(Graphics2D g2d, Point... points) {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
        g2d.fillPolygon(xPoints, yPoints, points.length);
    }
    
    private void borderPolygon(Graphics2D g2d, int borderWidth, Point... points) {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
        g2d.drawPolygon(xPoints, yPoints, points.length);
    }

    private Point calculateMiddlePoint(Point a, Point b) {
        return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
    }

    /**
     * Calculate point laying on line between a and b with distance to a
     *
     * @param a
     * @param b
     * @param distanceFromA
     * @return
     */
    private Point calculatePoint(Point a, Point b, int distanceFromA) {
        int xDelta = b.x - a.x;
        int yDelta = b.y - a.y;
        double pointDistance = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        double coef = 1d * distanceFromA / pointDistance;
        int newX = a.x + (int) (xDelta * coef);
        int newY = a.y + (int) (yDelta * coef);
        return new Point(newX, newY);
    }

    private double calculateDistance(Point a, Point b) {
        int xDelta = b.x - a.x;
        int yDelta = b.y - a.y;
        double pointDistance = Math.sqrt(Math.pow(xDelta, 2) + Math.pow(yDelta, 2));
        return pointDistance;
    }

    private Point translatePosition(Position position, ChessboardPoints chessBoardPoints, TranslateMode mode) {
        return calculateMiddleOfPolygon(getFieldBoundaries(position, chessBoardPoints, mode));
    }

    private Point[] getFieldBoundaries(Position position, ChessboardPoints chessBoardPoints, TranslateMode mode) {
        List<Point> p = new ArrayList<>(4);

        Point[][] pointsBottom = chessBoardPoints.getPlayerPoints();
        Point[][] pointsRight = chessBoardPoints.getRightPlayerPoints();
        Point[][] pointsLeft = chessBoardPoints.getLeftPlayerPoints();

        int letterOrdinal = position.getPositionLetter().ordinal();
        int numberOrdinal = position.getPositionNumber().ordinal();
        if (letterOrdinal < (CHESS_BOARD_SIZE / 2)) {
            if (numberOrdinal < CHESS_BOARD_SIZE / 2) {
                // bottom points left side
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = -1;
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = -1;
                    }
                }
                p.add(pointsBottom[1 + letterOrdinal][1 + numberOrdinal]);
                p.add(pointsBottom[2 + letterOrdinal][1 + numberOrdinal]);
                p.add(pointsBottom[2 + letterOrdinal][2 + numberOrdinal]);
                p.add(pointsBottom[1 + letterOrdinal][2 + numberOrdinal]);
            } else if (numberOrdinal < CHESS_BOARD_SIZE) {
                // left points right side reversed numbers reversed letters
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = CHESS_BOARD_SIZE;
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = -1;
                    }
                }
                p.add(pointsLeft[CHESS_BOARD_SIZE + 1 - letterOrdinal][1 + (CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[CHESS_BOARD_SIZE - letterOrdinal][1 + (CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[CHESS_BOARD_SIZE - letterOrdinal][(CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[CHESS_BOARD_SIZE + 1 - letterOrdinal][(CHESS_BOARD_SIZE) - numberOrdinal]);
            }
        } else if (letterOrdinal >= (CHESS_BOARD_SIZE / 2) && letterOrdinal < CHESS_BOARD_SIZE) {
            if (numberOrdinal < CHESS_BOARD_SIZE / 2) {
                // bottom points right side
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = -1;
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = CHESS_BOARD_SIZE;
                    }
                }
                p.add(pointsBottom[1 + letterOrdinal][1 + numberOrdinal]);
                p.add(pointsBottom[2 + letterOrdinal][1 + numberOrdinal]);
                p.add(pointsBottom[2 + letterOrdinal][2 + numberOrdinal]);
                p.add(pointsBottom[1 + letterOrdinal][2 + numberOrdinal]);
            } else if (numberOrdinal >= CHESS_BOARD_SIZE) {
                // right points left side reversed numbers reversed letters
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = (int) (CHESS_BOARD_SIZE * 1.5);
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = CHESS_BOARD_SIZE;
                    }
                }
                p.add(pointsRight[CHESS_BOARD_SIZE + 1 - letterOrdinal][1 + (int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[CHESS_BOARD_SIZE - letterOrdinal][1 + (int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[CHESS_BOARD_SIZE - letterOrdinal][(int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[CHESS_BOARD_SIZE + 1 - letterOrdinal][(int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
            }
        } else {
            if (numberOrdinal >= CHESS_BOARD_SIZE / 2 && numberOrdinal < CHESS_BOARD_SIZE) {
                // left points left side reversed numbers reversed letters
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = CHESS_BOARD_SIZE;
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = (int) (CHESS_BOARD_SIZE * 1.5);
                    }
                }
                p.add(pointsLeft[(int) (CHESS_BOARD_SIZE * 1.5) + 1 - letterOrdinal][1 + (CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[(int) (CHESS_BOARD_SIZE * 1.5) - letterOrdinal][1 + (CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[(int) (CHESS_BOARD_SIZE * 1.5) - letterOrdinal][(CHESS_BOARD_SIZE) - numberOrdinal]);
                p.add(pointsLeft[(int) (CHESS_BOARD_SIZE * 1.5) + 1 - letterOrdinal][(CHESS_BOARD_SIZE) - numberOrdinal]);
            } else if (numberOrdinal >= CHESS_BOARD_SIZE) {
                //right points right side reversed numbers
                switch (mode) {
                    case LETTER_LABEL: {
                        numberOrdinal = (int) (CHESS_BOARD_SIZE * 1.5);
                        break;
                    }
                    case NUMBER_LABEL: {
                        letterOrdinal = (int) (CHESS_BOARD_SIZE * 1.5);
                    }
                }
                p.add(pointsRight[1 + letterOrdinal - CHESS_BOARD_SIZE / 2][1 + (int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[2 + letterOrdinal - CHESS_BOARD_SIZE / 2][1 + (int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[2 + letterOrdinal - CHESS_BOARD_SIZE / 2][(int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
                p.add(pointsRight[1 + letterOrdinal - CHESS_BOARD_SIZE / 2][(int) (CHESS_BOARD_SIZE * 1.5) - numberOrdinal]);
            }
        }

        return p.toArray(new Point[4]);
    }

    private Point calculateMiddleOfPolygon(Point... points) {
        assert points.length == 4;
        Point m1 = calculateMiddlePoint(points[0], points[2]);
        Point m2 = calculateMiddlePoint(points[1], points[3]);
        return calculateMiddlePoint(m1, m2);
    }

    private enum TranslateMode {
        CHESS_FIELD, LETTER_LABEL, NUMBER_LABEL;
    }

}
