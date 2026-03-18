package logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GamePanel is responsible for:
// Game loop (using a Timer to call actionPerformed regularly)
// Handling mouse input (to place towers)
// Drawing the game (by overriding paintComponent)

public class GamePanel extends JPanel implements ActionListener {

    // sets the window size
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;

    // Controls the game loop
    private Timer timer;
    // Stores all game logic and data
    private GameState gameState;

    public GamePanel() {
        // Tells Swing the desired panel size
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        // Creates the core game logic object
        gameState = new GameState();
        // calls actionPerformed every 16 miliseconds
        timer = new Timer(16, this); // ~60 FPS
        // start the game loop
        timer.start();

        MouseHandler mouseHandler = new MouseHandler(gameState);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    // class that has the game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        // Updates the entire game logic (Moves enemies, Fires towers, Updates
        // projectiles, Spawns enemies)
        gameState.update();
        // Requests Swing to redraw the panel
        repaint();
    }

    // Called whenever Swing redraws the panel
    // This is where all drawing happens.
    @Override
    protected void paintComponent(Graphics g) {
        // Clears the panel properly ( erase old frames)
        super.paintComponent(g);
        // draw the game again
        gameState.draw(g);
    }

}
