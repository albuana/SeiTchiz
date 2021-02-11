package server.domain;

/**
 * 
 * Class that handles a user's information
 */
public class User {
	private String username;	
	private String password;
	
	/**
	 * User constructor
	 * @param username user's username
	 * @param password user's password
	 */
	public User(String username,  String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 
	 * @return the user's username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 
	 * @return the user's public key
	 * @since 1.0
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	

}
