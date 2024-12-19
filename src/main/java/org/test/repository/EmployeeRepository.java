package org.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
