package main;

import entities.Player;

import java.awt.*;

public class Game implements Runnable {
    private final GamePanel gamePanel;
    private final int FPS_SET = 144;
    private final int UPS_SET = 200;

    private Player player;

    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        new GameWindow(gamePanel);
        gamePanel.requestFocus();

        Thread thread = new Thread(this);
        thread.start();
    }

    private void initClasses() {
        player = new Player(100, 100);
    }

    private void update() {
        player.update();
    }

    public void render(Graphics g) {
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
