package com.komabjn.threeplayerchess.rendering.util;

import java.awt.Point;

/**
 *
 * @author Komabjn
 */
public class PolygonUtil {
    /**
     * Java implementation of algorithm found in:
     * https://wrfranklin.org/Research/Short_Notes/pnpoly.html
     *
     * @param p
     * @param polygonVertices
     * @return
     */
    public static boolean isPointInsidePolygon(Point p, Point... polygonVertices) {
        int nvert = polygonVertices.length;
        int i, j;
        boolean c = false;
        for (i = 0, j = nvert - 1; i < nvert; j = i++) {
            if (((polygonVertices[i].y > p.y) != (polygonVertices[j].y > p.y))
                    && (p.x < (polygonVertices[j].x - polygonVertices[i].x) * (p.y - polygonVertices[i].y) / (polygonVertices[j].y - polygonVertices[i].y) + polygonVertices[i].x)) {
                c = !c;
            }
        }
        return c;
    }
}
