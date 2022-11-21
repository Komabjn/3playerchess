package com.komabjn.threeplayerchess.rendering;

import com.komabjn.threeplayerchess.api.ChessPieceType;
import com.komabjn.threeplayerchess.api.Player;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Komabjn
 */
public class ChesspiecesGraphicsRepository {
    
    private static ChesspiecesGraphicsRepository instance;
    
    private final HashMap<Integer, BufferedImage> graphics = new HashMap<>();

    private ChesspiecesGraphicsRepository() {
    }
    
    public static ChesspiecesGraphicsRepository getInstance(){
        if(Objects.isNull(instance)){
            instance = new ChesspiecesGraphicsRepository();
        }
        return instance;
    }
    
    public BufferedImage getChessGraphics(ChessPieceType pieceType, Player player){
        int hash = createHash(pieceType, player);
        BufferedImage img = graphics.get(hash);
        if(img == null){
            try {
                img = ImageIO.read(getClass().getResource("/pieces/" + convertChessPieceToName(pieceType) + "_" + convertPlayerToColorName(player) + ".png"));
                graphics.put(hash, img);
            } catch (IOException ex) {
                Logger.getLogger(ChesspiecesGraphicsRepository.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return img;
    }

    private String convertPlayerToColorName(Player player){
        switch(player){
            case PLAYER_1:
                return "white";
            case PLAYER_2:
                return "black";
            case PLAYER_3:
                return "red";
            default:
                return null;
        }
    }
    
    private String convertChessPieceToName(ChessPieceType chessPieceType){
        return chessPieceType.name().toLowerCase();
    }
    
    private int createHash(ChessPieceType pieceType, Player player){
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(pieceType);
        hash = 97 * hash + Objects.hashCode(player);
        return hash;
    }
    
}
