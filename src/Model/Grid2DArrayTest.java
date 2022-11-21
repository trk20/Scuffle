package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class Grid2DArrayTest {

    private Grid2DArray<Integer> grid;

    /**
     * Sets up a new Grid2DArray for use in tests
     */
    @BeforeEach
    void setup(){
        grid = new Grid2DArray<Integer>(15);
    }


    /**
     * Tests that initialization creates a grid of the correct size
     */
    @Test
    void getSize() {
        assertEquals(15,grid.getSize());
        grid = new Grid2DArray<>(127);
        assertEquals(127,grid.getSize());
    }

    /**
     * Tests that setting a point on the grid to a value works and getting it returns the correct value
     */
    @Test
    void setAndGet() {
        grid.set(new Point(0,0),42);
        grid.set(new Point(5,5),12);
        assertEquals(42,grid.get(new Point(0,0)));
        assertEquals(12,grid.get(new Point(5,5)));
        grid.set(new Point(0,0),3);
        assertEquals(3,grid.get(new Point(0,0)));
    }


}