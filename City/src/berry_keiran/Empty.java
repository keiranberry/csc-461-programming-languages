package berry_keiran;

public class Empty extends Tile{

    /**
     * \brief Constructor for the empty tile class
     */
    Empty() {
        super('▫');
    }

    /**
     * \brief  Accept visitor for empty space
     *
     * @param visitor
     */
    public void accept(Visitor visitor){
        visitor.acceptEmpty(this);
    }
}
