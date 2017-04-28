package ycp.cs320.teamProject.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ycp.cs320.teamProject.controllers.Projectcontroller;
import ycp.cs320.teamProject.model.User;


public class CreateAccountServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//private getAccountInfo user = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("CreateAccountServlet: doGet");
		HttpSession session = req.getSession();
		System.out.println(session.getAttribute("username"));
		if (session.getAttribute("username") == null) {
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
		if(session.getAttribute("type").equals("User")){
			resp.sendRedirect(req.getContextPath() + "/MainPage");
			
		}
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req, resp);
	}	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("CreateAccountServlet: doPost");
		
		//session data 
		User model = new User();
		Projectcontroller controller = new Projectcontroller();
		
		//I'm taking this out because the DB will assign a user number
		//int UserNumber = (int) req.getSession().getAttribute("UserID");
		//model.setUserID(UserNumber);
		String UserName = (String) req.getParameter("username");
		model.setLastName(UserName);
		String Password = (String) req.getParameter("password");
		model.setLastName(Password);
		String FirstName = (String) req.getParameter("FirstName");
		model.setFirstName(FirstName);
		String LastName = (String) req.getParameter("LastName");
		model.setLastName(LastName);
		String Email = (String) req.getParameter("Email");
		model.setEmailAddress(Email);
		String isAdmin = (String) req.getParameter("Admin");
		model.setEmailAddress(isAdmin);

		controller.addUserToDatabase(UserName, Password, Email, isAdmin, FirstName, LastName);
		req.setAttribute("sessionid", model);
		if (req.getParameter("index") != null) {
			resp.sendRedirect(req.getContextPath() + "/Index");
		}
		
		req.getRequestDispatcher("/_view/CreateAccount.jsp").forward(req, resp);

	}
	private String getSession(HttpServletRequest req, String name) {
		// TODO Auto-generated method stub
		return String.valueOf(req.getParameter(name));
	}



}
