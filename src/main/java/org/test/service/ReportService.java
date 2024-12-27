package org.test.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public byte[] generateEmployeeReport() throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/employee_report.jrxml"));

        List<Map<String, Object>> employees = getEmployeesData();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Employee Report");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    private List<Map<String, Object>> getEmployeesData() {
        return jdbcTemplate.queryForList(
                "SELECT d.name AS departmentName, e.name AS employeeName, e.position AS employeePosition, e.salary AS employeeSalary " +
                        "FROM employees e " +
                        "JOIN departments d ON e.department_id = d.id " +
                        "ORDER BY d.name"
        );
    }
}
