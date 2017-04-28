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


public class changePasswordServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//private getAccountInfo user = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in change PW servlet do get");
		HttpSession session = req.getSession();
		if (session.getAttribute("username")== null) {
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;

		}
		req.getRequestDispatcher("/_view/changePassword.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//req.getRequestDispatcher("/_view/changePassword.jsp").forward(req, resp);
		if (req.getParameter("Index") != null) {
			System.out.println("back to index");
			resp.sendRedirect(req.getContextPath() + "/Index");
			//resp.sendRedirect(req.getContextPath() + "/Login");
		} 
		//String user = (String) req.getSession().getAttribute("User.username");
		HttpSession session = req.getSession();
		String userName = (String) session.getAttribute("username");
		Projectcontroller controller = new Projectcontroller();

		ArrayList<User> users = controller.getAccountInformation(userName);
		User user = users.get(0);

		System.out.println("CHANGEpw DO POST");

		//session data 
		//String name = (String)req.getAttribute("user");
		String oldPassword = (String) req.getParameter("oldPassword");
		String password = (String) req.getParameter("password");
		String password2 = (String) req.getParameter("password2");
		if (password.equals(password2)){
			controller.changeUserPassword(user.getUsername(), oldPassword, password);
			System.out.println(user.getUsername());
			System.out.println(oldPassword);
			System.out.println(password);
			//Passwords don't match
		
		}else {
			System.out.println("PW don't match");
			resp.sendRedirect(req.getContextPath()+ "/retrychangePassword");
			//model.setPassword(password);
		

		//req.setAttribute("sessionid", model);


		}
		req.getRequestDispatcher("/_view/changePassword.jsp").forward(req, resp);

	}




}
