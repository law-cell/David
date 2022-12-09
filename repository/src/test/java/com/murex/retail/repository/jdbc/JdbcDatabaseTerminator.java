package com.murex.retail.repository.jdbc;

import com.murex.retail.repository.ComponentTable;
import com.murex.retail.repository.DatabaseTerminator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class JdbcDatabaseTerminator implements DatabaseTerminator {
    private static final Logger LOG = LogManager.getLogger(JdbcDatabaseTerminator.class);
    private final JdbcUtilityClass instance = JdbcUtilityClass.getInstance();

    @Override
    public void terminate(ComponentTable[] componentTables) {
        Stream.of(componentTables).forEach(this::deleteTable);
        LOG.info("ALL tables deleted.");
        try {
            this.instance.getConnection().close();
        } catch (SQLException e) {
            LOG.error("Could not close connection with Database.");
        }
    }

    private void deleteTable(ComponentTable componentTable) {
        try (Statement stmt = this.instance.getConnection().createStatement()) {
            String query = "DROP TABLE IF EXISTS " + componentTable.toString() + ";";
            stmt.execute(query);
            LOG.info("Table deleted.");
        } catch (SQLException e) {
            LOG.error("Could not delete table '"+componentTable+"'.");
        }
    }
}
