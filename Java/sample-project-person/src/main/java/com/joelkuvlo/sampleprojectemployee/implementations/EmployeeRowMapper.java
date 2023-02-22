package com.joelkuvlo.sampleprojectperson.implementations;

import com.joelkuvlo.sampleprojectperson.model.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpId(rs.getLong("empId"));
        employee.setFirstName(rs.getString("firstName"));
        employee.setMiddleName(rs.getString("middleName"));
        employee.setLastName(rs.getString("lastName"));
        employee.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
        employee.setHireDate(rs.getDate("hireDate").toLocalDate());
        return employee;
    }
}
