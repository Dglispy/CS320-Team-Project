package ycp.cs320.teamProject.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ycp.cs320.teamProject.model.User;


public class AccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//private getAccountInfo user = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/_view/account.jsp").forward(req, resp);
		
		String user = (String) req.getSession().getAttribute("User.username");
		User model = new User();
		if (user == null) {
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;

		}
		
		int UserNumber = (int) req.getSession().getAttribute("UserID");
		model.setUserID(UserNumber);
		String FirstName = (String) req.getSession().getAttribute("FirstName");
		model.setFirstName(FirstName);
		String LastName = (String) req.getSession().getAttribute("LastName");
		model.setLastName(LastName);
		String Email = (String) req.getSession().getAttribute("Email");
		model.setEmailAddress(Email);
		

		req.getRequestDispatcher("/_view/Account.jsp").forward(req, resp);
	}



}
