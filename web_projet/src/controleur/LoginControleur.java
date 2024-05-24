package controleur;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domaine.User;
import services.UserSession;
import services.UserSessionRemote;

/**
 * Servlet implementation class LoginControleur
 */
@WebServlet("/LoginControleur")
public class LoginControleur extends HttpServlet {
	 @EJB
	    private UserSessionRemote UserSession;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControleur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = UserSession.getUserByLoginAndPassword(login, password);
        HttpSession session=request.getSession(true);
        if (user != null) {
            if ("ADMIN".equals(user.getType())) {
            	session.setAttribute("user",user);

                response.sendRedirect("AcceuilAdmin.jsp");
            } else {
            	
            	
            	session.setAttribute("user",user);
            	response.sendRedirect("AcceuilEmp.jsp");
            }
        } else {
           
            request.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
}}
