package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;

@WebServlet("/addcustomer")
public class AddCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";
	DbUtil dbUtil = null;
	PreparedStatement preparedStatement = null;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}

		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String emailID = request.getParameter("email_id").trim();
		String password = request.getParameter("password").trim();

		// Validate inputs
		if (!isValidName(firstName) || !isValidName(lastName)) {
			handleError(request, response, "Validation Failed !!!",
					"First name and last name should only contain alphabetic characters.");
			return;
		}
		if (!isValidEmail(emailID)) {
			handleError(request, response, "Validation Failed !!!", "Invalid email format.");
			return;
		}
		if (!isValidPassword(password)) {
			handleError(request, response, "Validation Failed !!!",
					"Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character.");
			return;
		}

		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();

		String username = (String) session.getAttribute("username");
		int operationPerformedByUserID = getUserID(username, connection);

		String sqlQuery = "INSERT INTO customers (OperationPerformedByUserID, firstName, lastName, emailID, password) VALUES (?,?,?,?,?)";

		try {
			if (insertUser(emailID, password, connection) > 0) {
				System.out.println("Successfully inserted in users column");
			} else {
				System.out.println("Failed to insert in users column");
			}

			int affectedRows = insertCustomer(operationPerformedByUserID, firstName, lastName, emailID, password,
					sqlQuery, connection);
			if (affectedRows > 0) {
				int customerID = getGeneratedCustomerID(preparedStatement);
				request.setAttribute("firstName", firstName);
				request.setAttribute("password", password);
				request.setAttribute("customerID", customerID);
				handleSuccess(request, response, customerID);
			} else {
				handleError(request, response, "Failure !!!", "Something went wrong. Please, Try again!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			handleError(request, response, "Error !!!",
					"An error occurred. The email address you entered is already associated with an existing account. Please try again with a different email address.");
		}
	}

	private boolean isValidName(String name) {
		return name != null && NAME_PATTERN.matcher(name).matches();
	}

	private boolean isValidEmail(String email) {
		return email != null && Pattern.matches(EMAIL_REGEX, email);
	}

	private boolean isValidPassword(String password) {
		return password != null && Pattern.matches(PASSWORD_REGEX, password);
	}

	private int getUserID(String username, Connection connection) {
		int userID = 0;
		try {
			preparedStatement = connection.prepareStatement("SELECT UserID FROM users WHERE username=?");
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				userID = resultSet.getInt("UserID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

	private int insertUser(String emailID, String password, Connection connection) throws SQLException {
		preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?,?,?)");
		preparedStatement.setString(1, emailID);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, "Customer");
		return preparedStatement.executeUpdate();
	}

	private int insertCustomer(int userID, String firstName, String lastName, String emailID, String password,
			String sqlQuery, Connection connection) throws SQLException {
		preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, userID);
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, lastName);
		preparedStatement.setString(4, emailID);
		preparedStatement.setString(5, password);
		return preparedStatement.executeUpdate();
	}

	private int getGeneratedCustomerID(PreparedStatement preparedStatement) throws SQLException {
		int customerID = 0;
		ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		if (generatedKeys.next()) {
			customerID = generatedKeys.getInt(1);
		}
		return customerID;
	}

	private void handleSuccess(HttpServletRequest request, HttpServletResponse response, int customerID)
			throws ServletException, IOException {
		request.setAttribute("showModal", "true");
		request.setAttribute("title", "Success !!!");
		request.setAttribute("message", "Customer details are inserted successfully. Your customer ID is " + customerID
				+ ". Please note down the customer ID for the next steps");
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
		requestDispatcher.forward(request, response);
	}

	private void handleError(HttpServletRequest request, HttpServletResponse response, String title, String message)
			throws ServletException, IOException {
		request.setAttribute("showModal", "true");
		request.setAttribute("title", title);
		request.setAttribute("message", message);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AddCustomer.jsp");
		requestDispatcher.forward(request, response);
	}
}
