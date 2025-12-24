package Ex2;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
            int w = map.getWidth();
            int h = map.getHeight();


            StdDraw.setCanvasSize(w * 100, h * 100);
            StdDraw.setXscale(-0.5, w - 0.5);
            StdDraw.setYscale(-0.5, h - 1.5);

        StdDraw.enableDoubleBuffering();


            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    int colorIndex = map.getPixel(x, y);

                    if (colorIndex >= 0 && colorIndex < PALETTE.length) {
                        StdDraw.setPenColor(PALETTE[colorIndex]);
                    } else {
                        StdDraw.setPenColor(Color.MAGENTA); // צבע בולט לשגיאות
                    }

                    double centerX = x  ;
                    double centerY = h - 1 - y  ;

                    StdDraw.filledSquare(centerX, centerY, 0.48); // 0.48 כדי להשאיר רווח קטן
                }
            }
            StdDraw.show();
        }


    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;


            // שימוש ב-try-with-resources לסגירה אוטומטית של הקובץ
            try (Scanner sc = new Scanner(new File(mapFileName))) {
                if (!sc.hasNextInt())
                    return null;

                int w = sc.nextInt();
                int h = sc.nextInt();

                 ans = new Map(w, h, 0); // הנחה שקיימת מחלקה Map
                for (int j = 0; j < h; j++) {
                    for (int i = 0; i < w; i++) {
                        if (sc.hasNextInt()) {
                            ans.setPixel(i, j, sc.nextInt());
                        }
                    }
                }
                return ans;
            }
            catch (FileNotFoundException e) {
                System.err.println("Error: File not found " + mapFileName);
                return null;
            }
            finally {
                return ans;
            }
        }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
            try (java.io.PrintWriter out = new java.io.PrintWriter(mapFileName)) {
                out.println(map.getWidth() + " " + map.getHeight()); // מימדים
                for (int j = 0; j < map.getHeight(); j++) {
                    for (int i = 0; i < map.getWidth(); i++) {
                        out.print(map.getPixel(i, j) + " ");
                    }
                    out.println();
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }



    static void main(String[] a) {



        String mapFile = "src/map.txt";
        Map2D map = loadMap(mapFile);
        if (map != null) {
            drawMap(map);
        } else {
            System.out.println("Could not load map.");
        }
    }

}
