package com.employeeService.service;

import com.employeeService.entity.Employee;
import com.employeeService.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Create a new employee
    public ResponseEntity<Employee> createEmployee(String jsonString) throws JsonProcessingException {
        Employee empData = objectMapper.readValue(jsonString, Employee.class);
        Employee savedData = employeeRepository.save(empData);
        return new ResponseEntity<>(savedData, HttpStatus.CREATED);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get an employee by ID
    public ResponseEntity<Employee> getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    // Update an employee
    public ResponseEntity<Employee> updateEmployee(int id, String jsonString) throws JsonProcessingException {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee updatedEmployee = objectMapper.readValue(jsonString, Employee.class);
            updatedEmployee.setId(id); // Ensure the ID remains the same
            employeeRepository.save(updatedEmployee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete an employee
    public ResponseEntity<Void> deleteEmployee(int id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
