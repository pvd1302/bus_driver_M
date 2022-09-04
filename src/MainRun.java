import service.serviceUltility;
import data.DataUlitility;
import untity.BusRoute;
import service.BusManagement;
import untity.Driver;

import java.util.Scanner;


/**@author
 * Lớp này có chức năng hiển thị và nhận 2tương tác từ người dùng.
 */

public class MainRun {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isActivate = false;

    public static void main(String[] args) {
        int choice = 0;

        DataUlitility dataUlitility = new DataUlitility();
        serviceUltility serviceUltility = new serviceUltility();

        Driver[] drivers = null;
        BusRoute[] busRoute = null;
        BusManagement[] managerments = null;

        do {
            System.out.println("\n____________XIN MỜI CHỌN MỘT CHỨC NĂNG____________");
            System.out.println("1. Thêm lái xe vào file.");
            System.out.println("2. Hiển thị danh sách lái xe.");
            System.out.println("3. Thêm 1 tuyến  vào file.");
            System.out.println("4. Hiển thị danh sách các tuyến.");
            System.out.println("5. Nhập thông tin Quản lý lái xe.");
            System.out.println("6. Sắp xếp thông tin lái xe.");
            System.out.println("7. Tìm kiếm thông tin lái theo tên.");
            System.out.println("8. Thoát chương trình.");
            System.out.println("Chọn chức năng: ");

            choice = scanner.nextInt();
            scanner.nextLine();// doc bo dong chua lua chon

            switch (choice) {
                case 8:
                    System.out.println("____________Thoát khỏi chương trình____________");
                    System.out.println("Cảm ơn quý vị đã sử dụng dịch vụ của chúng tôi!");
                    break;

                case 1:
                    if(!isActivate){
                        updateId(dataUlitility);
                    }
                    getDriverInfo(dataUlitility);
                    break;

                case 2:
                    drivers = readDriverInfo(dataUlitility, "DRIVER.DAT");
                    showDriverInfo(drivers);
                    break;

                case 3:
                    getBusRouteInfo(dataUlitility);
                    break;

                case 4:
                    busRoute = readRouteInfo(dataUlitility, "ROUTE.DAT");
                    showRouteInfo(busRoute);
                    break;

                case 5:
                    int driverId;
                    int busRouteId;
                    Driver driver = null;
                    BusRoute busRoute1 = null;
                    if (busRoute == null) {
                        busRoute = readRouteInfo(dataUlitility, "ROUTE.DAT");
                    }
                    if (drivers == null) {
                        drivers = readDriverInfo(dataUlitility, "DRIVER.DAT");
                    }
                    managerments = dataUlitility.readBMFromFile("BM.DAT");
                    managerments = update(serviceUltility, managerments, dataUlitility);
                    boolean done = false;
                    do {
                        showDriverInfo(drivers);
                        System.out.println("Nhập mã lái xe cần lập thông tin quản lý: ");
                        driverId = scanner.nextInt();
                        driver = findDriverByID(drivers, driverId);
                        if (driver != null) {
                            done = true;
                        }

                        if (done) {
                            break;
                        }
                    } while (true);

                    done = isDriveable(managerments, driverId);
                    if (done) {
                        System.out.println("Danh sách tuyến đã lái: ");
                        showDrivedInfo(managerments, driverId);

                        showRouteInfo(busRoute);
                        do {
                            System.out.println("Nhập mã tuyến: ");
                            busRouteId = scanner.nextInt();
                            busRoute1 = findRouteById(busRoute, busRouteId);
                            if (busRoute1 != null) {
                                done = true;
                            }
                            if (done) {
                                done = isNotFull(busRouteId, driverId, managerments);
                            }
                        } while (!done);

                        int numOfDriverdRoute = 0;
                        int drived = countDrived(busRouteId, driverId, managerments);
                        do {
                            System.out.println("Bạn đã lái " + drived + " tuyến.");
                            System.out.println("Nhập số lượng tuyến đã lái, tối đa " + (15 - drived) + " tuyến: ");
                            numOfDriverdRoute = scanner.nextInt();
                            numOfDriverdRoute += drived;
                            if (numOfDriverdRoute > 0 && numOfDriverdRoute <= 15) {
                                break;
                            }
                        } while (true);
                        scanner.nextLine();

                        BusManagement b = new BusManagement(driver, busRoute1,
                                numOfDriverdRoute);
                        dataUlitility.writeBMToFile(b, "BM.DAT");
                    } else {
                        System.out.println("Lái xe đã lái đủ  số lượng cho phép!");
                    }
                    managerments = readBMInfo(dataUlitility);
                    managerments = update(serviceUltility, managerments, dataUlitility);
                    showBMInfo(managerments);

                    break;

                case 6:
                    managerments = readBMInfo(dataUlitility);
                    managerments = update(serviceUltility, managerments, dataUlitility);
                    int sortChoice = 0;
                    do {
                        System.out.println("___________Lựa chọn sắp xếp___________");
                        System.out.println("1. Sắp xếp theo tên lái xe.");
                        System.out.println("2. Sắp xếp theo tổng số lượng lái.");
                        System.out.println("0. Thoát khỏi chức năng này.");
                        System.out.println("Chọn chức năng: ");
                        sortChoice = scanner.nextInt();
                        switch (sortChoice) {
                            case 0:
                                break;
                            case 1:
                                managerments = sortByName(managerments);
                                showBMInfo(managerments);
                                break;
                            case 2:
                                managerments = sortByNumofDrive(managerments);
                                showBMInfo(managerments);
                                break;
                        }
                    } while (sortChoice != 0);

                    break;

                case 7:
                    managerments = readBMInfo(dataUlitility);
                    managerments = update(serviceUltility, managerments, dataUlitility);

                    System.out.println("Nhập tên của lái xe cần tìm: ");
                    String key = scanner.nextLine();
                    BusManagement[] result = serviceUltility.findByName(managerments, key);
                    showBMInfo(result);
                    break;

                default:
                    System.out.println("Vui lòng chọn lại!");
            }
        } while (choice != 0);
    }

