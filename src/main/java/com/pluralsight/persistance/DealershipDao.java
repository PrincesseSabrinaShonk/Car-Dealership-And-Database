package com.pluralsight.persistance;

import com.pluralsight.models.DataSource;
import com.pluralsight.models.Dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DealershipDao {

    public Dealership getDealership() {   // Retrieves the first dealership from the database
        String sql = "SELECT * FROM dealerships LIMIT 1";    // SQL query to get the first dealership record

        // Try-with-resources ensures proper closing of database resources
        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {


            if (rs.next()) {  // Check if a result was found
                return new Dealership(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;  // Return null if no dealership found or error occurred
    }
}