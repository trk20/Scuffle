package Model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Handles accesses to a 2D (square shaped) array of type T.
 * Allows to pass Point objects for 2D array indexing.
 *
 * @param <T> The type of the object to make a 2D array for.
 *
 * @author Alex
 * @version NOV-18
 */
public class Grid2DArray<T>{
    // "T" is the generic type
    protected ArrayList<ArrayList<T>> array2D;
    protected int size;

    /**
     * General Constructor, used to copy Grid2DArray
     *
     * @param size The length of the "grid"
     * @param copiedArray The grid to copy into the internal array2D
     */
    private Grid2DArray(int size, ArrayList<ArrayList<T>> copiedArray){
        this.size = size;
        array2D = new ArrayList<>(copiedArray);
    }

    /**
     * Constructor for Grid2DArray
     *
     * @param size The length of the "grid"
     */
    @SuppressWarnings("Convert2Diamond")
    public Grid2DArray(int size){
        this(size, new ArrayList<ArrayList<T>>(size));
        // Initialize grid array with empty inner lists
        for(int i = 0; i < size; i++)
            array2D.add(new ArrayList<T>());
    }

    /**
     * Get a value at point p
     * @param p value coordinates in grid
     */
    public T get(Point p){
        if(isInArray(p))
            return array2D.get(p.x).get(p.y);
        else
            throw new IndexOutOfBoundsException(String
                    .format("Point outside of 2D Array (Size = %d), (Point=(%d,%d))",
                    size, p.x,p.y));
    }

    /**
     * Set a value at point p
     * @param p value coordinates in grid
     */
    public void set(Point p, T t){
        if(isInArray(p))
            array2D.get(p.x).add(p.y, t);
        else
            throw new IndexOutOfBoundsException(String
                    .format("Point outside of 2D Array (Size = %d), (Point=(%d,%d))",
                            size, p.x,p.y));
    }

    /**
     * Returns a deep copy of this Grid2DArray
     * @return a deep copy of this objet
     */
    public Grid2DArray<T> copy(){
        return new Grid2DArray<>(size, array2D);
    }

    /**
     * Checks if a point coordinate is in the Array2D.
     * This means it has positive coordinates, smaller than the size of the array.
     *
     * @param p The point that is checked to be in the Array2D.
     * @return true if the point is in the Array2D
     */
    private boolean isInArray(Point p) {
        return  p.x<size && p.y <size && // Within size bound
                p.x >= 0 && p.y >= 0; // Positive coords
    }

    /**
     * String shows the grid of elements in this format:
     * | element(0,0).toString | element(1,0).toString | (...) | element(size-1,0).toString |
     * | element(0,1).toString | element(1,1).toString | (...) |
     *  (...)
     * | element(0,size-1).toString  | (...) |
     *
     * @return String representation of grid
     */
    @Override
    public String toString() {
        // Note: Final result doesn't preserve the grid shape well (visually) because
        // BoardTile character amount (4 to 2) != Letter character amount (1)

        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < size; y++){
            sb.append("|");
            for(int x = 0; x < size; x++){ // Go row by row (x in inner loop)
                Point p = new Point(x,y);
                sb.append(" ").append(get(p).toString()).append(" |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}