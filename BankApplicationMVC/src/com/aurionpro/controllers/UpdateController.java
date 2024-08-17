package com.aurionpro.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aurionpro.dbconnection.DbUtil;

/**
 * Servlet implementation class UpdateController
 */
@WebServlet("/update")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PreparedStatement preparedStatement = null;
	Connection connection = null;
	DbUtil dbUtil = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		
		dbUtil = DbUtil.getDbUtil();
		Connection connection = dbUtil.connectToDb();
		

		int customerID = (int) session.getAttribute("customerID");
		System.out.println(customerID);
		try {
			preparedStatement = connection
					.prepareStatement("select FirstName, LastName from customers where customerID=?");
			preparedStatement.setInt(1, customerID);
			ResultSet result = preparedStatement.executeQuery();
			String previousFirstName = null;
			String previousLastName = null;
			while (result.next()) {
				previousFirstName = result.getString("FirstName");
				previousLastName = result.getString("LastName");
			}
			System.out.println(previousFirstName);
			System.out.println(previousLastName);
			session.setAttribute("firstName", previousFirstName);
			session.setAttribute("lastName", previousLastName);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/UpdatePage.jsp");
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
