package org.test.service;

import org.springframework.stereotype.Service;
import org.test.model.Department;

import java.util.HashMap;
import java.util.Map;

@Service
public class DepartmentService {
    private Map<String, Department> departmentMap = new HashMap<>();

    public Department getDepartmentById(String deptId) {
        return departmentMap.get(deptId);
    }

    public void addDepartment(Department department) {
        departmentMap.put(department.getId(), department);
    }
}
