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




//Priority  # 2
public class MainPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("MainPage doGet");
		HttpSession session = req.getSession();
		System.out.println(session.getAttribute("username"));
		if (session.getAttribute("username") == null) {
			// user is not logged in, or the session expired
			resp.sendRedirect(req.getContextPath() + "/Login");
			return;
		}
	
		Projectcontroller controller = new Projectcontroller();
				
		String userName = (String) session.getAttribute("username");
		System.out.println("Session info");
		System.out.println(req.getSession().getAttribute("username"));
		System.out.println(req.getSession().getAttribute("userID"));
		System.out.println(req.getSession().getAttribute("fristName"));
		System.out.println(req.getSession().getAttribute("lastName"));
		System.out.println(req.getSession().getAttribute("type"));
		System.out.println(req.getSession().getAttribute("emailAddress"));
		
		
		ArrayList<User> users = controller.getAccountInformation(userName);
		User user = users.get(0);
		System.out.println(user.getUsername());
		System.out.println(user.getUserID());
		req.setAttribute("user", user);
		
		int userID = (int) session.getAttribute("userID");
		user.setUserID(userID);
		
		


		req.getRequestDispatcher("/_view/MainPage.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/_view/MainPage.jsp").forward(req, resp);

		}
		


	}
