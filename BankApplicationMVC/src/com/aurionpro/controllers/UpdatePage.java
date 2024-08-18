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

/**
 * Servlet implementation class UpdatePage
 */
@WebServlet("/updatepage")
public class UpdatePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil = null;
	PreparedStatement preparedStatement = null;
	String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		if (!isValidPassword(newPassword)) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Failure");
			request.setAttribute("message",
					"Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character.\"");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/UpdatePage.jsp");
			requestDispatcher.forward(request, response);
			return;
		}

		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();

		int customerID = (int) session.getAttribute("customerID");

		try {
			preparedStatement = connection.prepareStatement("select password from customers where customerID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet result = preparedStatement.executeQuery();
			String previousPassword = null;
			while (result.next()) {
				previousPassword = result.getString("Password");
			}

			if (!previousPassword.equals(password)) {
				request.setAttribute("showModal", "true");
				request.setAttribute("title", "Failure");
				request.setAttribute("message", "Entered wrong password !!!!");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/UpdatePage.jsp");
				requestDispatcher.forward(request, response);
				return;
			}

			// System.out.println(previousFirstName);
			// System.out.println(previouslastName);
			preparedStatement = connection.prepareStatement("Update customers set firstName=? where customerID=?");
			preparedStatement.setString(1, firstName);
			preparedStatement.setInt(2, customerID);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("Update customers set lastName=? where customerID=?");
			preparedStatement.setString(1, lastName);
			preparedStatement.setInt(2, customerID);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("Update customers set password=? where customerID=?");
			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, customerID);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement("Update users set password=? where username=? and password=?");
			preparedStatement.setString(1, newPassword);
			String username = (String) session.getAttribute("username");
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, previousPassword);
			preparedStatement.executeUpdate();

			session.invalidate();
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/LoginPage.jsp");
			requestDispatcher.forward(request, response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean isValidPassword(String password) {
		return password != null && Pattern.matches(PASSWORD_REGEX, password);
	}

}
