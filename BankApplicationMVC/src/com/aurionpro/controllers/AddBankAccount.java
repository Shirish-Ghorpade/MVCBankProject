//package com.aurionpro.controllers;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import com.aurionpro.dbconnection.DbUtil;
//import com.aurionpro.entity.Customer;
//
//@WebServlet("/addaccount")
//public class AddBankAccount extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	DbUtil dbUtil = null;
//	ResultSet customerDetails = null;
//	Customer customer = null;
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		response.setContentType("text/html");
//		int customerID = Integer.parseInt(request.getParameter("searchByCustomerID"));
//		dbUtil = DbUtil.getDbUtil();
//		Connection connection = dbUtil.connectToDb();
//		RequestDispatcher requestDispatcher = null;
//		try {
//			PreparedStatement preparedStatement = connection
//					.prepareStatement("SELECT * FROM customers WHERE CustomerID=?");
//			preparedStatement.setInt(1, customerID);
//			customerDetails = preparedStatement.executeQuery();
//			if (customerDetails.next()) {
//				customer = new Customer(customerDetails.getInt("CustomerID"), customerDetails.getString("FirstName"),
//						customerDetails.getString("LastName"), customerDetails.getString("EmailID"));
//				request.setAttribute("customer", customer);
//				HttpSession session = request.getSession();
//				session.setAttribute("customerID", customer.getCustomerID());
//				requestDispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
//
//			}
//			else {
//				request.setAttribute("title", "Failure !!!");
//				request.setAttribute("message", "Entered customer id is not valid. Please try again");
//				requestDispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
//			}
//			requestDispatcher.forward(request, response);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			e.getMessage();
//		}
//
//	}
//}

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
import com.aurionpro.entity.Customer;

@WebServlet("/addaccount")
public class AddBankAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}

		int customerID;
		try {
			customerID = Integer.parseInt(request.getParameter("searchByCustomerID"));
		} catch (NumberFormatException e) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Invalid Input");
			request.setAttribute("message", "Customer ID must be a number.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
			dispatcher.forward(request, response);
			return;
		}

		DbUtil dbUtil = DbUtil.getDbUtil();
		try (Connection connection = dbUtil.connectToDb();
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM customers WHERE CustomerID=?")) {

			preparedStatement.setInt(1, customerID);
			try (ResultSet customerDetails = preparedStatement.executeQuery()) {
				if (customerDetails.next()) {
					Customer customer = new Customer(customerDetails.getInt("CustomerID"),
							customerDetails.getString("FirstName"), customerDetails.getString("LastName"),
							customerDetails.getString("EmailID"));

					request.setAttribute("customer", customer);
					session = request.getSession();
					session.setAttribute("customerData", customer);
					session.setAttribute("customerID", customer.getCustomerID());
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
					requestDispatcher.forward(request, response);
				} else {
					request.setAttribute("showModal", "true");
					request.setAttribute("title", "Failure !!!!!");
					request.setAttribute("message", "Entered customer ID is not valid. Please try again.");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
					requestDispatcher.forward(request, response);
				}
			}
		} catch (SQLException e) {
			request.setAttribute("showModal", "true");
			request.setAttribute("title", "Error");
			request.setAttribute("message", "An error occurred while processing your request. Please try again later.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/AddBankAccount.jsp");
			dispatcher.forward(request, response);
		}
	}
}
