package com.epam.rd.autotasks;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    private static final Logger LOGGER = System.getLogger(Application.class.getName());

    public static void main(String[] args) {
        try(Connection connection = DbManager.getConnection("localhost", "employee_info_master", "postgres", "postgres")) {
            int departmentCount = DbManager.callCountDepartments(connection);
            LOGGER.log(System.Logger.Level.INFO, "Total number of departments: " + departmentCount);

            int employeeCount = DbManager.callCountEmployees(connection);
            LOGGER.log(System.Logger.Level.INFO, "Total number of employees: " + employeeCount);

            employeeCount = DbManager.callCountEmployeesByDepartmentId(connection, 1);
            LOGGER.log(System.Logger.Level.INFO, "Number of employees in the first department: " + employeeCount);

            employeeCount = DbManager.callCountEmployeesByDepartmentId(connection, 2);
            LOGGER.log(System.Logger.Level.INFO, "Number of employees in the second department: " + employeeCount);

            employeeCount = DbManager.callCountEmployeesByDepartmentId(connection, 3);
            LOGGER.log(System.Logger.Level.INFO, "Number of employees in the third department: " + employeeCount);
        }catch (SQLException e) {
            e.printStackTrace();
            return;
        };
    }
}
