package org.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
