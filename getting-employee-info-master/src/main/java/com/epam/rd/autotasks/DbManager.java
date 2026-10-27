package com.epam.rd.autotasks;

import java.sql.*;
import java.util.Properties;

public class DbManager implements AutoCloseable{
	private static DbManager instance;
	private final Connection connection;

	private DbManager(String host, String databaseName, String username, String password) throws SQLException {
		String url = "jdbc:postgresql://" + host + "/" + databaseName;
		Properties props = new Properties();
		props.setProperty("user", username);
		props.setProperty("password", password);

		this.connection = DriverManager.getConnection(url, props);
	}

	public static Connection getConnection(String host, String databaseName, String username, String password) throws SQLException
	{
		if(instance==null) {
			instance = new DbManager(host, databaseName, username, password);
		}

		return instance.connection;
	}

	public static int callCountDepartments(Connection connection) throws SQLException {
		int total = 0;
		try (CallableStatement statement = connection.prepareCall("CALL COUNT_DEPARTMENTS(?)")) {
			statement.registerOutParameter(1, Types.INTEGER);
			statement.execute();

			total = statement.getInt(1);
		}

        return total;
	}

	public static int callCountEmployees(Connection connection) throws SQLException {
		int total = 0;
		try (CallableStatement statement = connection.prepareCall("CALL COUNT_EMPLOYEES(?)")) {
			statement.registerOutParameter(1, Types.INTEGER);
			statement.execute();

			total = statement.getInt(1);
		}

		return total;
	}

	public static int callCountEmployeesByDepartmentId(Connection connection, int departmentId) throws SQLException {
		int total = 0;
		try (CallableStatement statement = connection.prepareCall("CALL COUNT_EMPLOYEES_BY_DEPARTMENT_ID(?,?)"))  {
			statement.setInt(1, departmentId);

			statement.registerOutParameter(2, Types.INTEGER);
			statement.execute();

			total = statement.getInt(2);
		}

		return total;
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}
}