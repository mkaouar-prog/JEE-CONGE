package services;
import java.util.List;

import javax.ejb.Local;

import domaine.Conge;


@Local
public interface CongeSessionLocal {
	public Conge addConge(Conge c);
	public Conge validConge(Long idd);
	public Conge refuseConge(Long idd);
	public Conge annulerConge(Long idd);
	
	public Conge ArreteConge(Long idd);
	
	public List<Conge> getAllCongeAdm();
	public List<Conge> getAllCongeEmp(Long idd);
	public List<Conge> getAllCongeEmpEtat(Long idd,String e);
	public List<Conge> getAllCongeAdmEtat(String e);
	public List<Conge> getAllCongeEmpYear(Long idd,int e);
	public List<Conge> getAllCongeAdmYear(int e);
	public int getNbJRes(Long userId);
	 public void updateSys();
}
