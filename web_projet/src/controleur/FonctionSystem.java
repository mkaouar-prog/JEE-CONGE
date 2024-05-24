package controleur;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CongeSessionRemote;

/**
 * Servlet implementation class FonctionSystem
 */
@WebServlet("/FonctionSystem,loadOnStartup = 1")
public class FonctionSystem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FonctionSystem() {
        super();
        updateSyst();
        // TODO Auto-generated constructor stub
    }
    private void updateSyst() {
        try {
            InitialContext context = new InitialContext();
            CongeSessionRemote sessionRemote = (CongeSessionRemote) context.lookup("ejb:/ejb_projet/CN!services.CongeSessionRemote");
            sessionRemote.updateSys();
        } catch (NamingException e) {
            e.printStackTrace();
        }}

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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
