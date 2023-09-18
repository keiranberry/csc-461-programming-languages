package berry_keiran;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ParkingLot {

    protected static final int CLOSED_THRESHOLD = 80;
    private int nextVehicleId = 0;
    private int timeLotClosed = 0;
    private Boolean lotWasClosed = false;
    private int closedMinutes = 0;
    protected int id;
    private String lotName;
    private int lotSize;
    private double lotFee;
    private int vehiclesInLot;
    private double profit = 0;
    private int recentTime = 0;
    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    //GRADING: CONSTRUCTION
    ParkingLot(String name, int size, double fee){
        this.lotName = name;
        this.lotSize = size;
        this.lotFee = fee;
    }

    ParkingLot(int size){
        this("test", size, 1);
    }

    ParkingLot(int size, double fee){
        this("test", size, fee);
    }

    ParkingLot(String name, int size){
        this(name, size,1);
    }

    protected String getName(){
        return this.lotName;
    }

    protected int markVehicleEntry(int minutesSinceOpening){
        if(this.vehiclesInLot >= this.lotSize){
            return -1;
        }
        if(minutesSinceOpening < this.recentTime){
            return -1;
        }
        Vehicle vehicle = new Vehicle(nextVehicleId, minutesSinceOpening);
        this.vehicles.add(vehicle);
        this.vehiclesInLot++;
        this.nextVehicleId++;

        this.recentTime = minutesSinceOpening;
        handleClosureOnVehicleEntryOrExit(minutesSinceOpening);

        return vehicle.getId();
    }

    protected void markVehicleExit(int minutesSinceOpening, int carId){
        if(!noErrorsMarkingExit(minutesSinceOpening, carId)){
            return;
        }

        letVehicleExit(minutesSinceOpening, carId);
    }

    protected void letVehicleExit(int minutesSinceOpening, int carId){
        int duration = minutesSinceOpening - this.vehicles.get(carId).getEntryTime();
        double parkingCost;
        if(duration > 15){
            double hourlyDuration = (double) duration /60;
            parkingCost = hourlyDuration * this.lotFee;
            this.profit += parkingCost;
        }

        decrementVehiclesInLot(minutesSinceOpening);
        this.vehicles.get(carId).isInLot = false;
    }

    protected void decrementVehiclesInLot(int minutesSinceOpening){
        if(this.vehiclesInLot > 0){
            this.vehiclesInLot--;
        }
        this.recentTime = minutesSinceOpening;
        handleClosureOnVehicleEntryOrExit(minutesSinceOpening);
    }

    protected Boolean noErrorsMarkingExit(int minutesSinceOpening, int carId){
        if(carId < 0){
            return false;
        }

        if(!checkForErrorsMarkingExit(minutesSinceOpening, carId)){
            return false;
        }

        return this.vehicles.get(carId).vehicleIsInLot();
    }

    protected Boolean checkForErrorsMarkingExit(int minutesSinceOpening, int carId){
        return minutesSinceOpening >= this.recentTime;
    }

    protected double getProfit(){
        return this.profit;
    }

    protected int getVehiclesInLot(){
        return this.vehiclesInLot;
    }

    protected boolean isClosed(){
        double percentage = (double) this.vehiclesInLot / this.lotSize;
        return percentage >= ((double) CLOSED_THRESHOLD / 100);
    }

    protected int getClosedMinutes(){
        return this.closedMinutes;
    }

    public String toString(){
        return buildStatusForParkingLot() + buildMoneyCollected();
    }

    protected String buildStatusForParkingLot(){
        DecimalFormat formatter = new DecimalFormat("###.#");
        double percentage = (double) this.vehiclesInLot / this.lotSize;
        String formattedPercentage;
        if(percentage >= ((double) CLOSED_THRESHOLD / 100)){
            formattedPercentage = "CLOSED";
        }
        else {
            formattedPercentage = formatter.format(percentage * 100) + '%';
        }
        return String.format("Status for %s parking lot: %s vehicles (%s)",
                this.lotName, this.vehiclesInLot, formattedPercentage);
    }

    private String buildMoneyCollected(){
        String profit = String.format("%.2f", this.profit);
        return String.format(" Money Collected: $%s", profit);
    }

    private void handleClosureOnVehicleEntryOrExit(int currentTime){
        if(this.isClosed() && !this.lotWasClosed){
            this.timeLotClosed = currentTime;
            this.lotWasClosed = true;
        }
        else if(this.isClosed() && this.lotWasClosed){
            this.closedMinutes += (currentTime - this.timeLotClosed);
            this.timeLotClosed = currentTime;
        }
        else if(this.lotWasClosed){
            this.closedMinutes += (currentTime - this.timeLotClosed);
            this.lotWasClosed = false;
        }
    }
}
