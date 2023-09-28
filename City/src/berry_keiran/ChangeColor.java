package berry_keiran;

public class ChangeColor implements Visitor{

    private ColorText.Color color = ColorText.Color.BLACK;
    private int type;

    /**
     * \brief Constructor for the ChangeColor visitor
     *
     * @param color
     * @param type
     */
    ChangeColor(ColorText.Color color, int type){
        this.color = color;
        this.type = type;
    }

    /**
     * \brief Accept empty tile
     *
     * Does nothing, empty spaces do not change color
     *
     * @param empty
     */
    public void acceptEmpty(Empty empty){
    }

    /**
     * \brief Accept street tile
     *
     * Changes color of street tile
     *
     * @param street
     */
    public void acceptStreet(Street street){
        if(this.type == 2){
            street.changeColor(color);
        }
    }

    /**
     * \brief Accept greenspace tile
     *
     * Changes color of greenspace tile
     *
     * @param greenspace
     */
    public void acceptGreenspace(Greenspace greenspace){
        if(this.type == 3){
            greenspace.changeColor(color);
        }
    }

    /**
     * \brief Accept water tile
     *
     * Change color of water tile
     *
     * @param water
     */
    public void acceptWater(Water water){
        if(this.type == 3){
            water.changeColor(color);
        }
    }

    /**
     * \brief Accept building tile
     *
     * Change color of building tile
     *
     * @param building
     */
    public void acceptBuilding(Building building){
        if(this.type == 1){
            building.changeColor(color);
        }
    }
}
