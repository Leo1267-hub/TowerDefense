package logic;

import javax.swing.*;

// is responsiblle for:
// - creating the main window (JFrame)
// - creating the GamePanel (which runs the game loop and draws the game)

public class Main {
        public static void main(String[] args) {
                // creates new window
                JFrame frame = new JFrame("Tower Defense");
                // terminates when exit the window
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // cant resize the window
                frame.setResizable(false);
                // creates the game scree ( Draws the game, Runs the game loop)
                GamePanel panel = new GamePanel();
                // Window + panel = visible game
                frame.add(panel);
                // pack() makes the window match that size exactly
                frame.pack();
                // Centers the window on the screen
                frame.setLocationRelativeTo(null);
                // Makes the window appear on screen
                frame.setVisible(true);
        }
}
