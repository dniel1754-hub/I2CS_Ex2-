package Ex2;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Ex2_GUI - A comprehensive interactive GUI for Map2D.
 * Supports wall building, start/end selection, BFS pathfinding with parameters, and Flood Fill.
 */
public class Ex2_GUI {
    private static final Color[] PALETTE = {
            StdDraw.WHITE,  // 0 - Path
            StdDraw.BLACK,  // 1 - Wall (Obstacle)
            StdDraw.GRAY,   // 2 - Fill Color
            StdDraw.GREEN,  // 3 - Start Point
            StdDraw.ORANGE, // 4 - General
            StdDraw.YELLOW, // 5 - Shortest Path
            StdDraw.RED,    // 6 - End Point
            StdDraw.BLUE    // 7 - General
    };

    private static Pixel2D startPoint = null;
    private static Pixel2D endPoint = null;
    private static boolean fillMode = false;
    private static boolean isCyclic = false; // Toggle for map cyclicity
    private static final int OBSTACLE_VAL = 1; // The value representing a wall

    /**
     * Entry point of the program. Handles the window setup, main input loop,
     * and coordinates user interactions with the map and buttons.
     */
    public static void main(String[] args) {
        Map2D map = loadMap("map.txt");
        if (map == null) map = new Map(20, 20, 0);

        StdDraw.setCanvasSize(1100, 800);
        StdDraw.enableDoubleBuffering();

        while (true) {
            drawMap(map);

            if (StdDraw.isMousePressed()) {
                double mx = StdDraw.mouseX();
                double my = StdDraw.mouseY();
                int w = map.getWidth();
                int h = map.getHeight();

                if (mx >= -0.5 && mx < w - 0.5 && my >= -0.5 && my < h - 0.5) {
                    int x = (int) Math.round(mx);
                    int y = h - 1 - (int) Math.round(my);
                    Pixel2D p = new Index2D(x, y);

                    if (map.isInside(p)) {
                        if (fillMode) {
                            map.fill(p, 2, isCyclic);
                            fillMode = false;
                        } else {
                            handleMapClick(map, p);
                        }
                    }
                    StdDraw.pause(200);
                }


                if (mx > w && mx < w + 3.5) {
                    if (my > h * 0.82 && my < h * 0.92) { // Solve BFS
                        if (startPoint != null && endPoint != null) solveAndColor(map);
                    } else if (my > h * 0.68 && my < h * 0.78) { // Flood Fill
                        fillMode = !fillMode;
                    } else if (my > h * 0.54 && my < h * 0.64) { // Cyclic Toggle
                        isCyclic = !isCyclic;
                    } else if (my > h * 0.40 && my < h * 0.50) { // Clear All
                        reset(map);
                    } else if (my > h * 0.26 && my < h * 0.36) { // Save Map
                        saveMap(map, "map.txt");
                    }
                    StdDraw.pause(200);
                }
            }
            StdDraw.pause(20);
        }
    }

    /**
     * Handles point selection logic. Sets the Start point on the first click,
     * the End point on the second, and toggles walls thereafter.
     */
    private static void handleMapClick(Map2D map, Pixel2D p) {
        if (startPoint == null) {
            startPoint = p;
            map.setPixel(p, 0);
        } else if (endPoint == null && !p.equals(startPoint)) {
            endPoint = p;
            map.setPixel(p, 0);
        } else if (!p.equals(startPoint) && !p.equals(endPoint)) {
            int current = map.getPixel(p);
            map.setPixel(p, current == OBSTACLE_VAL ? 0 : OBSTACLE_VAL);
        }
    }

