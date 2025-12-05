package com.pluralsight.UserInterface;
import com.pluralsight.models.DataSource;
import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;
import com.pluralsight.models.Vehicle;
import com.pluralsight.persistance.DealershipDao;
import com.pluralsight.persistance.LeaseContractDao;
import com.pluralsight.persistance.SalesContractDao;
import com.pluralsight.persistance.VehicleDao;

public class UserInterface {
    private final VehicleDao vehicleDao = new VehicleDao();
    private final SalesContractDao salesDao = new SalesContractDao();
    private final LeaseContractDao leaseDao = new LeaseContractDao();
    private final DealershipDao dealershipDao = new DealershipDao();

    public void display() {
        int choice;
        do {
            System.out.println("----DEALERSHIP----");
            System.out.println("1. View All Vehicle");
            System.out.println("2. Search by Make/Model");
            System.out.println("3. Buy Vehicle");
            System.out.println("4. Lease Vehicle");
            System.out.println("5. View All Sale");
            System.out.println("6. View All Lease");
            System.out.println("0. Exit");

            choice = ConsoleHelper.promptForInt("Choose an option");

            switch (choice) {
                case 1 -> viewAll();
                case 2 -> searchByMakeModel();
                case 3 -> buyVehicle();
                case 4 -> leaseVehicle();
                case 5 -> viewAllSales();
                case 6 -> viewAllLeases();
            }

        } while (choice != 0);
    }

    private void viewAll() {
        vehicleDao.getAllVehicles().forEach(this::printVehicle);
    }

    private void searchByMakeModel() {
        String make = ConsoleHelper.promptForString("Enter make");
        String model = ConsoleHelper.promptForString("Enter model");
        vehicleDao.getByMakeModel(make, model).forEach(this::printVehicle);
    }

    private void buyVehicle() {
        String vin = ConsoleHelper.promptForString("Enter VIN to buy").trim();
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }
        SalesContract sc = new SalesContract(vin, vehicle.getPrice(), 100, 295);
        salesDao.addSale(sc);
        vehicleDao.deleteVehicle(vin);
        System.out.println("Purchase complete!");
    }

    private void leaseVehicle() {
        String vin = ConsoleHelper.promptForString("Enter VIN to lease").trim();
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }
        LeaseContract lc = new LeaseContract(vin, 5000, 300);
        leaseDao.addLease(lc);
        vehicleDao.deleteVehicle(vin);
        System.out.println("Lease complete!");
    }
    private void printVehicle(Vehicle v) {
        System.out.printf(
                "VIN: %s | Make: %s | Model: %s | Year: %d | Price: $%.2f%n",
                v.getVin(),
                v.getMake(),
                v.getModel(),
                v.getYear(),
                v.getPrice()
        );
    }
    private void viewAllSales() {
        System.out.println("==== ALL SALES ====");
        salesDao.getAllSales().forEach(s ->
                System.out.printf(
                        "Vehicle VIN: %s | Recording Fee: $%.2f | Processing Fee: $%.2f | Total: $%.2f%n",
                        s.getVin(), s.getRecordingFee(), s.getProcessingFee(), s.getTotalPrice()
                )
        );
    }
    private void viewAllLeases() {
        System.out.println("==== ALL LEASES ====");
        leaseDao.getAllLeases().forEach(l ->
                System.out.printf(
                        "Vehicle VIN: %s | Ending Value: $%.2f | Lease Fee: $%.2f%n",
                        l.getVin(), l.getEndingValue(), l.getLeaseFee()
                )
        );
    }
}