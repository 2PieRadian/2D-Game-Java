package main;

import entities.Player;
import levels.Level;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable {
    private final GamePanel gamePanel;
    private final int FPS_SET = 144;
    private final int UPS_SET = 200;

    public static final int TILES_DEFAULT_SIZE = 32; // 32x32
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Player player;
    private LevelManager levelManager;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void initClasses() {
        player = new Player(100, 100, (int) (64*SCALE), (int) (40*SCALE));
        levelManager = new LevelManager(this);
    }

    private void update() {
        player.update();
        levelManager.update();
    }

    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;

        // This is how much time should pass between each game update
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        // TimeStamp of the beginning of the previous loop iteration
        long previousTime = System.nanoTime();

        // Keeping track of the last saved nano-time for displaying new FPS and UPS
        long lastCheck = System.nanoTime();

        int frames = 0;
        int updates = 0;

        // These track how many updates and frames we need to catch up on
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            // Get current time in nanoseconds
            long currentTime = System.nanoTime();

            // Calculate how much time has passed since the last loop iteration
            // (deltaU >= 1) if (time since the last update >= timePerUpdate)
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;

            previousTime = currentTime;

            // UPS
            while (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            // FPS
            while (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            // Print FPS and UPS every second
            if (System.nanoTime() - lastCheck >= 1_000_000_000) {
                System.out.println("FPS : " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastCheck = System.nanoTime();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void windowOutOfFocus() {
        player.resetDirectionBooleans();
    }
}
