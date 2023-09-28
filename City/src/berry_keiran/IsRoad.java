package berry_keiran;

public class IsRoad implements Visitor{

    private Boolean isRoad = false;

    public void acceptEmpty(Empty empty) {
        this.isRoad = false;
    }

    /**
     * \brief Checks if the tile is a street
     *
     * This is the only function that will return true
     * in this visitor
     *
     * @param street
     */
    public void acceptStreet(Street street) {
        this.isRoad = true;
    }

    public void acceptGreenspace(Greenspace greenspace) {
        this.isRoad = false;
    }

    public void acceptWater(Water water) {
        this.isRoad = false;
    }

    public void acceptBuilding(Building building) {
        this.isRoad = false;
    }

    public Boolean getIsRoad(){
        return this.isRoad;
    }
}
