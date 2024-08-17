package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbUtil dbUtil = null;
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		if (username == null || password == null || role == null) {
			handleError(request, response, "Please provide all required fields.");
			return;
		}

		if (!isValidEmail(username)) {
			handleError(request, response, "Invalid email format.");
			return;
		}

		try {
			if (!authenticateUser(username, password, role, connection)) {
				handleError(request, response, "Invalid username, password, or role.");
				return;
			}

			session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("role", role);

			int customerID = getCustomerId(username, password, connection);
			if (customerID > 0) {
				session.setAttribute("customerID", customerID);
			}

			String redirectPage = getRedirectPage(role);
			if (redirectPage == null) {
				handleError(request, response, "Invalid role.");
				return;
			}

			RequestDispatcher requestDispatcher = request.getRequestDispatcher(redirectPage);
			requestDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			handleError(request, response, "An error occurred while processing your request.");
		} finally {
			closeConnection(connection);
		}
	}

	private boolean isValidEmail(String email) {
		return email != null && Pattern.matches(EMAIL_REGEX, email);
	}

	private boolean authenticateUser(String username, String password, String role, Connection connection)
			throws SQLException {
		String query = "SELECT * FROM users WHERE username=? AND password=? AND role=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, role);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	private int getCustomerId(String username, String password, Connection connection) throws SQLException {
		String query = "SELECT customerId FROM customers WHERE emailID=? AND password=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("customerId");
				}
			}
		}
		return 0;
	}

	private String getRedirectPage(String role) {
		switch (role.toLowerCase()) {
		case "customer":
			return "/CustomerHome.jsp";
		case "admin":
			return "/AdminHome.jsp";
		default:
			return null;
		}
	}

	private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		request.setAttribute("showModal", "true");
		request.setAttribute("title", "Error!!");
		request.setAttribute("error", errorMessage);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/LoginPage.jsp");
		requestDispatcher.forward(request, response);
	}

	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
