package com.murex.retail.repository.jdbc;

import com.murex.retail.model.component.ComponentProperty;
import com.murex.retail.model.component.ComputerComponent;
import com.murex.retail.model.factory.ComponentFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetParser {
    public static ComputerComponent parseRow(ResultSet rs) throws SQLException {
        String[] dbRow = new String[ComponentProperty.values().length];
        int columnCount = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            ComponentProperty property = ComponentProperty.valueOf(rs.getMetaData().getColumnName(i + 1));
            dbRow[property.getIndex()] = rs.getString(i + 1);
        }
        return ComponentFactory.getComponent(dbRow);
    }
}
