package com.joelkuvlo.sampleprojectperson;

import com.joelkuvlo.sampleprojectperson.DataSource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class DataSourceRowMapper implements RowMapper<DataSource> {
    @Override
    public DataSource mapRow(ResultSet rs, int rowNum) {
        DataSource dataSource = new DataSource();
        try {
            dataSource.setId(rs.getLong("id"));
            dataSource.setDataElements(Collections.singleton(rs.getString("data_elements")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}
