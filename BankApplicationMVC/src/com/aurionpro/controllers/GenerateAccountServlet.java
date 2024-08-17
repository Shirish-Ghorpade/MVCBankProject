package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;

/**
 * Servlet implementation class GenerateAccountServlet
 */
@WebServlet("/generateaccount")
public class GenerateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DbUtil dbUtil = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		dbUtil = DbUtil.getDbUtil();
		String balanceStr = request.getParameter("balance");
		String customerIDStr = request.getParameter("cust-id");

		double balance = 0.0;
		int customerID = 0;

		try {
			// Validate and parse the balance
			if (balanceStr != null && !balanceStr.isEmpty()) {
				balance = Double.parseDouble(balanceStr);
				if (balance < 5000) {
					throw new NumberFormatException("Balance must be at least 5000.");
				}
			} else {
				throw new NumberFormatException("Balance parameter is missing or empty.");
			}
		} catch (NumberFormatException e) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error !!!!");
			request.setAttribute("message", "Invalid balance amount: " + e.getMessage());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/OpenAccount.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		try {
			// Validate and parse the customerID
			if (customerIDStr != null && !customerIDStr.isEmpty()) {
				customerID = Integer.parseInt(customerIDStr);
				if (customerID <= 0) {
					throw new NumberFormatException("Customer ID must be a positive integer.");
				}
			} else {
				throw new NumberFormatException("Customer ID parameter is missing or empty.");
			}
		} catch (NumberFormatException e) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error !!!!");
			request.setAttribute("message", "Invalid customer ID: " + e.getMessage());
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/OpenAccount.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		if (balance < 5000) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error !!!!");
			request.setAttribute("message", "Balance should be at least 5000.");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/OpenAccount.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		HashSet<Long> accountNumbers = new HashSet<>();
		accountNumbers.add(0L);
		long accountNumber = 0;

		try {
			connection = dbUtil.connectToDb();

			while (true) {
				long randomNumber = generateAccountNumber();
				if (!accountNumbers.contains(randomNumber)) {
					accountNumber = randomNumber;
					break;
				}
			}

			preparedStatement = connection.prepareStatement(
					"INSERT INTO bank_accounts (AccountCustomerID, AccountNumber, Balance) VALUES (?,?,?)");

			preparedStatement.setInt(1, customerID);
			preparedStatement.setLong(2, accountNumber);
			preparedStatement.setDouble(3, balance);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				request.setAttribute("title", "Congratulations!!!!");
				request.setAttribute("message",
						"Your account has been successfully created. Your new account number is " + accountNumber
								+ ". Thank you for choosing us. If you have any questions or need assistance, feel free to contact our support team.");

			} else {
				request.setAttribute("message", "Something went wrong. Please try again.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("title", "Failure !!!");
			request.setAttribute("message",
					"Account is already created for this Customer ID " + customerID + ". Please try again.");
		} finally {
			// Close resources
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/OpenAccount.jsp");
		request.setAttribute("showModal", "true");
		requestDispatcher.forward(request, response);
	}

	private static long generateAccountNumber() {
		Random random = new Random();
		return 10000000000L + (long) (random.nextDouble() * 9000000000L);
	}
}
