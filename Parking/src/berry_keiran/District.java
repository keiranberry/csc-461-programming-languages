/*Additional OOP  requirements
        toString properly extended				____
        Constructors properly handled			____
        Access properly handled (code style requirement)	____

        Last tier completed: _______*/
package berry_keiran;

import java.util.ArrayList;

/**
 * <p>
 * The ParkingLot class contains functions which can manage a paid parking
 * lot, including how full it is, how much money has been made, and which
 * cars are still on the lot. The ParkingLot class can also produce a
 * string containing information on the status of the lot and the
 * profits made.
 *</p>
 * <p>
 * The FreeParkingLot class extends the ParkingLot class, but has no
 * functionality for measuring profits. Because of this, the status string
 * does not contain any profit information.
 *</p>
 * <p>
 * The District class manages ParkingLots and FreeParkingLots, using an
 * ArrayList of parking lots. The district class can return information about
 * the entire district of parking lots, and track additional useful information
 * such as how often all the lots are full. The informational string for the
 * District class gives information on each parking lot in the district.
 * </p>
 *
 * @author Keiran Berry
 */
public class District {
    ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>();
    Boolean districtWasClosed = false;
    int timeDistrictClosed;
    int closedMinutes = 0;

    public int add(ParkingLot parkingLot){
        parkingLots.add(parkingLot);
        parkingLot.id = parkingLots.size() - 1;
        return parkingLot.id;
    }

    public int getVehiclesParkedInDistrict(){
        int totalVehicles = 0;
        for(int i = 0; i < parkingLots.size(); i++){
            totalVehicles += parkingLots.get(i).getVehiclesInLot();
        }
        return totalVehicles;
    }

    public int markVehicleEntry(int parkingLotId, int minutesSinceOpening){

        int output = parkingLots.get(parkingLotId).markVehicleEntry(minutesSinceOpening);
        checkClosure(minutesSinceOpening);
        return output;
    }

    public void markVehicleExit(int parkingLotId, int minutesSinceOpening, int carId){
        parkingLots.get(parkingLotId).markVehicleExit(minutesSinceOpening, carId);
        checkClosure(minutesSinceOpening);
    }

    public Boolean isClosed(){
        Boolean anyOpen = false;
        for(int i = 0; i < this.parkingLots.size(); i++)
        {
            if(!this.parkingLots.get(i).isClosed()){
                anyOpen = true;
            }
        }
        return !anyOpen;
    }

    public ParkingLot getLot(int index){
        return parkingLots.get(index);
    }

    public int getClosedMinutes(){
        return closedMinutes;
    }

    public double getTotalMoneyCollected(){
        double profit = 0;
        for (ParkingLot parkingLot : parkingLots) {
            profit += parkingLot.getProfit();
        }

        return profit;
    }

    @Override
    public String toString(){
        String output = "District status:\n";
        for (ParkingLot parkingLot : parkingLots) {
            output += parkingLot.toString() + '\n';
        }

        return output;
    }

    private void checkClosure(int currentTime){
        if(this.isClosed() && !this.districtWasClosed){
            this.timeDistrictClosed = currentTime;
            this.districtWasClosed = true;
        }
        else if(this.isClosed() && this.districtWasClosed){
            this.closedMinutes += (currentTime - this.timeDistrictClosed);
            this.timeDistrictClosed = currentTime;
        }
        else if(this.districtWasClosed){
            this.closedMinutes += (currentTime - this.timeDistrictClosed);
            this.districtWasClosed = false;
        }
    }
}
