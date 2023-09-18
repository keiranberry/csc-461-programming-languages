package berry_keiran;

public class Vehicle {
    private int id;
    private int entryTime;
    protected boolean isInLot;

    Vehicle(int id, int entryTime){
        this.id = id;
        this.entryTime = entryTime;
        this.isInLot = true;
    }

    protected int getId(){
        return this.id;
    }

    protected int getEntryTime(){
        return this.entryTime;
    }

    protected Boolean vehicleIsInLot(){
        return this.isInLot;
    }

}
