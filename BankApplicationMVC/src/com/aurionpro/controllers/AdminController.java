package com.aurionpro.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the value of the clicked button
		
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		
        String addCustomerDetails = request.getParameter("addCustomerDetails");
        String addBankAccount = request.getParameter("addBankAccount");
        String viewCustomers = request.getParameter("viewCustomers");
        String viewTransaction = request.getParameter("viewTransaction");

        // Determine which button was clicked
        if (addCustomerDetails != null) {
            // Handle "Add New Customer" logic
            handleAddCustomerDetails(request, response);
        } else if (addBankAccount != null) {
            // Handle "Add Bank Account" logic
            handleAddBankAccount(request, response);
        } else if (viewCustomers != null) {
            // Handle "View Customers" logic
            handleViewCustomers(request, response);
        } else if (viewTransaction != null) {
            // Handle "View Transaction" logic
            handleViewTransaction(request, response);
        } else {
            // Handle the case where no button was clicked or an unexpected state
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleAddCustomerDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to add customer details
        request.getRequestDispatcher("/AddCustomer.jsp").forward(request, response);
    }

    private void handleAddBankAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to add bank account
        request.getRequestDispatcher("/AddBankAccount.jsp").forward(request, response);
    }

    private void handleViewCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to view customers
        request.getRequestDispatcher("/customers").forward(request, response);
    }

    private void handleViewTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to view transactions
        request.getRequestDispatcher("/transactions").forward(request, response);
    }
}
