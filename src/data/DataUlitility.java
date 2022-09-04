package data;
import untity.BusRoute;
import service.BusManagement;
import untity.Driver;
import java.io.*;
import java.util.Scanner;

/**
 * Lớp này để thao tác với file: thực hiện đọc ghi file
 */
public class DataUlitility {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;

    private Scanner scanner;


    public static int countLines(String filename) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void openConnectionToRead(String fileName){
        try {
            File mFile = new File(fileName);
            if(!mFile.exists()){
                mFile.createNewFile();
            }
            scanner = new Scanner(mFile, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnectionToRead(){
        scanner.close();
    }

    public void closeConnectionToWrite() {
        printWriter.close();
        try {
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openConnectionToWrite(String fileName) {
        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRouteToFile(BusRoute busRoute, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(busRoute.getBusRouteID() + "|" + busRoute.getDistance() + "|"
                );
        closeConnectionToWrite();
    }

    public BusRoute[] readAllRouteFromFile(String fileName) {
        openConnectionToRead(fileName);
        BusRoute[] routes = new BusRoute[countLines(fileName)];
        int i = 0;
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            routes[i++] = creaRouteFromData(data);
        }
        closeConnectionToRead();
        return routes;
    }

    private BusRoute creaRouteFromData(String data) {
        BusRoute busRoute = new BusRoute();
        String[] fields = data.split("\\|");

        busRoute.setBusRouteID(Integer.parseInt(fields[0]));
        busRoute.setDistance(Double.parseDouble(fields[1]));
        busRoute.setNumofStop(Integer.parseInt(fields[0]));


        return busRoute;
    }

    public void writeDriverToFile(Driver driver, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(driver.getDriverID() + "|" + driver.getFullName() + "|"
                + driver.getAddress() + "|" + driver.getPhoneNumber() + "|" + driver.getLevel());
        closeConnectionToWrite();
    }

    public Driver[] readAllDriverFromFile(String fileName) {
        openConnectionToRead(fileName);
        Driver[] drivers = new Driver[countLines(fileName)];
        int i = 0;
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Driver driver = createDriverFromData(data);
            drivers[i++] = driver;
        }
        closeConnectionToRead();
        return drivers;
    }

    private Driver createDriverFromData(String data) {
        String[] datas = data.split("\\|");

        Driver driver = new Driver();
        driver.setDriverID(Integer.parseInt(datas[0]));
        driver.setFullName(datas[1]);
        driver.setAddress(datas[2]);
        driver.setPhoneNumber(datas[3]);
        driver.setLevel(datas[4]);

        return driver;
    }

    public void writeBMToFile(BusManagement bm, String fileName) {
        openConnectionToWrite(fileName);
        printWriter.println(bm.getDriver().getDriverID() + "|" + bm.getBusRoute().getBusRouteID()
                + "|" + bm.getNumOfDrive() + "|" );
        closeConnectionToWrite();
    }

    public BusManagement[] readBMFromFile(String fileName) {
        openConnectionToRead(fileName);
        BusManagement[] bms = new BusManagement[countLines(fileName)];
        int i = 0;
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            BusManagement x = createBMFromData(data);
            bms[i++] = x;
        }
        closeConnectionToRead();
        return bms;
    }

    private BusManagement createBMFromData(String data) {
        BusManagement x = new BusManagement();
        String[] fields = data.split("\\|");

        x.setDriver(new Driver(Integer.parseInt(fields[0])));
        x.setBusRoute(new BusRoute());
        x.setNumOfDrive(Integer.parseInt(fields[2]));
        return x;
    }
}
