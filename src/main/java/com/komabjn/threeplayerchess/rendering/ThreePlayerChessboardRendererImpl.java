package com.komabjn.threeplayerchess.rendering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Komabjn
 */
public class ThreePlayerChessboardRendererImpl extends javax.swing.JPanel {

    private static final int CHESS_BOARD_MARGIN = 50;

    private static final int CHESS_BOARD_SIZE = 8;

    public ThreePlayerChessboardRendererImpl() {
        initComponents();
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

        int width = 1500;
        double coef = Math.sqrt(3) / 2;
        BufferedImage image = new BufferedImage(width, (int) (width * coef), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dchessBoard = image.createGraphics();

        drawChessboard(g2dchessBoard, new Dimension(image.getWidth(), image.getHeight()), Color.BLACK);

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

    private void drawChessboard(Graphics2D g2d, Dimension panelDimension, Color color) {
        Color previousColor = g2d.getColor();
        g2d.setColor(color);

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

        g2d.setColor(Color.DARK_GRAY);
        
        boolean simplifiedLines = false;
        if (!simplifiedLines) {
            for (int i = 0; i < pointCountLetters; i++) {
                for (int j = 0; j < pointCountNumbers - 1; j++) {
                    drawLine(g2d, bottomPoints[i][j], bottomPoints[i][j + 1]);
                    drawLine(g2d, rightPoints[i][j], rightPoints[i][j + 1]);
                    drawLine(g2d, leftPoints[i][j], leftPoints[i][j + 1]);
                }
            }
            for (int i = 0; i < pointCountNumbers; i++) {
                for (int j = 0; j < pointCountLetters - 1; j++) {
                    drawLine(g2d, bottomPoints[j][i], bottomPoints[j + 1][i]);
                    drawLine(g2d, rightPoints[j][i], rightPoints[j + 1][i]);
                    drawLine(g2d, leftPoints[j][i], leftPoints[j + 1][i]);
                }
            }
        } else {
            for (int i = 0; i < bottomPoints.length; i++) {
                drawLine(g2d, bottomPoints[i][pointCountNumbers - 1], bottomPoints[i][1]);
                drawLine(g2d, bottomPoints[i][1], bottomPoints[i][0]);
                
                drawLine(g2d, rightPoints[i][pointCountNumbers - 1], rightPoints[i][1]);
                drawLine(g2d, rightPoints[i][1], rightPoints[i][0]);

                drawLine(g2d, leftPoints[i][pointCountNumbers - 1], leftPoints[i][1]);
                drawLine(g2d, leftPoints[i][1], leftPoints[i][0]);
            }
            int middlePointIndex = pointCountLetters / 2;
            for (int i = 0; i < pointCountNumbers; i++) {
                drawLine(g2d, bottomPoints[0][i], bottomPoints[1][i]);
                drawLine(g2d, bottomPoints[1][i], bottomPoints[middlePointIndex][i]);
                drawLine(g2d, bottomPoints[middlePointIndex][i], bottomPoints[pointCountLetters - 2][i]);
                drawLine(g2d, bottomPoints[pointCountLetters - 2][i], bottomPoints[pointCountLetters - 1][i]);

                drawLine(g2d, rightPoints[0][i], rightPoints[1][i]);
                drawLine(g2d, rightPoints[1][i], rightPoints[middlePointIndex][i]);
                drawLine(g2d, rightPoints[middlePointIndex][i], rightPoints[pointCountLetters - 2][i]);
                drawLine(g2d, rightPoints[pointCountLetters - 2][i], rightPoints[pointCountLetters - 1][i]);

                drawLine(g2d, leftPoints[0][i], leftPoints[1][i]);
                drawLine(g2d, leftPoints[1][i], leftPoints[middlePointIndex][i]);
                drawLine(g2d, leftPoints[middlePointIndex][i], leftPoints[pointCountLetters - 2][i]);
                drawLine(g2d, leftPoints[pointCountLetters - 2][i], leftPoints[pointCountLetters - 1][i]);
            }
        }

        for (int i = 1; i < pointCountLetters - 2; i += 2) {
            for (int j = 1; j < pointCountNumbers - 1; j += 2) {
                fillPolygon(g2d, bottomPoints[i][j], bottomPoints[i][j + 1], bottomPoints[i + 1][j + 1], bottomPoints[i + 1][j]);
                fillPolygon(g2d, rightPoints[i][j], rightPoints[i][j + 1], rightPoints[i + 1][j + 1], rightPoints[i + 1][j]);
                fillPolygon(g2d, leftPoints[i][j], leftPoints[i][j + 1], leftPoints[i + 1][j + 1], leftPoints[i + 1][j]);
            }
        }
        for (int i = 2; i < pointCountLetters - 2; i += 2) {
            for (int j = 2; j < pointCountNumbers - 1; j += 2) {
                fillPolygon(g2d, bottomPoints[i][j], bottomPoints[i][j + 1], bottomPoints[i + 1][j + 1], bottomPoints[i + 1][j]);
                fillPolygon(g2d, rightPoints[i][j], rightPoints[i][j + 1], rightPoints[i + 1][j + 1], rightPoints[i + 1][j]);
                fillPolygon(g2d, leftPoints[i][j], leftPoints[i][j + 1], leftPoints[i + 1][j + 1], leftPoints[i + 1][j]);
            }
        }

        g2d.setColor(previousColor);
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
        
        points[0][1] = calculatePoint(points[0][0], points[0][pointCountNumbers - 1], (int)(CHESS_BOARD_MARGIN * bottomFactor));
        points[pointCountLetters - 1][1] = calculatePoint(points[pointCountLetters - 1][0], points[pointCountLetters - 1][pointCountNumbers - 1], (int)(CHESS_BOARD_MARGIN * bottomFactor));
        {
            Point middlePoint = calculateMiddlePoint(points[0][1], points[pointCountLetters - 1][1]);
            Point leftAfterMargin = calculatePoint(points[0][1], middlePoint, (int)(CHESS_BOARD_MARGIN * bottomFactor));
            points[1][1] = leftAfterMargin;
            double distanceToMiddle = calculateDistance(leftAfterMargin, middlePoint);
            int distancePerRow = (int) (distanceToMiddle / (CHESS_BOARD_SIZE / 2));
            for (int i = 2; i < middlePointIndex; i++) {
                points[i][1] = calculatePoint(points[i - 1][1], middlePoint, distancePerRow);
            }
            points[middlePointIndex][1] = middlePoint;
            Point rightAfterMargin = calculatePoint(points[pointCountLetters - 1][1], middlePoint, (int)(CHESS_BOARD_MARGIN * bottomFactor));
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

    private void fillPolygon(Graphics2D g2d, Point... points) {
        int[] xPoints = new int[points.length];
        int[] yPoints = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            xPoints[i] = points[i].x;
            yPoints[i] = points[i].y;
        }
        g2d.fillPolygon(xPoints, yPoints, points.length);
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
}
