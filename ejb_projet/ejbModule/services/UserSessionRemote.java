package services;

import javax.ejb.Remote;

import domaine.User;
@Remote
public interface UserSessionRemote {
	public User getUserByLoginAndPassword(String login, String password);

    public User getUserById(Long id);

}
