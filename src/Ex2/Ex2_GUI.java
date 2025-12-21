package Ex2;

import java.awt.*;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {
    private static final Color[] PALETTE = {
            StdDraw.WHITE, // אינדקס 0
            StdDraw.BLACK,// אינדקס 1
            StdDraw.GRAY,// אינדקס 2
            StdDraw.GREEN,// אינדקס 3
            StdDraw.ORANGE,// אינדקס 4
            StdDraw.YELLOW,// אינדקס 5
            StdDraw.RED,   // אינדקס 6
            StdDraw.BLUE   // אינדקס 7
    };
    public static void drawMap(Map2D map) {
        //
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;

        return ans;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {


    }
    static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        drawMap(map);
    }
    /// ///////////// Private functions ///////////////
}
