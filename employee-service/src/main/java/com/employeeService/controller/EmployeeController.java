package com.employeeService.controller;

import com.employeeService.entity.Employee;
import com.employeeService.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Create a new employee
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody String jsonString) {
        try {
            return employeeService.createEmployee(jsonString);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
    }

    // Get all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    // Update an employee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody String jsonString) {
        try {
            return employeeService.updateEmployee(id, jsonString);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
    }

    // Delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }
}
