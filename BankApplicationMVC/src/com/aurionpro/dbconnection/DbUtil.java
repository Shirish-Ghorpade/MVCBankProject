package com.aurionpro.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {

	Connection connection = null;
	static DbUtil dbUtil = null;
	PreparedStatement preparedStatement = null;

	private DbUtil() {

	}

	public static DbUtil getDbUtil() {
		if (dbUtil == null) {
			return new DbUtil();
		}
		return dbUtil;
	}

	public Connection connectToDb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "root");
			System.out.println("Connected sucessfully");

		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error connecting to the database");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return connection;
	}

	public long getSenderAccountNumber(int customerID) {
		long senderAccountNumber = 0;
		try {
			preparedStatement = connection
					.prepareStatement("SELECT accountNumber from bank_accounts where AccountCustomerID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				// finding the value of sender account number
				senderAccountNumber = resultSet.getLong("AccountNumber");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return senderAccountNumber;
	}

	public double getSenderPreviousBalance(long senderAccountNumber) {
		double senderPreviousBalance = 0;
		try {
			preparedStatement = connection
					.prepareStatement("SELECT balance from bank_accounts where AccountNumber = ?");
			preparedStatement.setLong(1, senderAccountNumber);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				senderPreviousBalance = resultSet.getLong("balance");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return senderPreviousBalance;

	}

}