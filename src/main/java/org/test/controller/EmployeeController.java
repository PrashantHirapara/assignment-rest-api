package org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.dto.EmployeeDTO;
import org.test.model.Employee;
import org.test.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(),
                        emp.getEmail(), emp.getPosition(), emp.getSalary()))
                .toList();
    }

    @GetMapping("/department/{deptId}")
    public List<EmployeeDTO> getEmployeesInDepartment(@PathVariable String deptId) {
        return employeeService.getEmployeesInDepartment(deptId).stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getEmail(), emp.getPosition(), emp.getSalary()))
                .toList();
    }

    @PostMapping("/department/{deptId}")
    public ResponseEntity<String> addEmployee(@PathVariable String deptId, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeService.addEmployeeToDepartment(employee, deptId);

        return new ResponseEntity<>("Employee added successfully to department " + deptId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Map<String, String>> deleteEmployee(@PathVariable String empId) {

        try {
            employeeService.deleteEmployee(empId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee with id " + empId + " successfully deleted");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error deleting employee with id " + empId);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String empId) {
        try {
            Employee employee = employeeService.getEmployeeById(empId);
            EmployeeDTO employeeDTO = new EmployeeDTO(
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getPosition(),
                    employee.getSalary()
            );
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{empId}")
    public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable String empId, @RequestBody Employee updatedEmployee) {
        try {
            employeeService.updateEmployee(empId, updatedEmployee);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee with id " + empId + " successfully updated");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
