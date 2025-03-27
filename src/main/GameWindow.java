package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        setTitle("Sprites");
        add(gamePanel);

        pack(); // Use pack() after add(gamePanel)
        setLocationRelativeTo(null); // Set this after we have used pack(), because we don't know the real size of the window before packing it.

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowOutOfFocus();
            }
        });
    }
}
