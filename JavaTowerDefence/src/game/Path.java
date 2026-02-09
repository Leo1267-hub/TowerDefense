package game;

import java.awt.*;
import java.util.ArrayList;

public class Path {

    private ArrayList<Point> points = new ArrayList<>();
    public static final int TILE_SIZE = 64;

    public Path() {
        // initialize the path with some points
        add(0, 5);
        add(3, 5);
        add(3, 9);
        add(8, 9);
        add(8, 6);
        add(19, 6);
    }

    // declare an array of points representing the path
    // every point is a corner where the path changes direction
    // private Point[] points = {

    // new Point(50, 300),
    // new Point(300, 300),
    // new Point(300, 100),
    // new Point(600, 100),
    // new Point(600, 400),
    // new Point(750, 400)
    // };

    public void add(int tileX, int tileY) {
        points.add(new Point(
                tileX * TILE_SIZE + TILE_SIZE / 2,
                tileY * TILE_SIZE + TILE_SIZE / 2));
    }

    // enemies will ask for points on the path by index
    public Point getPoint(int index) {
        // Which corner it’s currently moving toward
        return points.get(index);
    }

    // enemies shuold know when the path ends and when to stop
    public int length() {
        return points.size();
    }

    // draw the path on the screen
    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        // for each point draw a line to another point
        for (int i = 0; i < points.size() - 1; i++) {
            g.drawLine(points.get(i).x, points.get(i).y,
                    points.get(i + 1).x, points.get(i + 1).y);
        }
    }
}
