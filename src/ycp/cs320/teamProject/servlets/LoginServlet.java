package ycp.cs320.teamProject.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import ycp.cs320.teamProject.model.MileStone1_User;
import ycp.cs320.teamProject.model.User;
import ycp.cs320.teamProject.controllers.Projectcontroller;

//Priority #1
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Projectcontroller p;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("\nLoginServlet: doGet");

		req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("\nLoginServlet: doPost");

		String errorMessage = null;
		String username = null;
		String password = null;

		Projectcontroller p = new Projectcontroller();
		User model = new User();
		//get user and PW
		username = req.getParameter("username");
		password = req.getParameter("password");

		System.out.println("   Name: <" + username + "> PW: <" + password + ">");			

		if (username == null || password == null || username.equals("") || password.equals("")) {
			errorMessage = "Please specify both user name and password";
		} else {

			ArrayList<User> user = null;
			user = p.matchUserNameWithPassword(username);
			if(user != null && user.size()>0) {
				User u = user.get(0);
				System.out.println(u.getUsername());
				//Authenticate the user
				if(Projectcontroller.authenticate(u, password) == true){
					//Set the session true and set their username
					HttpSession session = req.getSession();
					session.setAttribute("username", u.getUsername());
					session.setAttribute("type", u.isAdmin());
					session.setAttribute("userID", u.getUserID());
					session.setAttribute("firstName", u.getFirstName());
					session.setAttribute("lastName", u.getLastName());
					session.setAttribute("emailAddress", u.getEmailAddress());
					System.out.println("Session info");
					System.out.println(req.getSession().getAttribute("username"));
					System.out.println(req.getSession().getAttribute("userID"));
					System.out.println(req.getSession().getAttribute("fristName"));
					System.out.println(req.getSession().getAttribute("lastName"));
					System.out.println(req.getSession().getAttribute("type"));
					System.out.println(req.getSession().getAttribute("emailAddress"));
					//req.getSession(true).setAttribute("username", u.getUsername());
					//req.getSession().setAttribute("type", u.isAdmin());
					//req.getSession().setAttribute("userID", u.getUserID());
					model.setSessionid(req.getSession().getId());
					//If user is an owner send them to a page of their restaurants
					if(u.isAdmin().equals("Admin")){
						resp.sendRedirect(req.getContextPath() + "/Index");
					}
					//If user is a patron send to the homepage
					else{
						//req.setAttribute("userName", u.getUsername());


						resp.sendRedirect(req.getContextPath() + "/MainPage");
					}

				}
				//display error meassage for incorrect username or password
				else{
					errorMessage = "Incorrect Username or Password";
					req.setAttribute("errorMessage", errorMessage);
					req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
				}
			}
			//otherwise, print an error message
			else{
				errorMessage = "Incorrect Username or Password";
				req.setAttribute("errorMessage", errorMessage);
				req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
				System.out.println("   Invalid login - returning to /Login");
			}

			req.setAttribute("sessionid", model.getSessionid());
			req.getRequestDispatcher("/_view/Login.jsp").forward(req, resp);
		}
	}

}

