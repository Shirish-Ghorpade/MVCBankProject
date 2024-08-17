package com.aurionpro.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/customercontroller")
public class CustomerController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the value of the clicked button
    	
    	HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage.jsp");
			return;
		}
		
        String passbook = request.getParameter("passbook");
        String newTransaction = request.getParameter("newtransaction");
        String editProfile = request.getParameter("editprofile");

        // Determine which button was clicked
        if (passbook != null) {
            // Handle "Pass Book" logic
            handlePassBook(request, response);
        } else if (newTransaction != null) {
            // Handle "New Transactions" logic
            handleNewTransaction(request, response);
        } else if (editProfile != null) {
            // Handle "Edit Profile" logic
            handleEditProfile(request, response);
        } else {
            // Handle the case where no button was clicked or an unexpected state
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handlePassBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to handle passbook
        request.getRequestDispatcher("/passbook").forward(request, response);
    }

    private void handleNewTransaction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to handle new transactions
        request.getRequestDispatcher("/NewTransaction.jsp").forward(request, response);
    }

    private void handleEditProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Your logic to handle edit profile
        request.getRequestDispatcher("/update").forward(request, response);
    }
}



