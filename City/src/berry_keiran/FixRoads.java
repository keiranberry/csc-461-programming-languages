package berry_keiran;

public class FixRoads implements Visitor{

    private Tile[][] grid;

    /**
     * \brief Constructor for FixRoads class
     *
     * @param tiles
     */
    FixRoads(Tile[][] tiles){
        this.grid = tiles;
    }

    public void acceptEmpty(Empty empty) {

    }

    /**
     * \brief Accept street
     *
     * Accepts a street to fix. Calls
     * another visitor to check the surrounding
     * tiles and set the street symbol to the
     * corresponding one.
     *
     * @param street
     */
    public void acceptStreet(Street street) {
        //GRADING: NESTED
        IsRoad isRoad = new IsRoad();
        int x = street.getX();
        int y = street.getY();
        int aboveX = x - 1;
        int belowX = x + 1;
        int leftY = y - 1;
        int rightY = y + 1;
        boolean roadAbove = false;
        boolean roadBelow = false;
        boolean roadLeft = false;
        boolean roadRight = false;

        if(aboveX >= 0){
            grid[aboveX][y].accept(isRoad);
            roadAbove = isRoad.getIsRoad();
        }

        if(belowX <= 6){
            grid[belowX][y].accept(isRoad);
            roadBelow = isRoad.getIsRoad();
        }

        if(leftY >= 0){
            grid[x][leftY].accept(isRoad);
            roadLeft = isRoad.getIsRoad();
        }

        if(rightY <= 6){
            grid[x][rightY].accept(isRoad);
            roadRight = isRoad.getIsRoad();
        }

        street.setAdjacencies(roadLeft, roadAbove, roadBelow, roadRight);
    }

    public void acceptGreenspace(Greenspace greenspace) {

    }

    public void acceptWater(Water water) {

    }

    public void acceptBuilding(Building building) {

    }
}
