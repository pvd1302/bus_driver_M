package service;

import data.DataUlitility;
import untity.BusRoute;
import untity.Driver;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 Lớp này dùng để thực hiện các chức năng như tìm kiếm, cập nhật, chỉnh sửa, xóa, sắp xếp
 */
public class serviceUltility {
    private DataUlitility ulitility;

    public serviceUltility() {
        this.ulitility = new DataUlitility();
    }

    public void sortByName(BusManagement[] args) {
        Arrays.sort(args);
    }

    public void sortByNumofDrive(BusManagement[] args) {
        Arrays.sort(args);
    }

    public BusManagement[] findByName(BusManagement[] drivers, String key) {
        String regex = ".*" + key.toLowerCase() + ".*";
        BusManagement[] x = new BusManagement[1000];
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        int count = 0;
        for (int i = 0; i < drivers.length; i++) {
            String name = drivers[i].getDriver().getFullName().toLowerCase();
            if (pattern.matcher(name).find()) {
                boolean isExist = false;
                for (int j = 0; j < count; j++) {
                    if (x[j].getDriver().getDriverID() == drivers[i].getDriver().getDriverID()) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    x[count++] = drivers[i];
                }
            }
        }

        return x;
    }

    public BusManagement[] updateBRMInfo(BusManagement[] bm,
                                                 Driver[] drivers, BusRoute[] busRoutes) {
        for (int i = 0; i < bm.length; i++) {
            int routeId = bm[i].getBusRoute().getBusRouteID();
            int driverId = bm[i].getDriver().getDriverID();
            int total = countRouteByDriver(bm, driverId);
            bm[i].setDriver(findReaderByID(drivers, driverId));
            bm[i].setBusRoute(findRouteById(busRoutes, routeId));
            bm = setTotalRouteDrive(bm, driverId, total);
        }
        return bm;
    }

    public int countRouteByDriver(BusManagement[] bm, int driverID) {
        int count = 0;
        for (int i = 0; i < bm.length; i++) {
            if (bm[i].getDriver().getDriverID() == driverID) {
                count += bm[i].getNumOfDrive();
            }
        }
        return count;
    }

    public BusManagement[] setTotalRouteDrive(BusManagement[] bm, int driverID, int total) {

        for (int i = 0; i < bm.length; i++) {
            if (driverID == bm[i].getDriver().getDriverID()) {
                bm[i].setTotal(total);
            }
        }
        return bm;
    }

    private static BusRoute findRouteById(BusRoute[] routes, int id) {
        for (int i = 0; routes != null && i < routes.length && routes[i] != null; i++) {
            if (routes[i].getBusRouteID() == id) {
                return routes[i];
            }
        }
        return null;
    }

    private static Driver findReaderByID(Driver[] drivers, int driverId) {
        for (int i = 0; i < drivers.length; i++) {
            if (drivers[i].getDriverID() == driverId) {
                return drivers[i];
            }
        }
        return null;
    }
}
