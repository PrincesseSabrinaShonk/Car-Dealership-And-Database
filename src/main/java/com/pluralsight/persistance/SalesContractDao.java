package com.pluralsight.persistance;
import com.pluralsight.models.DataSource;
import com.pluralsight.models.SalesContract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesContractDao {
    public void addSale(SalesContract contract) {
        String sql = "INSERT INTO sales_contracts (vin, sales_price, recording_fee, processing_fee) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, contract.getVin());
            ps.setDouble(2, contract.getSalesPrice());
            ps.setDouble(3, contract.getRecordingFee());
            ps.setDouble(4, contract.getProcessingFee());

            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<SalesContract> getAllSales() {
        List<SalesContract> sales = new ArrayList<>();
        String sql = "SELECT * FROM sales_contracts";

        try (Connection conn = DataSource.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                sales.add(new SalesContract(
                        rs.getString("vin"),
                        rs.getDouble("sales_price"),
                        rs.getDouble("recording_fee"),
                        rs.getDouble("processing_fee")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }
}