package berry_keiran;

public class Rezone implements Visitor{
    private City city;
    Rezone(City c){
        this.city = c;
    }

    /**
     * \brief Changes empty tile to greenspace
     *
     * @param empty
     */
    public void acceptEmpty(Empty empty) {
        this.city.changeTile(1, empty.getX(), empty.getY());
    }

    public void acceptStreet(Street street) {
    }

    public void acceptGreenspace(Greenspace greenspace) {
    }

    public void acceptWater(Water water) {
    }

    public void acceptBuilding(Building building) {
    }
}
