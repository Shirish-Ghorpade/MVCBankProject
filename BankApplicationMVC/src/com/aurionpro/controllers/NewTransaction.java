package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;

/**
 * Servlet implementation class NewTransaction
 */
@WebServlet("/newtransaction")
public class NewTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final double MINIMUM_BALANCE = 5000.0; // Minimum balance requirement
	private DbUtil dbUtil = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int customerID = (int) session.getAttribute("customerID");
		long senderAccountNumber = dbUtil.getSenderAccountNumber(customerID);

		String amountStr = request.getParameter("amount");
		double amount = 0.0;

		try {
			// Validate and parse the balance
			if (amountStr != null && !amountStr.isEmpty()) {
				amount = Double.parseDouble(amountStr);
				if (amount <= 0) {
					request.setAttribute("showModal", "true");
					request.setAttribute("title", "Failure !!!!");
					request.setAttribute("message", "Amount cannot be negative or zero, Please try again");
					forwardToPage(request, response);
					return;
				}
			} else {
				request.setAttribute("showModal", "true");
				request.setAttribute("title", "Failure !!!!");
				request.setAttribute("message", "Amount parameter, Please try again");
				forwardToPage(request, response);
			}
		} catch (NumberFormatException e) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error !!!!");
			request.setAttribute("message", "Invalid balance amount: " + e.getMessage());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/NewTransaction.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		System.out.println("inserted amount is " + amount);

		String transactionType = request.getParameter("transactionType");
		if ("None".equals(transactionType)) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Failure !!!!");
			request.setAttribute("message", "Transaction type is required");
			forwardToPage(request, response);
			return;
		}

		long receiverAccountNumber = senderAccountNumber;
		if ("transfer".equalsIgnoreCase(transactionType)) {
			receiverAccountNumber = Long.parseLong(request.getParameter("receiverAccountNumber"));
		}

		try {
			double senderBalanceBeforeTransaction = findBalance(preparedStatement, resultSet, connection,
					senderAccountNumber);

			if ("transfer".equalsIgnoreCase(transactionType)) {
				if (senderAccountNumber == receiverAccountNumber) {
					request.setAttribute("showModal", "true");
					request.setAttribute("title", "Failure !!!!");
					request.setAttribute("message",
							"You cannot transfer money to self account. If you want then use credit or debit option");
					forwardToPage(request, response);
					return;
				}
				if (senderBalanceBeforeTransaction - amount < MINIMUM_BALANCE) {
					request.setAttribute("showModal", "true");
					request.setAttribute("title", "Failure !!!!");
					request.setAttribute("message",
							"Insufficient balance. Minimum balance of " + MINIMUM_BALANCE + " must be maintained.");
					forwardToPage(request, response);
					return;
				}

				preparedStatement = connection
						.prepareStatement("Update bank_accounts set balance = balance - ? where AccountNumber = ?");
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, senderAccountNumber);
				int row1 = preparedStatement.executeUpdate();

				preparedStatement = connection
						.prepareStatement("Update bank_accounts set balance = balance + ? where AccountNumber = ?");
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, receiverAccountNumber);
				int row2 = preparedStatement.executeUpdate();

				if (row1 > 0 && row2 > 0) {
					double senderBalanceAfterTransaction = findBalance(preparedStatement, resultSet, connection,
							senderAccountNumber);
					request.setAttribute("title", "Success!!!");
					request.setAttribute("message",
							"Transfer successful. Previous balance: " + senderBalanceBeforeTransaction
									+ ", Amount debited: " + amount + ", Current balance: "
									+ senderBalanceAfterTransaction);
					makeTransaction(preparedStatement, resultSet, connection, senderAccountNumber,
							receiverAccountNumber, amount, "Debit");
					makeTransaction(preparedStatement, resultSet, connection, receiverAccountNumber,
							senderAccountNumber, amount, "Credit");
				} else {
					request.setAttribute("title", "Failure");
					request.setAttribute("message", "Transfer failed, please try again.");
				}

			} else if ("credit".equalsIgnoreCase(transactionType)) {
				preparedStatement = connection
						.prepareStatement("Update bank_accounts set balance = balance + ? where AccountNumber = ?");
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, senderAccountNumber);

				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					double senderBalanceAfterTransaction = findBalance(preparedStatement, resultSet, connection,
							senderAccountNumber);
					request.setAttribute("title", "Success!!!");
					request.setAttribute("message",
							"Credit successful. Previous balance: " + senderBalanceBeforeTransaction
									+ ", Amount credited: " + amount + ", Current balance: "
									+ senderBalanceAfterTransaction);
					makeTransaction(preparedStatement, resultSet, connection, senderAccountNumber,
							receiverAccountNumber, amount, "Credit");
				} else {
					request.setAttribute("title", "Failure");
					request.setAttribute("message", "Credit failed, please try again.");
				}

			} else if ("debit".equalsIgnoreCase(transactionType)) {
				if (senderBalanceBeforeTransaction - amount < MINIMUM_BALANCE) {
					request.setAttribute("showModal", "true");
					request.setAttribute("title", "Failure !!!!");
					request.setAttribute("message",
							"Insufficient balance. Minimum balance of " + MINIMUM_BALANCE + " must be maintained.");
					forwardToPage(request, response);
					return;
				}

				preparedStatement = connection
						.prepareStatement("Update bank_accounts set balance = balance - ? where AccountNumber = ?");
				preparedStatement.setDouble(1, amount);
				preparedStatement.setLong(2, senderAccountNumber);
				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					double senderBalanceAfterTransaction = findBalance(preparedStatement, resultSet, connection,
							senderAccountNumber);
					request.setAttribute("title", "Success!!!");
					request.setAttribute("message",
							"Debit successful. Previous balance: " + senderBalanceBeforeTransaction
									+ ", Amount debited: " + amount + ", Current balance: "
									+ senderBalanceAfterTransaction);
					makeTransaction(preparedStatement, resultSet, connection, senderAccountNumber,
							receiverAccountNumber, amount, "Debit");
				} else {
					request.setAttribute("title", "Failure");
					request.setAttribute("message", "Debit failed, please try again.");
				}

			}

			forwardToPage(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error");
			request.setAttribute("message", "An error occurred, please try again.");
			forwardToPage(request, response);
		}
	}

	private double findBalance(PreparedStatement preparedStatement, ResultSet resultSet, Connection connection,
			long accountNumber) {
		double balance = 0;
		try {
			preparedStatement = connection
					.prepareStatement("SELECT balance from bank_accounts where AccountNumber = ?");
			preparedStatement.setLong(1, accountNumber);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				balance = resultSet.getDouble("balance");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	private void makeTransaction(PreparedStatement preparedStatement, ResultSet resultSet, Connection connection,
			long senderAccountNumber, long receiverAccountNumber, double amount, String transactionType) {
		try {
			preparedStatement = connection.prepareStatement(
					"INSERT INTO transactions (SenderAccountNumber, ReceiverAccountNumber, TransactionType, Amount, Date) VALUES (?,?,?,?,NOW())");
			preparedStatement.setLong(1, senderAccountNumber);
			preparedStatement.setLong(2, receiverAccountNumber);
			preparedStatement.setString(3, transactionType);
			preparedStatement.setDouble(4, amount);
			int row = preparedStatement.executeUpdate();
			if (row > 0) {
				System.out.println("Transaction details inserted successfully.");
			} else {
				System.out.println("Failed to insert transaction details.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void forwardToPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/NewTransaction.jsp");
		request.setAttribute("showModal", "true");
		requestDispatcher.forward(request, response);
	}
}
