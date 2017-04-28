package ycp.cs320.teamProject.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ycp.cs320.teamProject.model.User;



public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		System.out.println("In the Index servlet do get");
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
		req.getRequestDispatcher("/_view/Index.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("In the Index servlet");
		if (req.getParameter("account") != null) {
			resp.sendRedirect(req.getContextPath() + "/account");
		} else if (req.getParameter("createAccount") != null) {
			resp.sendRedirect(req.getContextPath() + "/CreateAccount");
		} else if (req.getParameter("changePW") != null) {
			resp.sendRedirect(req.getContextPath() + "/changePassword");
		} 
		else if (req.getParameter("createSOP") != null) {
			resp.sendRedirect(req.getContextPath() + "/createSOP");
		} else if (req.getParameter("MainPage") != null) {
			resp.sendRedirect(req.getContextPath() + "/MainPage");
		} else if (req.getParameter("reviseSOP") != null) {
			resp.sendRedirect(req.getContextPath() + "/reviseSOP");
		} else if (req.getParameter("sop") != null) {
			resp.sendRedirect(req.getContextPath() + "/SOP");
		} else if (req.getParameter("treaingHistory") != null) {
			resp.sendRedirect(req.getContextPath() + "/Traininghistory");
		} else if (req.getParameter("createPosition") != null) {
			resp.sendRedirect(req.getContextPath() + "/createPosition");
		} else if (req.getParameter("Login") != null) {
			resp.sendRedirect(req.getContextPath() + "/Login");
		}
		//req.setAttribute("sessionid", model);
		req.getRequestDispatcher("/_view/Index.jsp").forward(req, resp);
	}

}


