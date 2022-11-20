package com.komabjn.threeplayerchess;

import com.komabjn.threeplayerchess.rendering.ThreePlayerChessboardRendererFrame;
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
            ThreePlayerChessboardRendererFrame renderer = new ThreePlayerChessboardRendererFrame();
            renderer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            renderer.pack();
            renderer.setVisible(true);
        });
    }
}
