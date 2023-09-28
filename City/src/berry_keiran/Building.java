package berry_keiran;

public class Building extends Tile{

    public Building() {
        super('⌂');
    }

    /**
     * \brief Accept visitor
     *
     * Calls the visitor accept function for the building tile
     *
     * @param visitor
     */
    public void accept(Visitor visitor) {
        visitor.acceptBuilding(this);
    }
}
