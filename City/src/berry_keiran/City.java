package berry_keiran;

public class City {

    private Tile[][] tiles = new Tile[7][7];

    /**
     * \brief Constructor for city class
     */
    public City(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                this.tiles[i][j] = new Empty();
                this.tiles[i][j].setX(i);
                this.tiles[i][j].setY(j);
            }
        }
    }

    /**
     * \brief Accept visitor
     *
     * Accepts a visitor for each tile in the city
     *
     * @param visitor
     */
    public void accept(Visitor visitor){
        for(Tile[] tileRow : this.tiles){
            for(Tile tile: tileRow){
                tile.accept(visitor);
            }
        }
    }

    /**
     * \brief Print city
     *
     * Iterates through the city, printing each tile to the console
     *
     */
    public void print(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j ++){
                System.out.print(ColorText.colorString(tiles[i][j].getSymbol(), tiles[i][j].getColor()));
            }
            System.out.print('\n');
        }
    }

    /**
     * \brief Change type of tile
     *
     * Takes in type of tile and coordinates, changes the
     * corresponding tile to the type specified.
     *
     * @param type
     * @param x
     * @param y
     */
    public void changeTile(int type, int x, int y){
        switch(type){
            case 1:
                this.tiles[x][y] = new Greenspace();
                this.tiles[x][y].setX(x);
                this.tiles[x][y].setY(y);
                break;
            case 2:
                this.tiles[x][y] = new Water();
                this.tiles[x][y].setX(x);
                this.tiles[x][y].setY(y);
                break;
            case 3:
                this.tiles[x][y] = new Street();
                this.tiles[x][y].setX(x);
                this.tiles[x][y].setY(y);
                break;
            case 4:
                this.tiles[x][y] = new Building();
                this.tiles[x][y].setX(x);
                this.tiles[x][y].setY(y);
                break;
            case 5:
                this.tiles[x][y] = new Empty();
                this.tiles[x][y].setX(x);
                this.tiles[x][y].setY(y);
        }
    }

    /**
     * \brief Return the tiles array for the city
     *
     * @return 2d array of tiles
     */
    public Tile[][] getTiles(){
        return this.tiles;
    }
}
