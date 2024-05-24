package services;

import javax.ejb.Local;

import domaine.User;
@Local
public interface UserSessionLocal {
	public User getUserByLoginAndPassword(String login, String password);

    public User getUserById(Long id);

}
