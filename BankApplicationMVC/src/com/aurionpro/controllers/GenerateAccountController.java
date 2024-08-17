package com.aurionpro.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GenerateAccountController
 */
@WebServlet("/openaccount")
public class GenerateAccountController extends HttpServlet {

	private static final long serialVersionUID = 8238848079157710049L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/OpenAccount.jsp");
		requestDispatcher.forward(request, response);
	}
}
