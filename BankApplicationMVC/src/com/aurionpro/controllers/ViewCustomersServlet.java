//package com.aurionpro.controllers;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.aurionpro.dbconnection.DbUtil;
//import com.aurionpro.entity.Customer;
//
///**
// * Servlet implementation class ViewCustomersServlet
// */
//@WebServlet("/ViewCustomersServlet")
//public class ViewCustomersServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private DbUtil dbUtil = null;
//	private PreparedStatement preparedStatement = null;
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.setContentType("text/html");
//		dbUtil = DbUtil.getDbUtil();
//		Connection connection = dbUtil.connectToDb();
//
//		try {
//			List<Customer> customerList = new ArrayList<>();
//			String sqlQuery = "SELECT c.FirstName, c.LastName, a.AccountNumber, a.Balance FROM customers as c INNER JOIN bank_accounts as a ON c.customerID=a.AccountCustomerID";
//			preparedStatement = connection.prepareStatement(sqlQuery);
//			ResultSet customerDetails = preparedStatement.executeQuery();
//			while (customerDetails.next()) {
//				customerList
//						.add(new Customer(customerDetails.getString("FirstName"), customerDetails.getString("LastName"),
//								customerDetails.getLong("AccountNumber"), customerDetails.getDouble("Balance")));
//			}
//			request.setAttribute("customerList", customerList);
//			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewCustomers.jsp");
//			requestDispatcher.forward(request, response);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//}
package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;
import com.aurionpro.entity.Customer;

/**
 * Servlet implementation class ViewCustomersServlet
 */
@WebServlet("/customers")
public class ViewCustomersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DbUtil dbUtil = null;
	private PreparedStatement preparedStatement = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();

		List<Customer> customerList = new ArrayList<>();
		String searchQuery = request.getParameter("search");
		String sqlQuery = "SELECT c.FirstName, c.LastName, a.AccountNumber, a.Balance " + "FROM customers as c "
				+ "INNER JOIN bank_accounts as a ON c.customerID = a.AccountCustomerID";

		if (searchQuery != null && !searchQuery.trim().isEmpty()) {
			sqlQuery += " WHERE c.FirstName LIKE ? OR c.LastName LIKE ?";
		}

		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
			if (searchQuery != null && !searchQuery.trim().isEmpty()) {
				String searchPattern = "%" + searchQuery + "%";
				preparedStatement.setString(1, searchPattern);
				preparedStatement.setString(2, searchPattern);
			}
			ResultSet customerDetails = preparedStatement.executeQuery();
			while (customerDetails.next()) {
				customerList
						.add(new Customer(customerDetails.getString("FirstName"), customerDetails.getString("LastName"),
								customerDetails.getLong("AccountNumber"), customerDetails.getDouble("Balance")));
			}
			request.setAttribute("customerList", customerList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewCustomers.jsp");
			requestDispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
