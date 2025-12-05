package com.pluralsight.models;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
    // Static BasicDataSource instance - shared across the entire application
    private static BasicDataSource ds = new BasicDataSource();

     // Static initialization block  runs once when class is first loaded
    static {
        ds.setUrl("jdbc:mysql://localhost:3306/car_dealership");   // Set database connection URL
        ds.setUsername("root");                                      // Set database username
        ds.setPassword("yearup");
        ds.setMinIdle(5);                                            // Set minimum number of idle connections in the pool
        ds.setMaxIdle(10);                                           // Set maximum number of idle connections in the pool
        ds.setMaxOpenPreparedStatements(50);                            // Set maximum number of prepared statements
    }

    public static BasicDataSource getDataSource() {   //BasicDataSource configured for the car_dealership database
        return ds;
    }

}