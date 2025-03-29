package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

//    public LoadSave() {
//        BufferedImage levelOneData = GetSpriteAtlas(LEVEL_ONE_DATA);
//        System.out.println(levelOneData.getHeight()); // 14
//        System.out.println(levelOneData.getWidth()); // 26
//    }
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    /*
    * LevelData = Each A[i][j] has the value of the sprite index at that [i][j]
    * */
    public static int[][] GetLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));

                int redValue = color.getRed();
                if (redValue >= 48)
                    redValue = 0;

                levelData[i][j] = redValue;
            }
        }

        return levelData;
    }
}