    /**
     * Renders the 2D grid cells using the palette and draws thin black lines
     * to create a clear table-like visual structure.
     */
    public static void drawMap(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        StdDraw.setXscale(-0.5, w + 3.5);
        StdDraw.setYscale(-0.5, h - 0.5);
        StdDraw.clear();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int colorIndex = map.getPixel(x, y);

                if (startPoint != null && x == startPoint.getX() && y == startPoint.getY()) colorIndex = 3;
                else if (endPoint != null && x == endPoint.getX() && y == endPoint.getY()) colorIndex = 6;

                StdDraw.setPenColor(PALETTE[colorIndex]);
                StdDraw.filledSquare(x, h - 1 - y, 0.5);

                StdDraw.setPenColor(Color.BLACK);
                StdDraw.setPenRadius(0.001);
                StdDraw.square(x, h - 1 - y, 0.5);
            }
        }
        drawInterface(w, h);
        StdDraw.show();
    }

    /**
     * Draws the side menu buttons, including the Solve, Fill, Cyclic Toggle,
     * Clear, and Save buttons with their current states.
     */
    private static void drawInterface(int w, int h) {
        double bx = w + 1.75;

        // Solve Button
        StdDraw.setPenColor(Color.CYAN);
        StdDraw.filledRectangle(bx, h * 0.87, 1.5, 0.4);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(bx, h * 0.87, "Solve BFS");

        // Flood Fill Button
        StdDraw.setPenColor(fillMode ? Color.YELLOW : Color.LIGHT_GRAY);
        StdDraw.filledRectangle(bx, h * 0.73, 1.5, 0.4);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(bx, h * 0.73, "Flood Fill");

        // Cyclic Mode Toggle
        StdDraw.setPenColor(isCyclic ? Color.PINK : Color.LIGHT_GRAY);
        StdDraw.filledRectangle(bx, h * 0.59, 1.5, 0.4);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(bx, h * 0.59, "Cyclic: " + (isCyclic ? "ON" : "OFF"));

        // Clear Button
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(bx, h * 0.45, 1.5, 0.4);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(bx, h * 0.45, "Clear All");

        // Save Button
        StdDraw.setPenColor(new Color(200, 255, 200));
        StdDraw.filledRectangle(bx, h * 0.31, 1.5, 0.4);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(bx, h * 0.31, "Save Map");
    }

    /**
     * Calls shortestPath with start/end points, obstacle value, and cyclic status,
     * then colors the resulting path yellow.
     */
    private static void solveAndColor(Map2D map) {
        // Clear previous yellow paths
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (map.getPixel(i, j) == 5) map.setPixel(i, j, 0);

        Pixel2D[] path = map.shortestPath(startPoint, endPoint, OBSTACLE_VAL, isCyclic);
        if (path != null) {
            for (Pixel2D p : path) {
                if (!p.equals(startPoint) && !p.equals(endPoint)) {
                    map.setPixel(p, 5);
                }
            }
        }
    }

    /**
     * Clears all selection points and removes any calculated shortest paths
     * from the current map data.
     */
    private static void reset(Map2D map) {
        startPoint = null;
        endPoint = null;
        fillMode = false;
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (map.getPixel(i, j) == 5) map.setPixel(i, j, 0);
    }

    /**
     * Reads map dimensions and pixel values from a text file to reconstruct
     * the Map2D object.
     */
    public static Map2D loadMap(String fileName) {
        try (Scanner sc = new Scanner(new File(fileName))) {
            int w = sc.nextInt();
            int h = sc.nextInt();
            Map m = new Map(w, h, 0);
            for (int j = 0; j < h; j++)
                for (int i = 0; i < w; i++)
                    if (sc.hasNextInt()) m.setPixel(i, j, sc.nextInt());
            return m;
        } catch (Exception e) { return null; }
    }

    /**
     * Writes the map dimensions and all current pixel integer values
     * into a text file for persistent storage.
     */
    public static void saveMap(Map2D map, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(map.getWidth() + " " + map.getHeight());
            for (int j = 0; j < map.getHeight(); j++) {
                for (int i = 0; i < map.getWidth(); i++) out.print(map.getPixel(i, j) + " ");
                out.println();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}