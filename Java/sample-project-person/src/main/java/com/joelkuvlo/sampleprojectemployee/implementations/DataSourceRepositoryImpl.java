package com.joelkuvlo.sampleprojectperson.implementations;

import com.joelkuvlo.sampleprojectperson.model.Employee;
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
    private final String tableName = "EMPLOYEE";

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

    public void save(Employee employee) {
        String sql = "INSERT INTO " + tableName + " (first_name, middle_name, last_name, date_of_birth, hire_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getMiddleName(), employee.getLastName(),
                employee.getDateOfBirth(), employee.getHireDate());
    }

    public List<Employee> findAll() {
        String sql = "SELECT id as empId, first_name as firstName, middle_name as middleName, last_name as lastName, date_of_birth as dateOfBirth, hire_date as hireDate FROM dev.person";
        List<Employee> employees = jdbcTemplate.query(sql, new PersonRowMapper());
        for (Employee employee : employees) {
            System.out.println(employee.getEmpId() + " " + employee.getFirstName() + " " + employee.getLastName());
        }
        return employees;
    }




    public Employee findById(Long id) {
        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
    }

    public void update(Employee employee) {
        String sql = "UPDATE " + tableName + " SET first_name = ?, middle_name = ?, last_name = ?, " +
                "date_of_birth = ?, hire_date = ? WHERE id = ?";
        jdbcTemplate.update(sql, employee.getFirstName(), employee.getMiddleName(), employee.getLastName(),
                employee.getDateOfBirth(), employee.getHireDate(), employee.getEmpId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}

