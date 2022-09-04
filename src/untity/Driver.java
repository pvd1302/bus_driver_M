package untity;

import java.io.Serializable;

public class Driver implements Serializable {
    public static int id = 10000;
    private int driverID;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String level;

    public Driver(int driverID) {
        this.driverID = driverID;
    }

    public Driver() {
        //setReaderID();
    }

    public Driver(String fullName, String address, String phoneNumber, String level) {
        setDriverID();
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID() {
        this.driverID = id++;
    }

    public void setDriverID(int id){
        driverID = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return getClass().getName()+"[driverID= " + driverID + ", fullName= "+ fullName
                +", address= " + address + ", phoneNumber= " + phoneNumber +", trình độ= "+ level + "]";
    }
}
