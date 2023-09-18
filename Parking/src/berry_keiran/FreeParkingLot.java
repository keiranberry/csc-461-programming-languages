package berry_keiran;

public class FreeParkingLot extends ParkingLot{
    FreeParkingLot(String name, int size){
        super(name, size);
    }

    FreeParkingLot(int size){
        super(size);
    }

    @Override
    protected void markVehicleExit(int minutesSinceOpening, int carId){
        super.markVehicleExit(minutesSinceOpening, carId);
    }

    protected void markVehicleExit(int minutesSinceOpening){
        this.markVehicleExit(minutesSinceOpening, 0);
    }

    @Override
    public String toString(){
        return super.buildStatusForParkingLot();
    }

    @Override
    protected Boolean noErrorsMarkingExit(int minutesSinceOpening, int carId){
        return super.checkForErrorsMarkingExit(minutesSinceOpening, carId);
    }

    @Override
    protected void letVehicleExit(int minutesSinceOpening, int carId){
        decrementVehiclesInLot(minutesSinceOpening);
    }
}
