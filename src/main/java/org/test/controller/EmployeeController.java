package org.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void addEmployee(@PathVariable String deptId, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeService.addEmployeeToDepartment(employee, deptId);
    }

    @DeleteMapping("/department/{deptId}/{empId}")
    public void deleteEmployeeFromDepartment(@PathVariable String deptId, @PathVariable String empId) {
        employeeService.deleteEmployeeFromDepartment(deptId, empId);
    }

}
