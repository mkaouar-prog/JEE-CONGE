package domaine;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity

public class User implements Serializable {

	
	@OneToMany(mappedBy = "user")
    private List<Conge> conge;




     public User(List<Conge> conge, long id, String code, String type, Date dateEmbauchement, String password,
            String nom, String prenom, String login) {
        super();
        this.conge = conge;
        this.id = id;
        this.code = code;
        this.type = type;
        this.dateEmbauchement = dateEmbauchement;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
    }
    public User(long id, String code, String type, Date dateEmbauchement, String password, String nom, String prenom,
            String login) {
        super();
        this.id = id;
        this.code = code;
        this.type = type;
        this.dateEmbauchement = dateEmbauchement;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
    }

	
	   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	@Column(length=50)
	private String nom;
	@Column(length=50)
	private String prenom;
	

	private Date dateEmbauchement;
	@Column(length=50)
	private String login;
	@Column(length=10)
	private String password;
	private String type;
	private static final long serialVersionUID = 1L;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", code=" + code + ", nom=" + nom + ", prenom=" + prenom + ", dateEmbauchement="
				+ dateEmbauchement + ", login=" + login + ", password=" + password + ", type=" + type + "]";
	}
	public User() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}   
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}   
	public Date getDateEmbauchement() {
		return this.dateEmbauchement;
	}

	public void setDateEmbauchement(Date dateEmbauchement) {
		this.dateEmbauchement = dateEmbauchement;
	}   
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
   
}
