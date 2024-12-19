package org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.dto.EmployeeDTO;
import org.test.model.Employee;
import org.test.service.EmployeeService;

import java.util.List;

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

    @DeleteMapping("/department/{deptId}/{empId}")
    public ResponseEntity<String> deleteEmployeeFromDepartment(@PathVariable String deptId, @PathVariable String empId) {

        try {
            employeeService.deleteEmployeeFromDepartment(deptId, empId);
            return new ResponseEntity<>("Employee with ID " + empId + " deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Employee with ID " + empId + " not found", HttpStatus.NOT_FOUND);
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

}
