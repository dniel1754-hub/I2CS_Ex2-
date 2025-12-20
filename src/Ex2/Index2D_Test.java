package Ex2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Index2D_Test {
    @Test
    void testGettersAndConstructor() {
        Index2D a = new Index2D(5, 6);
        assertEquals(5, a.getX());  // x shoud be 5
        assertEquals(6, a.getY());  // y shoud be 6
    }

    @Test
    void testGettersAndConstructor2() {
        Index2D p = new Index2D(3, 4);
        assertEquals(3, p.getX(), "X should be 3");
        assertEquals(4, p.getY(), "Y should be 4");
    }


}