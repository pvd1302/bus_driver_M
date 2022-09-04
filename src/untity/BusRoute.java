package untity;

import java.io.Serializable;

public class BusRoute implements Serializable {
    public static int id = 10000;
    private int routeID;
    private double distance;
    private int numofStop;


    public BusRoute() {
        this.routeID = routeID;
    }

    public BusRoute(double distance, int numofStop) {
        //setRouteID();
    }

    public BusRoute(int routeID, double distance, int numofStop) {
        this.routeID = routeID;
        this.distance = distance;
        this.numofStop = numofStop;
    }

    public int getBusRouteID() {
        return routeID;
    }

    public void setBusRouteID() {
        this.routeID = id++;
    }

    public void setBusRouteID(int id){
        routeID = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getNumofStop() {
        return numofStop;
    }

    public void setNumofStop(int numofStop) {
        this.numofStop = numofStop;
    }



    @Override
    public String toString() {
        return getClass().getName()+"[routeID= " + routeID + ", distance= "+ distance
                +", numofStop= " + numofStop + "]";
    }
}
