package service;

import untity.BusRoute;
import untity.Driver;

import java.io.Serializable;

public class BusManagement implements Serializable {
    private Driver driver;
    private BusRoute busRoute;
    private int numOfDrive; // so lượt lái
    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public BusRoute getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(BusRoute busRoute) {
        this.busRoute = busRoute;
    }

    public int getNumOfDrive() {
        return numOfDrive;
    }

    public void setNumOfDrive(int numOfDrive) {
        this.numOfDrive = numOfDrive;
    }


    public BusManagement() {
    }

    public BusManagement(Driver driver, BusRoute busRoute, int numOfDrive) {
        this.driver = driver;
        this.busRoute = busRoute;
        this.numOfDrive = numOfDrive;

    }
}
