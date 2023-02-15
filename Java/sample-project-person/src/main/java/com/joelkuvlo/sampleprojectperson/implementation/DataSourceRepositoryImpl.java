package com.joelkuvlo.sampleprojectperson.implementation;

import com.joelkuvlo.sampleprojectperson.AppLogger;
import com.joelkuvlo.sampleprojectperson.DataSource;
import com.joelkuvlo.sampleprojectperson.DataSourceRowMapper;
import com.joelkuvlo.sampleprojectperson.repository.DataSourceRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataSourceRepositoryImpl implements DataSourceRepository {
    private static final Logger LOGGER = AppLogger.getLogger();
    private final JdbcTemplate jdbcTemplate;
    @Value(value = "${tableName}")
    private String tableName;
    @Value(value = "${columnName}")
    private String columnName;

    @Autowired
    public DataSourceRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public List<DataSource> findByName(String name) {
        String sql = "SELECT * FROM " + tableName + " WHERE name = ?";
        return jdbcTemplate.query(sql, new Object[]{name}, new DataSourceRowMapper());
    }

    public void save(DataSource dataSource) {
        LOGGER.debug("Saving data elements to the database");
        String sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
        for (String dataElement : dataSource.getDataElements()) {
            jdbcTemplate.update(sql, dataElement);
            LOGGER.debug("Data element inserted: {}", dataElement);
        }
        LOGGER.debug("Data elements saved successfully");
    }

    @Override
    public void saveAll(List<DataSource> dataSources) {
        String sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
        dataSources.forEach(dataSource -> jdbcTemplate.update(sql, dataSource.getDataElements()));
    }

    @Override
    public List<String> findAllDataElements() {
        String sql = "SELECT " + columnName + " FROM " + tableName;
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<DataSource> getDataSources() {
        String sql = "SELECT * FROM DataSource";
        List<DataSource> dataSources = jdbcTemplate.query(sql, new DataSourceRowMapper());
        return dataSources;
    }

    public void createTable() {
        try {
            jdbcTemplate.execute("SELECT 1 FROM " + tableName + " LIMIT 1");
        } catch (Exception e) {
            String sql = "CREATE TABLE " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, " + columnName + " VARCHAR(255))";
            jdbcTemplate.execute(sql);
        }
    }
}
