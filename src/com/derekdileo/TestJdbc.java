package com.derekdileo;

import java.sql.Connection;
import java.sql.DriverManager;

// Sanity check for JDBC connectivity prior to Hibernate implementation
public class TestJdbc {

	public static void main(String[] args) {

		String jdbcUrl = "jdbc:mysql://localhost:3306/todo_app";
		String user = "root";
		String pass = "password";

		try {

			System.out.println("Connecting to database: " + jdbcUrl);

			@SuppressWarnings("unused")
			Connection conn = DriverManager.getConnection(jdbcUrl, user, pass);

			System.out.println("Connection successful!");

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
