package berry_keiran;

public class Water extends Tile{
    Water() {
        super('~');
    }

    /**
     * \brief Accept visitor
     *
     * Calls the visitor accept function for the water tile
     *
     * @param visitor
     */
    public void accept(Visitor visitor) {
        visitor.acceptWater(this);
    }
}
