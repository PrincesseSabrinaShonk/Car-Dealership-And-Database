package com.pluralsight.persistance;

import com.pluralsight.models.DataSource;
import com.pluralsight.models.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {

    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("dealership_id"),
                rs.getString("vin"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("color"),
                rs.getInt("odometer"),
                rs.getDouble("price"),
                rs.getString("vehicle_type")
        );
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = """
            SELECT v.*, i.dealership_id 
            FROM vehicles v 
            JOIN inventory i ON v.VIN = i.VIN
        """;

        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vehicles.add(mapResultSetToVehicle(rs));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return vehicles;
    }

    public List<Vehicle> getByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = """
            SELECT v.*, i.dealership_id 
            FROM vehicles v 
            JOIN inventory i ON v.VIN = i.VIN
            WHERE make = ? AND model = ?
        """;

        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, make);
            ps.setString(2, model);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vehicles.add(mapResultSetToVehicle(rs));
            }

        } catch (Exception e) { e.printStackTrace(); }

        return vehicles;
    }

    public void deleteVehicle(String vin) {
        String sql = "DELETE FROM vehicles WHERE vin = ?";
        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ps.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }
    public Vehicle getVehicleByVin(String vin) {
        String sql = """
        SELECT v.*, i.dealership_id 
        FROM vehicles v 
        JOIN inventory i ON v.VIN = i.VIN
        WHERE v.VIN = ?
    """;
        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToVehicle(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}