package Ex2;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Queue;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */


class MapTest {
    private Map a;
        private Map2D _map;
        private final int DEFAULT_VAL = 0;

        @BeforeEach
        void setUp() {
            _map = new Map(0); // החלף בשם המחלקה שלך
        }
    /** Tests that the map is initialized correctly with given dimensions and initial value. */
        @Test
        void testInitDimensions() {
            _map.init(10, 20, 5);
            assertEquals(10, _map.getWidth());
            assertEquals(20, _map.getHeight());
            assertEquals(5, _map.getPixel(0, 0));
            assertEquals(5, _map.getPixel(9, 19));
        }
    /** Verifies map creation from a 2D array and ensures a Deep Copy is performed. */
        @Test
        void testInitFromArrayAndDeepCopy() {
            int[][] arr = {
                    {1, 2, 3},
                    {4, 5, 6}
            };
            _map.init(arr);
            assertEquals(2, _map.getWidth());
            assertEquals(3, _map.getHeight());
            arr[0][0] = 99;
            assertNotEquals(99, _map.getPixel(0, 0));
            assertEquals(1, _map.getPixel(0, 0));
        }
    /** Ensures that the array returned by getMap is a copy and does not modify the original map. */
        @Test
        void testGetMapDeepCopy() {
            _map.init(5, 5, 10);
            int[][] copy = _map.getMap();
            copy[0][0] = 50;
            // שינוי בהעתק לא אמור לשנות את המפה הפנימית
            assertNotEquals(50, _map.getPixel(0, 0));
        }
    /** Tests getting and setting pixel values using coordinates and Pixel2D objects. */
        @Test
        void testSetAndGetPixel() {
            _map.init(10, 10, 0);
            Pixel2D p = new Index2D(3, 4);
            _map.setPixel(3, 4, 7);
            assertEquals(7, _map.getPixel(p));

            _map.setPixel(p, 9);
            assertEquals(9, _map.getPixel(3, 4));
        }
    /** Checks if coordinates are correctly identified as being inside or outside map boundaries. */
        @Test
        void testIsInside() {
            _map.init(10, 10, 0);
            assertTrue(_map.isInside(new Index2D(0, 0)));
            assertTrue(_map.isInside(new Index2D(9, 9)));
            assertFalse(_map.isInside(new Index2D(10, 5)));
            assertFalse(_map.isInside(new Index2D(-1, 0)));
        }
    /** Tests the addition of another Map2D's values to the current map. */
        @Test
        void testAddMap2D() {
            _map.init(2, 2, 10);
            Map2D other = new Map(0);
            other.init(2, 2, 5);

            _map.addMap2D(other);
            assertEquals(15, _map.getPixel(0, 0));

            // בדיקה שלא קורה כלום אם הממדים שונים
            Map2D wrongDim = new Map(0);
            wrongDim.init(3, 3, 100);
            _map.addMap2D(wrongDim);
            assertEquals(15, _map.getPixel(0, 0)); // נשאר אותו דבר
        }
    /** Tests multiplying all map pixels by a scalar value. */
        @Test
        void testMul() {
            _map.init(2, 2, 10);
            _map.mul(0.5);
            assertEquals(5, _map.getPixel(0, 0));
            _map.mul(2.1); // 5 * 2.1 = 10.5 -> floor/cast to 10
            assertEquals(10, _map.getPixel(1, 1));
        }
    /** Verifies that the map dimensions are updated correctly after a rescale operation. */
        @Test
        void testRescale() {
            _map.init(100, 200, 1);
            _map.rescale(1.2, 0.5);
            assertEquals(120, _map.getWidth());
            assertEquals(100, _map.getHeight());
        }
    /** Tests drawing a filled rectangle between two points. */
        @Test
        void testDrawRect() {
            _map.init(10, 10, 0);
            Pixel2D p1 = new Index2D(2, 2);
            Pixel2D p2 = new Index2D(4, 4);
            int color = 5;
            _map.drawRect(p1, p2, color);

            assertEquals(color, _map.getPixel(2, 2));
            assertEquals(color, _map.getPixel(4, 4));
            assertEquals(color, _map.getPixel(2, 4));
            assertEquals(0, _map.getPixel(1, 1)); // מחוץ למלבן
        }
    /** Tests drawing a filled circle based on center point and radius. */
        @Test
        void testDrawCircle() {
            _map.init(20, 20, 0);
            Pixel2D center = new Index2D(10, 10);
            double rad = 3.5;
            int color = 7;
            _map.drawCircle(center, rad, color);

            assertTrue(_map.getPixel(10, 10) == color); // המרכז
            assertTrue(_map.getPixel(13, 10) == color); // מרחק 3 (בתוך הרדיוס)
            assertFalse(_map.getPixel(15, 10) == color); // מרחק 5 (מחוץ לרדיוס)
        }
    /** Tests drawing a line between two points and verifies intermediate pixels. */
        @Test
        void testDrawLine() {
            _map.init(10, 10, 0);
            Pixel2D p1 = new Index2D(1, 1);
            Pixel2D p2 = new Index2D(5, 1); // קו אופקי
            int color = 3;
            _map.drawLine(p1, p2, color);

            for (int x = 1; x <= 5; x++) {
                assertEquals(color, _map.getPixel(x, 1), "Pixel at " + x + ",1 should be colored");
            }
            assertEquals(0, _map.getPixel(1, 2));
        }
    /** Verifies the equality of two maps with identical content and dimensions. */
        @Test
        void testEquals1() {
            _map.init(3, 3, 1);
            Map2D other = new Map(0);
            other.init(3, 3, 1);

            assertTrue(_map.equals(other));

            other.setPixel(0, 0, 9);
            assertFalse(_map.equals(other));
        }





