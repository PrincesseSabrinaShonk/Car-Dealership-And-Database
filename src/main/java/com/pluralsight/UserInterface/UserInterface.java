package com.pluralsight.UserInterface;
import com.pluralsight.models.LeaseContract;
import com.pluralsight.models.SalesContract;
import com.pluralsight.models.Vehicle;
import com.pluralsight.persistance.DealershipDao;
import com.pluralsight.persistance.LeaseContractDao;
import com.pluralsight.persistance.SalesContractDao;
import com.pluralsight.persistance.VehicleDao;

import java.util.List;

public class UserInterface {
    private final VehicleDao vehicleDao = new VehicleDao();
    private final SalesContractDao salesDao = new SalesContractDao();
    private final LeaseContractDao leaseDao = new LeaseContractDao();
    private final DealershipDao dealershipDao = new DealershipDao();

    public void display() {
        int choice;
        do {
            System.out.println("\n----DEALERSHIP----");
            System.out.println("1. View All Vehicle");
            System.out.println("2. Search by Make/Model");
            System.out.println("3. Buy Vehicle");
            System.out.println("4. Lease Vehicle");
            System.out.println("5. View All Sale");
            System.out.println("6. View All Lease");
            System.out.println("7. List All VINs");
            System.out.println("0. Exit");

            choice = ConsoleHelper.promptForInt("Choose an option");

            switch (choice) {
                case 1 -> viewAll();
                case 2 -> searchByMakeModel();
                case 3 -> buyVehicle();
                case 4 -> leaseVehicle();
                case 5 -> viewAllSales();
                case 6 -> viewAllLeases();
                case 7 -> listAllVins();
                case 0 -> System.out.println("Thank you for visiting! Goodbye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    private void viewAll() {
        List<Vehicle> vehicles = vehicleDao.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles available.");
            return;
        }
        System.out.println("\n==== ALL VEHICLES ====");
        vehicles.forEach(this::printVehicle);
        System.out.println("Total vehicles: " + vehicles.size());
    }

    private void searchByMakeModel() {
        String make = ConsoleHelper.promptForString("Enter make");
        String model = ConsoleHelper.promptForString("Enter model");
        List<Vehicle> vehicles = vehicleDao.getByMakeModel(make, model);
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles found for " + make + " " + model);
            return;
        }
        System.out.println("\nFound " + vehicles.size() + " vehicle(s):");
        vehicles.forEach(this::printVehicle);
    }

    private void buyVehicle() {
        System.out.println("\n==== BUY VEHICLE ====");
        String vin = ConsoleHelper.promptForString("Enter VIN to buy").trim();
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if (vehicle == null) {
            System.out.println("Vehicle not found! Use option 7 to see all available VINs.");
            return;
        }
        // Display vehicle details
        System.out.println("\nVehicle Details:");
        printVehicle(vehicle);
        // Confirm purchase
        String confirm = ConsoleHelper.promptForString("\nConfirm purchase? (yes/no)");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Purchase cancelled.");
            return;
        }

        // Calculate total
        double recordingFee = 100;
        double processingFee = 295;
        double total = vehicle.getPrice() + recordingFee + processingFee;

        System.out.println("\n--- Purchase Summary ---");
        System.out.printf("Vehicle Price:    $%.2f%n", vehicle.getPrice());
        System.out.printf("Recording Fee:    $%.2f%n", recordingFee);
        System.out.printf("Processing Fee:   $%.2f%n", processingFee);
        System.out.printf("Total Price:      $%.2f%n", total);
        System.out.println("------------------------");

        SalesContract sc = new SalesContract(vin, vehicle.getPrice(), recordingFee, processingFee);
        salesDao.addSale(sc);
        vehicleDao.deleteVehicle(vin);
        System.out.println("\n✓ Purchase complete! Vehicle has been sold.");
    }

    private void leaseVehicle() {
        System.out.println("\n==== LEASE VEHICLE ====");
        String vin = ConsoleHelper.promptForString("Enter VIN to lease").trim();
        Vehicle vehicle = vehicleDao.getVehicleByVin(vin);

        if (vehicle == null) {
            System.out.println("Vehicle not found! Use option 7 to see all available VINs.");
            return;
        }
        // Display vehicle details
        System.out.println("\nVehicle Details:");
        printVehicle(vehicle);

        // Confirm lease
        String confirm = ConsoleHelper.promptForString("\nConfirm lease? (yes/no)");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Lease cancelled.");
            return;
        }
        // Calculate lease details
        double endingValue = 5000;
        double leaseFee = 300;

        System.out.println("\n--- Lease Summary ---");
        System.out.printf("Vehicle Price:    $%.2f%n", vehicle.getPrice());
        System.out.printf("Ending Value:     $%.2f%n", endingValue);
        System.out.printf("Lease Fee:        $%.2f%n", leaseFee);
        System.out.println("---------------------");
        LeaseContract lc = new LeaseContract(vin, endingValue, leaseFee);
        leaseDao.addLease(lc);
        vehicleDao.deleteVehicle(vin);
        System.out.println("\n✓ Lease complete! Vehicle has been leased.");
    }

    private void listAllVins() {
        List<Vehicle> vehicles = vehicleDao.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("\nNo vehicles available.");
            return;
        }
        System.out.println("\n==== ALL AVAILABLE VINs ====");
        vehicles.forEach(v ->
                System.out.printf("%-18s - %d %s %s ($%.2f)%n",
                        v.getVin(),
                        v.getYear(),
                        v.getMake(),
                        v.getModel(),
                        v.getPrice())
        );
        System.out.println("Total vehicles: " + vehicles.size());
    }

    private void printVehicle(Vehicle v) {
        System.out.printf("VIN: %-18s | %d %s %s | %s | %,d miles | $%,.2f%n",
                v.getVin(),
                v.getYear(),
                v.getMake(),
                v.getModel(),
                v.getColor(),
                v.getOdometer(),
                v.getPrice()
        );
    }

    private void viewAllSales() {
        System.out.println("\n==== ALL SALES ====");
        List<SalesContract> sales = salesDao.getAllSales();
        if (sales.isEmpty()) {
            System.out.println("No sales recorded.");
            return;
        }
        System.out.println("Total sales: " + sales.size());
        System.out.println();
        int count = 1;
        for (SalesContract s : sales) {
            System.out.println("Sale #" + count++);
            System.out.printf("  VIN:            %s%n", s.getVin());
            System.out.printf("  Sales Price:    $%,.2f%n", s.getSalesPrice());
            System.out.printf("  Recording Fee:  $%.2f%n", s.getRecordingFee());
            System.out.printf("  Processing Fee: $%.2f%n", s.getProcessingFee());
            System.out.printf("  Total:          $%,.2f%n", s.getTotalPrice());
            System.out.println();
        }
    }

    private void viewAllLeases() {
        System.out.println("\n==== ALL LEASES ====");
        List<LeaseContract> leases = leaseDao.getAllLeases();
        if (leases.isEmpty()) {
            System.out.println("No leases recorded.");
            return;
        }
        System.out.println("Total leases: " + leases.size());
        System.out.println();
        int count = 1;
        for (LeaseContract l : leases) {
            System.out.println("Lease #" + count++);
            System.out.printf("  VIN:           %s%n", l.getVin());
            System.out.printf("  Ending Value:  $%,.2f%n", l.getEndingValue());
            System.out.printf("  Lease Fee:     $%.2f%n", l.getLeaseFee());
            System.out.println();
        }
    }
}