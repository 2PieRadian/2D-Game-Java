package main;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        setTitle("Sprites");
        add(gamePanel);

        pack(); // Use pack() after add(gamePanel)
        setLocationRelativeTo(null); // Set this after we have used pack(), because we don't know the real size of the window before packing it.

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