    // tests for getneighbors function
    /** Ensures a pixel in the middle of the map has 4 neighbors. */
    @Test
    void testGetNeighborsMiddle() {
        Map m = new Map(10, 10, 0);
        Pixel2D p = new Index2D(5, 5);
        // במרכז המפה, תמיד צריכים להיות 4 שכנים
        Queue<Pixel2D> neighbors = m.getNeighbers(p, false);
        assertEquals(4, neighbors.size(), "שכן במרכז המפה חייב להחזיר 4 שכנים");
    }
    /** Tests the simple flood fill algorithm. */
    @Test
    void simpleFillTest() {
        // 1. יצירת מפה של 3X3 מלאה ב-0
        Map m = new Map(3, 3, 0);

        // 2. צביעה של המרכז
        Pixel2D start = new Index2D(1, 1);
        int count = m.fill(start, 5, false);

        // 3. בדיקה: האם נצבעו 9 תאים? האם הצבע במרכז הוא 5?
        assertEquals(9, count, "כל המפה אמורה להיצבע");
        assertEquals(5, m.getPixel(1, 1), "הצבע בנקודת ההתחלה חייב להשתנות");
        assertEquals(5, m.getPixel(0, 0), "הצבע בפינה חייב להשתנות");
    }
    /** Verifies that a corner pixel in a non-cyclic map has only 2 neighbors. */
    @Test
    void testGetNeighborsCornerNonCyclic() {
        Map m = new Map(10, 10, 0);
        Pixel2D p = new Index2D(0, 0);
        // בפינה ללא מחזוריות, יש רק 2 שכנים (ימינה ולמטה)
        Queue<Pixel2D> neighbors = m.getNeighbers(p, false);
        assertEquals(2, neighbors.size(), "בפינה ללא מחזוריות צריכים להיות רק 2 שכנים");
    }
    /** Verifies that a corner pixel in a cyclic map has 4 neighbors due to wrap-around. */
    @Test
    void testGetNeighborsCornerCyclic() {
        Map m = new Map(10, 10, 0);
        Pixel2D p = new Index2D(0, 0);
        // בפינה עם מחזוריות, תמיד צריכים להיות 4 שכנים (חלקם "עוטפים" לצד השני)
        Queue<Pixel2D> neighbors = m.getNeighbers(p, true);
        assertEquals(4, neighbors.size(), "במפה מחזורית תמיד צריכים להיות 4 שכנים");
    }
    // test for rescale
    @BeforeEach
     public void setUp2() {
        // אתחול מפה בגודל 10x10 עם ערכים כלשהם
         a = new Map(10, 10, 5);


    }

    @Test
    void testDownScale() {
        // הקטנה בחצי - אמור להיות 5x5
        a.rescale(0.5, 0.5);
        assertEquals(5, a.getWidth());
        assertEquals(5, a.getHeight());
        // בדיקה שהערכים שנשארו אכן הועתקו נכון
        assertEquals(5, a.getMap()[0][0]);
    }

    @Test
    void testUpScale() {
        // הגדלה פי 2 - אמור להיות 20x20
        a.rescale(2.0, 2.0);
        assertEquals(20, a.getWidth());
        assertEquals(20, a.getHeight());
        // הפינה המקורית צריכה להישאר 5, השאר יהיה 0 (ברירת מחדל של int)
        assertEquals(5, a.getMap()[0][0]);
        assertEquals(0, a.getMap()[15][15]);
    }

    @Test
    void testInvalidScale() {
        // בדיקה שזריקת השגיאה עובדת על ערכים שליליים
        assertThrows(RuntimeException.class, () -> {
            a.rescale(-1.0, 5.0);
        });
    }