    private static void updateId(DataUlitility dataUlitility) {
        Driver[] drivers = readDriverInfo(dataUlitility, "DRIVER.DAT");
        if(drivers.length > 0){
            Driver.id = drivers[drivers.length - 1].getDriverID()+1;
        }

        BusRoute[] busRoutes = readRouteInfo(dataUlitility, "ROUTE.DAT");
        if(busRoutes.length > 0){
            BusRoute.id = busRoutes[busRoutes.length-1].getBusRouteID()+1;
        }

        isActivate = true;
    }

    private static BusManagement[] update(serviceUltility serviceUltility,
                                          BusManagement[] managerments,
                                          DataUlitility dataUlitility) {
        Driver[] drivers = readDriverInfo(dataUlitility, "DRIVER.DAT");
        BusRoute[] busRoutes = readRouteInfo(dataUlitility, "ROUTE.DAT");
        return serviceUltility.updateBRMInfo(managerments, drivers, busRoutes);
    }

    private static BusManagement[] readBMInfo(DataUlitility dataUlitility) {
        return dataUlitility.readBMFromFile("BM.DAT");
    }

    private static BusManagement[] sortByNumofDrive(BusManagement[] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                if (m[i].getTotal() > m[j].getTotal()) {
                    BusManagement x = m[i];
                    m[i] = m[j];
                    m[j] = x;
                }
            }
        }
        return m;
    }

    private static void showBMInfo(BusManagement[] m) {
        System.out.println("____________Thông tin quản lý lái xe____________");
        System.out.printf("%-10s%-20s%-15s%-20s%-15s%-10s%-15s\n", "Mã lái xe",
                "Họ tên", "mã tuyến", "khoảng cách", "số điểm dừng");
        for (int i = 0; i < m.length; i++) {
            if (m[i] != null) {
                System.out.printf("%-10d%-20s%-15d%-20s%-15d%-10s%-15d\n", m[i].getDriver().getDriverID(),
                        m[i].getDriver().getFullName(),
                        m[i].getBusRoute().getBusRouteID(), m[i].getBusRoute().getDistance(),
                        m[i].getNumOfDrive(), m[i].getTotal());
            }
        }

    }

    private static void showBMPersonalInfo(BusManagement[] m, int persionId) {
        System.out.println("____________Thông tin tuyến xe của lái xe đã lái mã " + persionId + "____________");
        System.out.printf("%10s%10s%10s%-10s\n", "Mã lái xe", "Mã tuyến", "số lượng", "tổng");
        for (int i = 0; i < m.length; i++) {
            if (m[i].getDriver().getDriverID() == persionId) {
                System.out.printf("%10d%10d%10d%-10s\n", m[i].getDriver().getDriverID(),
                        m[i].getBusRoute().getBusRouteID(), m[i].getNumOfDrive(), m[i].getTotal());
            }
        }

    }

    private static BusManagement[] sortByName(BusManagement[] managerments) {
        for (int i = 0; i < managerments.length-1; i++) {
            for (int j = managerments.length-1; j > i; j--) {
                if (check(managerments[j].getDriver().getFullName(),
                        managerments[j-1].getDriver().getFullName())) {
                    BusManagement tmp = managerments[j];
                    managerments[j] = managerments[j-1];
                    managerments[j-1] = tmp;
                }
            }
        }
        return managerments;
    }

    private static boolean check(String name1, String name2) {
        String[] items1 = name1.split(" ");
        String[] items2 = name2.split(" ");
        if (items1[items1.length - 1].compareTo(items2[items2.length - 1]) < 0) {
            return true;
        }
        return false;
    }

    private static int countDrived(int busRouteID, int driverId, BusManagement[] m) {
        for (int i = 0; m != null && i < m.length && m[i] != null; i++) {
            if (m[i].getDriver().getDriverID() == driverId &&
                    m[i].getBusRoute().getBusRouteID() == busRouteID) {
                return m[i].getNumOfDrive();
            }
        }
        return 0;
    }

    private static boolean isNotFull(int busRouteID, int driverId, BusManagement[] m) {
        for (int i = 0; i < m.length; i++) {
            if ((m[i].getDriver().getDriverID() == driverId &&
                    m[i].getBusRoute().getBusRouteID() == busRouteID
                    && m[i].getNumOfDrive() == 15)){
                return false;
            }
        }
        return true;
    }

    private static void showDrivedInfo(BusManagement[] b, int driverId) {
        System.out.println("Mã tuyến \t Số lượt lái");
        for (int i = 0; i < b.length; i++) {
            if (b[i].getDriver().getDriverID() == driverId) {
                System.out.println(b[i].getBusRoute().getBusRouteID() + "\t" + b[i].getNumOfDrive());
            }
        }
        System.out.println("\nMỗi lái xe chỉ được lái tối đã 15 chuyến!");
    }

    private static boolean isDriveable(BusManagement[] managerments, int driverId) {
        int count = 0;
        int total = 0;
        for (int i = 0; i < managerments.length; i++) {
            if (managerments[i].getDriver().getDriverID() ==
                    driverId) {
                count++;
                total += managerments[i].getNumOfDrive();
            }
        }
        if (count <= 15 && total <= 15)
            return true;
        else
            return false;
    }

    private static BusRoute findRouteById(BusRoute[] routes, int id) {
        for (int i = 0; i < routes.length; i++) {
            if (routes[i].getBusRouteID() == id) {
                return routes[i];
            }
        }
        return null;
    }

    private static Driver findDriverByID(Driver[] drivers, int driverId) {
        for (int i = 0; i < drivers.length; i++) {
            if (drivers[i].getDriverID() == driverId) {
                return drivers[i];
            }
        }
        return null;
    }

    private static void showRouteInfo(BusRoute[] routes) {
        System.out.println("____________Thông tin tuyến  trong file____________");
        if (routes[0] == null) {
            System.out.println("Danh sách rỗng!");
        } else {
            for (int i = 0; i < routes.length; i++) {
                if (routes[i] != null) {
                    System.out.println(routes[i]);
                }
            }
        }

    }

    private static void getBusRouteInfo(DataUlitility dataUlitility) {
        System.out.println("____________Nhập thông tin tuyến____________");


        double Distance;
        int NumofStop;

        System.out.println("Nhập khoảng cách: ");
        Distance = scanner.nextDouble();
        System.out.println("Nhập số điểm dừng: ");
        NumofStop = scanner.nextInt();


        BusRoute busRoute = new BusRoute(Distance, NumofStop);
        dataUlitility.writeRouteToFile(busRoute, "ROUTE.DAT");
    }

    private static void showDriverInfo(Driver[] drivers) {
        System.out.println("____________Thông tin Lái xe trong file____________");
        if (drivers[0] == null) {
            System.out.println("Danh sách rỗng!");
        } else {
            for (int i = 0; i < drivers.length; i++) {
                if (drivers[i] != null) {
                    System.out.println(drivers[i]);
                }
            }
        }
    }

    private static void getDriverInfo(DataUlitility dataUlitility) {
        System.out.println("____________Nhập thông tin Lái xe____________");
        String name, address, phoneNum, level;

        System.out.println("Nhập họ và tên: ");
        name = scanner.nextLine();

        System.out.println("Nhập địa chỉ: ");
        address = scanner.nextLine();

        System.out.println("Nhập số điện thoại: ");
        phoneNum = scanner.nextLine();

        System.out.println("Nhập trình độ: ");
        level = scanner.nextLine();


        Driver driver = new Driver(name, address, phoneNum, level);
        dataUlitility.writeDriverToFile(driver, "DRIVER.DAT");
    }

    private static Driver[] readDriverInfo(DataUlitility dataUlitility, String s) {
        return dataUlitility.readAllDriverFromFile(s);
    }

    private static BusRoute[] readRouteInfo(DataUlitility dataUlitility, String s) {
        return dataUlitility.readAllRouteFromFile(s);
    }
}
