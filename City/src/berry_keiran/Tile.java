package berry_keiran;

public abstract class Tile {
    private char symbol;
    private ColorText.Color color = ColorText.Color.BLACK;

    private int x;
    private int y;

    Tile(char symbol){
        this.symbol = symbol;
    }

    public abstract void accept(Visitor visitor);

    public char getSymbol(){
        return this.symbol;
    }

    public ColorText.Color getColor(){
        return this.color;
    }

    public void changeColor(ColorText.Color color) {
        this.color = color;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }
}
