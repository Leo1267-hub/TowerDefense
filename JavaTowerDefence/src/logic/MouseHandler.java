package logic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    private final GameState gameState;

    public MouseHandler(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gameState.handleMouseClick(e.getX(), e.getY());
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        gameState.handleMouseMove(-1, -1);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        gameState.handleMouseMove(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameState.handleMouseMove(e.getX(), e.getY());
    }
}
