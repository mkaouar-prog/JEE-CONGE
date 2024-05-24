package controleur;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import domaine.Conge;
import domaine.User;
import services.CongeSessionRemote;


/**
 * Servlet implementation class AddCongeServlet
 */
@WebServlet("/AddCongeServlet")
public class AddCongeServlet extends HttpServlet {
	@EJB
    private CongeSessionRemote CongeSession;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCongeServlet() {
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long userId = user.getId();
		String description = request.getParameter("description");
		String dateDebStr = request.getParameter("dateDeb");
	    String dateFinStr = request.getParameter("dateFin");

		try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date dateDeb = dateFormat.parse(dateDebStr);
	        Date dateFin = dateFormat.parse(dateFinStr);

	        Conge c = new Conge();
	        c.setDescription(description);
	        c.setDateDeb(dateDeb);
	        c.setDateFin(dateFin);
	        c.setEtat("SOLLICITE"); 
	        c.setUser(user); 

	        CongeSession.addConge(c);
	        
	        response.sendRedirect("AcceuilEmp.jsp");
	    } catch (ParseException e) {
	        e.printStackTrace();
	       
	    } catch (java.text.ParseException e) {
			
			e.printStackTrace();
		}

	}

}
