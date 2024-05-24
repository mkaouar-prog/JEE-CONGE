package domaine;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Conge
 *
 */
@Entity

public class Conge implements Serializable {
	@ManyToOne 
	private User user;
	public User getUser() 
	{ return user; 
	}
	public void setUser(User user) {
		this.user = user; 
		}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private Date dateDeb;
	private Date dateFin;
	private Date dateRupture;
	private String etat;
	private static final long serialVersionUID = 1L;

	public Conge() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}   
	public Date getDateDeb() {
		return this.dateDeb;
	}

	public void setDateDeb(Date dateDeb) {
		this.dateDeb = dateDeb;
	}   
	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}   
	public Date getDateRupture() {
		return this.dateRupture;
	}

	public void setDateRupture(Date dateRupture) {
		this.dateRupture = dateRupture;
	}   
	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public Conge(Long id, String description, Date dateDeb, Date dateFin, Date dateRupture, String etat, User user) {
		super();
		this.id = id;
		this.description = description;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.dateRupture = dateRupture;
		this.etat = etat;
		this.user = user;
	}
	@Override
	public String toString() {
		return "Conge [id=" + id + ", description=" + description + ", dateDeb=" + dateDeb + ", dateFin=" + dateFin
				+ ", dateRupture=" + dateRupture + ", etat=" + etat + ", user=" + user + "]";
	}
	public Conge(Long id, String description, Date dateDeb, Date dateFin, Date dateRupture, String etat) {
		super();
		this.id = id;
		this.description = description;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.dateRupture = dateRupture;
		this.etat = etat;
		
	}
   
}
