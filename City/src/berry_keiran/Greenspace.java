package berry_keiran;

public class Greenspace extends Tile{

    public Greenspace() {
        super('░');
    }

    /**
     * \brief Accept visitor
     *
     * Calls the visitor accept function for the greenspace tile
     *
     * @param visitor
     */
    public void accept(Visitor visitor) {
        visitor.acceptGreenspace(this);
    }
}
