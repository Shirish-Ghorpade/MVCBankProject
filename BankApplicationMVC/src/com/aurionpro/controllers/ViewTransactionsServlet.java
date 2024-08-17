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
//import javax.servlet.http.HttpSession;
//
//import com.aurionpro.dbconnection.DbUtil;
//import com.aurionpro.entity.Transactions;
//
//@WebServlet("/transactions")
//public class ViewTransactionsServlet extends HttpServlet {
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
//			List<Transactions> transactionsList = new ArrayList<>();
//			String sqlQuery = "SELECT SenderAccountNumber, ReceiverAccountNumber, TransactionType, Amount, Date from transactions";
//			preparedStatement = connection.prepareStatement(sqlQuery);
//			ResultSet customerTransactionsDetails = preparedStatement.executeQuery();
//			while (customerTransactionsDetails.next()) {
//				transactionsList.add(new Transactions(customerTransactionsDetails.getLong("SenderAccountNumber"),
//						customerTransactionsDetails.getLong("ReceiverAccountNumber"),
//						customerTransactionsDetails.getString("TransactionType"),
//						customerTransactionsDetails.getDouble("Amount"),
//						customerTransactionsDetails.getString("Date")));
//			}
//			// System.out.println(transactionsList);
//			request.setAttribute("transactionsList", transactionsList);
//			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewTransactions.jsp");
//			requestDispatcher.forward(request, response);
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}

//
//
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
//import com.aurionpro.entity.Transactions;
//
//@WebServlet("/transactions")
//public class ViewTransactionsServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private DbUtil dbUtil = null;
//    private PreparedStatement preparedStatement = null;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html");
//        dbUtil = DbUtil.getDbUtil();
//        Connection connection = dbUtil.connectToDb();
//
//        try {
//            List<Transactions> transactionsList = new ArrayList<>();
//            String sortType = request.getParameter("sort");
//            String sqlQuery = "SELECT SenderAccountNumber, ReceiverAccountNumber, TransactionType, Amount, Date FROM transactions";
//
//            if ("date".equals(sortType)) {
//                sqlQuery += " ORDER BY Date";
//            } else if ("type".equals(sortType)) {
//                sqlQuery += " ORDER BY TransactionType";
//            }
//
//            preparedStatement = connection.prepareStatement(sqlQuery);
//            ResultSet customerTransactionsDetails = preparedStatement.executeQuery();
//            while (customerTransactionsDetails.next()) {
//                transactionsList.add(new Transactions(customerTransactionsDetails.getLong("SenderAccountNumber"),
//                        customerTransactionsDetails.getLong("ReceiverAccountNumber"),
//                        customerTransactionsDetails.getString("TransactionType"),
//                        customerTransactionsDetails.getDouble("Amount"),
//                        customerTransactionsDetails.getString("Date")));
//            }
//            request.setAttribute("transactionsList", transactionsList);
//            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewTransactions.jsp");
//            requestDispatcher.forward(request, response);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
//

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
//import com.aurionpro.entity.Transactions;
//
//@WebServlet("/transactions")
//public class ViewTransactionsServlet extends HttpServlet {
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
//			List<Transactions> transactionsList = new ArrayList<>();
//			String sortType = request.getParameter("sort");
//			String sqlQuery = "SELECT SenderAccountNumber, ReceiverAccountNumber, TransactionType, Amount, Date FROM transactions";
//
//			System.out.println(sortType);
//			if ("date".equals(sortType)) {
//				sqlQuery += " ORDER BY Date";
//			} else if ("type".equals(sortType)) {
//				sqlQuery += " ORDER BY TransactionType";
//			} else if ("amount".equals(sortType)) {
//				sqlQuery += " ORDER BY Amount";
//			}
//
//			preparedStatement = connection.prepareStatement(sqlQuery);
//			ResultSet customerTransactionsDetails = preparedStatement.executeQuery();
//			while (customerTransactionsDetails.next()) {
//				transactionsList.add(new Transactions(customerTransactionsDetails.getLong("SenderAccountNumber"),
//						customerTransactionsDetails.getLong("ReceiverAccountNumber"),
//						customerTransactionsDetails.getString("TransactionType"),
//						customerTransactionsDetails.getDouble("Amount"),
//						customerTransactionsDetails.getString("Date")));
//			}
//			request.setAttribute("transactionsList", transactionsList);
//			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewTransactions.jsp");
//			requestDispatcher.forward(request, response);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
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
import com.aurionpro.entity.Transactions;

@WebServlet("/transactions")
public class ViewTransactionsServlet extends HttpServlet {
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

		try {
			List<Transactions> transactionsList = new ArrayList<>();
			String sortType = request.getParameter("sort");
			String sqlQuery = "SELECT SenderAccountNumber, ReceiverAccountNumber, TransactionType, Amount, Date FROM transactions";

			if ("date".equals(sortType)) {
				sqlQuery += " ORDER BY Date";
			} else if ("type".equals(sortType)) {
				sqlQuery += " ORDER BY TransactionType";
			} else if ("amount".equals(sortType)) {
				sqlQuery += " ORDER BY Amount";
			}

			preparedStatement = connection.prepareStatement(sqlQuery);
			ResultSet customerTransactionsDetails = preparedStatement.executeQuery();
			while (customerTransactionsDetails.next()) {
				transactionsList.add(new Transactions(customerTransactionsDetails.getLong("SenderAccountNumber"),
						customerTransactionsDetails.getLong("ReceiverAccountNumber"),
						customerTransactionsDetails.getString("TransactionType"),
						customerTransactionsDetails.getDouble("Amount"),
						customerTransactionsDetails.getString("Date")));
			}
			request.setAttribute("transactionsList", transactionsList);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ViewTransactions.jsp");
			requestDispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
