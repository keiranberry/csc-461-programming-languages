package berry_keiran;

public class Street extends Tile{
    private int adjCode = 0;
    public Street() {
        super('━');
    }

    /**
     * \brief Accept visitor
     *
     * Calls the visitor accept function for the street tile
     *
     * @param visitor
     */
    public void accept(Visitor visitor) {
        visitor.acceptStreet(this);
    }

    /**
     * \brief Indicate the road tiles adjacent to this one
     *
     * The road time image chosen is dependent on the tiles around
     * it. This is where the adjacency of road tiles is indicated.
     *
     * \param left True if road tile to left
     * \param top True if road tile to top
     * \param bot True if road tile to lower left
     * \param right True if road tile to lower right
     */
    public void setAdjacencies(boolean left, boolean top, boolean bot, boolean right)
    {
        // Create the adjacency code
        int code = (left ? 1 : 0) | (top ? 2 : 0) | (bot ? 4 : 0) | (right ? 8 : 0);
        if (adjCode == code)
        {
            // We are already set. Do nothing
            return;
        }

        //unicode starts at 2510
        char symbols[] = {
                '━',      // 0 right
                '━',      // 1 right
                '┃',      // 2 ud
                '┛',      // 3 lu
                '┃',      // 4 ud
                '┓',      // 5 ld
                '┃',      // 6 ud
                '┫',     // 7 lud
                '━',      // 8 right
                '━',      // 9 right
                '┗',      // 10 top
                '┻',     // 11 lur
                '┏',      // 12 dr
                '┳',    // 13 ldr
                '┣',     // 14 udr
                '╋'    // 15 ludr
        };

        // Select the appropriate image
        adjCode = code;

        this.setSymbol(symbols[code]);

    }
}
