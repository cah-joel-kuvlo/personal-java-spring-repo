package com.joelkuvlo.sampleprojectperson.implementations;

import com.joelkuvlo.sampleprojectperson.model.Person;
import com.joelkuvlo.sampleprojectperson.repository.DataSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DataSourceRepositoryImpl implements DataSourceRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String tableName = "person";

    @Autowired
    public DataSourceRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                "first_name VARCHAR(255), middle_name VARCHAR(255), last_name VARCHAR(255), " +
                "date_of_birth DATE, hire_date DATE)";
        jdbcTemplate.execute(sql);
    }

    public void save(Person person) {
        String sql = "INSERT INTO " + tableName + " (first_name, middle_name, last_name, date_of_birth, hire_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, person.getFirstName(), person.getMiddleName(), person.getLastName(),
                person.getDateOfBirth(), person.getHireDate());
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Person.class));
    }

    public void update(Person person) {
        String sql = "UPDATE " + tableName + " SET first_name = ?, middle_name = ?, last_name = ?, " +
                "date_of_birth = ?, hire_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getFirstName(), person.getMiddleName(), person.getLastName(),
                person.getDateOfBirth(), person.getHireDate(), person.getEmpId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

