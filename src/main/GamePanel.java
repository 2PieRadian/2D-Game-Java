package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        MouseInputs mouseInputs = new MouseInputs(this);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        // 32x32 Image Size
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
//        setMinimumSize(size);
        setPreferredSize(size);
//        setMaximumSize(size);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}