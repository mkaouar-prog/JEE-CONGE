package services;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domaine.User;

@Stateless(name = "US")
public class UserSession  implements UserSessionLocal, UserSessionRemote{
	@PersistenceContext
    private EntityManager em;



    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

}