    // --- טסטים לפונקציה המרכזית fill ---
    /** Tests the simple flood fill algorithm on an empty map. */
    @Test
    void testFillSimple() {
        Map m = new Map(5, 5, 0); // מפה של 5x5 מאופסת
        Pixel2D p = new Index2D(2, 2);
        int changed = m.fill(p, 1, false);

        // המפה כולה הייתה 0, לכן כל 25 הפיקסלים צריכים להשתנות ל-1
        assertEquals(25, changed);
        assertEquals(1, m.getPixel(0, 0));
        assertEquals(1, m.getPixel(4, 4));
    }
    /** Verifies that flood fill respects boundaries created by lines. */
    @Test
    void testFillWithBoundary() {
        Map m = new Map(5, 5, 0);
        // ניצור "קיר" של 1-ים שיחצה את המפה
        m.drawLine(new Index2D(2, 0), new Index2D(2, 4), 1);

        // נבצע fill בנקודה (0,0) עם צבע 2. זה אמור לצבוע רק צד אחד של הקיר.
        // צד אחד הוא 2 שורות (0 ו-1) כפול 5 פיקסלים = 10
        int changed = m.fill(new Index2D(0, 0), 2, false);
        assertEquals(10, changed);
        assertEquals(2, m.getPixel(0, 0));
        assertEquals(1, m.getPixel(2, 0)); // הקיר נשאר 1
        assertEquals(0, m.getPixel(3, 0)); // הצד השני נשאר 0
    }
    /** Ensures fill returns zero changes when filling with the same color. */
    @Test
    void testFillSameColor() {
        Map m = new Map(5, 5, 7);
        Pixel2D p = new Index2D(1, 1);
        // ניסיון לצבוע בערך שכבר קיים
        int changed = m.fill(p, 7, false);
        assertEquals(0, changed, "צביעה באותו צבע צריכה להחזיר 0 שינויים");
    }
    // test for allDistance function
    /** Tests distance map calculation from a start point in an empty grid. */
    @Test
    void testSimpleDistances() {
        // יצירת מפה 3x3 שכולה בצבע 0
        Map m = new Map(3, 3, 0);
        Pixel2D start = new Index2D(0, 0);

        // חישוב מרחקים (אין מכשולים, צבע המכשול הוא 1)
        Map2D res = m.allDistance(start, 1, false);

        // בדיקת מרחקים צפויים:
        // 0 1 2
        // 1 2 3
        // 2 3 4
        assertEquals(0, res.getPixel(0, 0));
        assertEquals(2, res.getPixel(2, 0)); // מרחק לנקודה (2,0)
        assertEquals(4, res.getPixel(2, 2)); // הנקודה הכי רחוקה
    }
    /** Tests that distance calculation correctly bypasses obstacles. */
    @Test
    void testWithObstacle() {
        Map m = new Map(3, 3, 0);
        // נשים מכשול (צבע 1) בנקודה (1,0) ובנקודה (1,1)
        m.setPixel(1, 0, 1);
        m.setPixel(1, 1, 1);

        Pixel2D start = new Index2D(0, 0);
        Map2D res = m.allDistance(start, 1, false);

        // בנקודת המכשול צריך להיות 1- (לא נגיש/מכשול)
        assertEquals(-1, res.getPixel(1, 0));

        // הדרך ל-(2,0) עכשיו ארוכה יותר כי צריך לעקוף מלמטה
        // מסלול אפשרי: (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (2,1) -> (2,0)
        // המרחק אמור להיות 6
        assertEquals(6, res.getPixel(2, 0));
    }
    /** Verifies distance calculation in a cyclic map environment. */
    @Test
    void testCyclicDistances() {
        Map m = new Map(10, 10, 0);
        Pixel2D start = new Index2D(0, 0);

        // נבדוק מרחק לנקודה (9,0) - שהיא הקצה השני של ציר ה-X
        Map2D res = m.allDistance(start, 1, true); // cyclic = true

        // במצב רגיל המרחק הוא 9. במצב מחזורי המרחק הוא רק 1!
        assertEquals(1, res.getPixel(9, 0));

        // באותו אופן לנקודה (0,9)
        assertEquals(1, res.getPixel(0, 9));
    }
    /** Checks that unreachable areas are marked correctly with -1. */
    @Test
    void testUnreachable() {
        Map m = new Map(5, 5, 0);
        // נבנה ריבוע של מכשולים (צבע 1) מסביב למרכז (2,2)
        m.setPixel(2, 1, 1);
        m.setPixel(1, 2, 1);
        m.setPixel(3, 2, 1);
        m.setPixel(2, 3, 1);

        Pixel2D start = new Index2D(0, 0);
        Map2D res = m.allDistance(start, 1, false);

        // הנקודה (2,2) חסומה מכל כיוון, לכן המרחק אליה חייב להיות 1-
        assertEquals(-1, res.getPixel(2, 2));
    }


