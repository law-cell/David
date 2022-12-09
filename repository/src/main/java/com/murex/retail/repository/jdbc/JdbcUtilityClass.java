package com.murex.retail.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtilityClass {
    private static volatile JdbcUtilityClass instance = null;
    private static final Logger LOG = LogManager.getLogger(JdbcUtilityClass.class);

    private JdbcUtilityClass() {
        try {
            Driver driver = new org.h2.Driver();
            DriverManager.registerDriver(driver);
            LOG.info("Driver '" + driver + "' successfully registered.");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    public static JdbcUtilityClass getInstance() {
        if (instance == null) {
            synchronized (JdbcUtilityClass.class) {
                if (instance == null) {
                    instance = new JdbcUtilityClass();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() {
        try {
            String dbUrl = "jdbc:h2:mem:computer_component;DB_CLOSE_DELAY=-1";
            return DriverManager.getConnection(dbUrl, "root", "root");
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }
}