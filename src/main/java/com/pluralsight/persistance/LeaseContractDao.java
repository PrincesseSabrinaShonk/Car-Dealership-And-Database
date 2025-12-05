package com.pluralsight.persistance;

import com.pluralsight.models.DataSource;
import com.pluralsight.models.LeaseContract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaseContractDao {
    public void addLease(LeaseContract contract) {
        String sql = "INSERT INTO lease_contracts (vin, ending_value, lease_fee) VALUES (?, ?, ?)";


        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, contract.getVin());
            ps.setDouble(2, contract.getEndingValue());
            ps.setDouble(3, contract.getLeaseFee());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
    public List<LeaseContract> getAllLeases() {
        List<LeaseContract> leases = new ArrayList<>();
        String sql = "SELECT * FROM lease_contracts"; // adjust table name

        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                leases.add(new LeaseContract(
                        rs.getString("vin"),
                        rs.getDouble("ending_value"),
                        rs.getDouble("lease_fee")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leases;
    }
}
