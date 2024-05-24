package services;
import javax.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domaine.Conge;
import domaine.User;
@Stateless (name ="CN")

public class CongeSession implements CongeSessionLocal,CongeSessionRemote {
	@PersistenceContext
    private EntityManager em;

    public Conge addConge(Conge c) {
        User u = c.getUser();
        Date sysDate = new Date();

        
        long diffInTime = sysDate.getTime() - u.getDateEmbauchement().getTime();
        long diffInDays = diffInTime / (1000 * 60 * 60 * 24);
        if (diffInDays < 365) {
            return null; 
        }

      
        long diffInDaysConge = (c.getDateFin().getTime() - c.getDateDeb().getTime()) / (1000 * 60 * 60 * 24);
        if (diffInDaysConge > 30) {
            return null; 
        }

        
        em.persist(c);
        return c;
    }
    public Conge validConge(Long idd){
    	
    	Conge conge = em.find(Conge.class,idd);
    	if (conge.getEtat().equals("SOLLICITE")) {

    	conge.setEtat("VALIDE");
    	 conge = em.merge(conge);return conge;}return null;
    
    	
    }
    public Conge annulerConge(Long idd) {
    	Conge conge = em.find(Conge.class,idd);
    	if (conge.getEtat().equals("SOLLICITE")) {
    	conge.setEtat("ANNULE");
    	 conge = em.merge(conge);
    	return conge;}
    	return null;
    }
    
//    public Conge EnCoursConge(Long idd) {
//    	Conge conge = em.find(Conge.class,idd);
//    	Date sysDate = new Date();
//    	if (conge.getEtat().equals("VALIDE")&& conge.getDateDeb().before(sysDate) && conge.getDateFin().after(sysDate)) {
//    		conge.setEtat("EN_COURS");
//       	 	conge = em.merge(conge);
//       	 	return conge;
//    }
//    	return null;
//    
//}
    public Conge refuseConge(Long idd) {
        Conge conge = em.find(Conge.class, idd);
        if (conge != null && (conge.getEtat().equals("SOLLICITE") || conge.getEtat().equals("VALIDE"))) {
            conge.setEtat("REFUSE");
            conge = em.merge(conge);
            return conge;
        }
        return null;
    }

	public Conge ArreteConge(Long idd) {
		Date sysDate = new Date();
		Conge conge = em.find(Conge.class,idd);
		if(conge.getEtat().equals("EN_COURS")) {
		conge.setEtat("ARRETE");
		conge.setDateRupture(sysDate);
		conge = em.merge(conge);
   	 	return conge;
		}return null;
	}
//	public Conge FinConge(Conge c) {
//	    if (c != null) {
//	        c.setEtat("FINI");
	public void updateSys() {
        Date sysDate = new Date();

 
        List<Conge> congesValides = getAllCongeAdmEtat("VALIDE");
        for (Conge conge : congesValides) {
            if (conge.getDateDeb().before(sysDate) && conge.getDateFin().after(sysDate)) {
                conge.setEtat("EN_COURS");
                em.merge(conge);
            }
        }

        
        List<Conge> congesEnCours = getAllCongeAdmEtat("EN_COURS");
        for (Conge conge : congesEnCours) {
            if (conge.getDateFin().before(sysDate)) {
                conge.setEtat("FINI");
                em.merge(conge);
            }
        }
    }
	        
	        
	 
	       
	public List<Conge> getAllCongeEmp(Long idd) {
	    
	    Query query = em.createQuery("SELECT c FROM Conge c WHERE c.user.id = :userId");
	    query.setParameter("userId", idd);
	    return query.getResultList();
	}
	
	public List<Conge> getAllCongeAdm() {
	    
		 Query query = em.createQuery("SELECT c FROM Conge c ORDER BY c.dateDeb DESC");
	    List<Conge> allConges = query.getResultList();
		return allConges;}
	
	
	public List<Conge> getAllCongeEmpEtat(Long idd, String e) {
	    Query query = em.createQuery("SELECT c FROM Conge c WHERE c.user.id = :userId AND c.etat = :e");
	    query.setParameter("userId", idd);
	    query.setParameter("e", e);
	    List<Conge> allConges = query.getResultList();
	    return allConges;
	}

	public List<Conge> getAllCongeAdmYear(int e){
		Query query=em.createQuery("SELECT c FROM Conge c WHERE FUNCTION('YEAR', c.dateDeb) = :year");

		query.setParameter("year", e);
		List<Conge> allConges = query.getResultList();
		return allConges;
	}
	public List<Conge> getAllCongeEmpYear(Long idd,int e){
		Query query = em.createQuery("SELECT c FROM Conge c WHERE c.user.id = :userId AND FUNCTION('YEAR', c.dateDeb) = :year");
        query.setParameter("userId", idd);
        query.setParameter("year", e);
        List<Conge> allConges = query.getResultList();
		return allConges;
	}
	public List<Conge> getAllCongeAdmEtat(String e){
		Query query=em.createQuery("SELECT c FROM Conge c WHERE c.etat = :e ");
		
		query.setParameter("etat", e);
		List<Conge> allConges = query.getResultList();
		return allConges;
	}
	public int getNbJRes(Long userId) {
		int anneeEnCours = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	    List<Conge> conges = getAllCongeEmpYear(userId,anneeEnCours);
	   
	    int joursPrisConge = 0;
	    for (Conge conge : conges) {
	        
	        if (conge.getEtat().equals("VALIDE") || conge.getEtat().equals("EN_COURS")) {
	            long diffInDays = (conge.getDateFin().getTime() - conge.getDateDeb().getTime()) / (1000 * 60 * 60 * 24);
	            joursPrisConge += diffInDays;
	        }else if(conge.getEtat().equals("ARRETE")) {
	        	long diffInDays = (conge.getDateRupture().getTime() - conge.getDateDeb().getTime()) / (1000 * 60 * 60 * 24);
	            joursPrisConge += diffInDays;
	        	
	        }
	        	
	    }
	    
	  
	    int joursTotalConge = 30; 
	    
	    int joursRestantsConge = joursTotalConge - joursPrisConge;
	    
	    return joursRestantsConge;
		
	}
	



    
}