        private Map2D map;
        private final int OBS = 1; // צבע המכשול
        private final int FREE = 0; // שטח פתוח
    /** Verifies the shortest path calculation between two points in an empty map. */
        @Test
        void testSimplePath() {
            // מפה ריקה בגודל 5x5
            map = new Map(5, 5, FREE);
            Pixel2D p1 = new Index2D(0, 0);
            Pixel2D p2 = new Index2D(2, 2);

            Pixel2D[] path = map.shortestPath(p1, p2, OBS, false);

            // המרחק הקצר ביותר בין (0,0) ל-(2,2) הוא 4 צעדים
            // לכן המערך צריך להיות באורך 5 (כולל נקודת ההתחלה)
            assertNotNull(path, "המסלול לא אמור להיות null");
            assertEquals(5, path.length);
            assertEquals(p1, path[0]);
            assertEquals(p2, path[path.length - 1]);
            assertTrue(isValidPath(path, OBS, false), "המסלול חייב להיות רציף וללא מכשולים");
        }
    /** Verifies that the shortest path successfully navigates around obstacles. */
        @Test
        void testPathWithObstacles() {
            map = new Map(5, 5, FREE);
            // יצירת "קיר" שחוסם את הדרך הישירה בין (0,2) ל-(4,2)
            map.setPixel(2, 1, OBS);
            map.setPixel(2, 2, OBS);
            map.setPixel(2, 3, OBS);

            Pixel2D p1 = new Index2D(0, 2);
            Pixel2D p2 = new Index2D(4, 2);

            Pixel2D[] path = map.shortestPath(p1, p2, OBS, false);

            assertNotNull(path);
            // בדיקה שהמסלול לא עובר דרך הקיר
            for (Pixel2D p : path) {
                assertNotEquals(OBS, map.getPixel(p), "המסלול עובר דרך מכשול!");
            }
            assertTrue(isValidPath(path, OBS, false));
        }
    /** Tests that the shortest path utilizes map cyclicity to find a faster route. */
        @Test
        void testCyclicPath() {
            // מפה 10x10, מעבר מקצה לקצה
            map = new Map(10, 10, FREE);
            Pixel2D p1 = new Index2D(0, 5);
            Pixel2D p2 = new Index2D(9, 5);

            // במצב רגיל המרחק הוא 9. במצב מחזורי המרחק הוא 1 (קפיצה אחת שמאלה)
            Pixel2D[] path = map.shortestPath(p1, p2, OBS, true);

            assertNotNull(path);
            assertEquals(2, path.length, "במפה מחזורית המסלול אמור להיות באורך 2");
            assertEquals(p1, path[0]);
            assertEquals(p2, path[1]);
        }
    /** Ensures the pathfinding function returns null when no valid path exists. */
        @Test
        void testNoPath() {
            map = new Map(3, 3, FREE);
            // חסימת היעד (2,2) מכל הכיוונים
            map.setPixel(1, 2, OBS);
            map.setPixel(2, 1, OBS);

            Pixel2D p1 = new Index2D(0, 0);
            Pixel2D p2 = new Index2D(2, 2);

            Pixel2D[] path = map.shortestPath(p1, p2, OBS, false);

            // לא ניתן להגיע, הפונקציה צריכה להחזיר null לפי הדרישות
            assertNull(path, "כשאין מסלול חוקי התוצאה צריכה להיות null");
        }

        // מתודת עזר לבדיקה שהמסלול תקין ורציף
        private boolean isValidPath(Pixel2D[] path, int obsColor, boolean cyclic) {
            if (path == null || path.length == 0) return false;
            for (int i = 0; i < path.length - 1; i++) {
                if (map.getPixel(path[i]) == obsColor) return false;
                // בדיקה שכל איבר הוא שכן של האיבר הבא
                if (!isNeighbor(path[i], path[i+1], cyclic)) return false;
            }
            return map.getPixel(path[path.length-1]) != obsColor;
        }

        private boolean isNeighbor(Pixel2D a, Pixel2D b, boolean cyclic) {
            int dx = Math.abs(a.getX() - b.getX());
            int dy = Math.abs(a.getY() - b.getY());
            if (!cyclic) return (dx + dy == 1);

            // במצב מחזורי, מרחק של width-1 נחשב גם הוא לשכנות (מרחק 1 בפועל)
            int w = map.getWidth();
            int h = map.getHeight();
            if (dx == w - 1) dx = 1;
            if (dy == h - 1) dy = 1;
            return (dx + dy == 1);

    }

    private final int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setuo() {

        _m3_3 = new Map(_map_3_3);
        _m1 = new Map(500);
        _m0 = new Map(500);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }
}

