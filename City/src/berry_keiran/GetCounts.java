package berry_keiran;

public class GetCounts implements Visitor{

    private int countEmpty = 0;
    private int countGreenspace = 0;
    private int countWater = 0;
    private int countRoads = 0;
    private int countBuilding = 0;
    public void acceptEmpty(Empty empty){
        this.countEmpty++;
    }

    public void acceptStreet(Street street){
        this.countRoads++;
    }

    public void acceptGreenspace(Greenspace greenspace){
        this.countGreenspace++;
    }

    public void acceptWater(Water water){
        this.countWater++;
    }

    public void acceptBuilding(Building building){
        this.countBuilding++;
    }

    public int getCountEmpty(){
        return this.countEmpty;
    }

    public int getCountGreenspace(){
        return this.countGreenspace;
    }

    public int getCountWater(){
        return this.countWater;
    }

    public int getCountRoads(){
        return this.countRoads;
    }

    public int getCountBuilding(){
        return this.countBuilding;
    }
}
