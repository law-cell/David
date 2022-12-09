package com.murex.retail.repository.jdbc;

import com.murex.retail.repository.ComponentTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDatabaseInitializer {
    private static final Logger LOG = LogManager.getLogger(JdbcUtilityClass.class);
    private final JdbcUtilityClass instance;

    public JdbcDatabaseInitializer() {
        this.instance = JdbcUtilityClass.getInstance();
    }

    public void initialize() {
        createAll(ComponentTable.values());
        LOG.info("ALL tables created.");
    }

    private void createAll(ComponentTable[] componentTables) {
        try (Statement stmt = instance.getConnection().createStatement()) {
            for(ComponentTable componentTable : componentTables) {
                stmt.execute(JdbcStatementUtilities.createTable(componentTable));
                LOG.info(componentTable + " table created.");
            }
        } catch (SQLException e) {
            LOG.error("Failed to create tables in database.", e);
        }
    }
}
