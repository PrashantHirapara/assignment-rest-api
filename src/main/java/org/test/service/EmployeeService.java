package org.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.model.Department;
import org.test.model.Employee;
import org.test.repository.DepartmentRepository;
import org.test.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addEmployeeToDepartment(Employee employee, String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found"));
        employee.setDepartment(department);
        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new RuntimeException("Employee with id " + employeeId + " not found");
        }
    }

    public List<Employee> getEmployeesInDepartment(String departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new RuntimeException("Department not found"));
        return department.getEmployees();
    }

    public void updateEmployee(String employeeId, Employee updatedEmployee) {
        if (employeeRepository.existsById(employeeId)) {
            Employee existingEmployee = employeeRepository.findById(employeeId).isPresent() ? employeeRepository.findById(employeeId).get() : null;

            assert existingEmployee != null;
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setPosition(updatedEmployee.getPosition());
            existingEmployee.setSalary(updatedEmployee.getSalary());

            employeeRepository.save(existingEmployee);
        } else {
            throw new RuntimeException("Employee with id " + employeeId + " not found");
        }
    }
}
