package models;

/**
 * Model class for the users table
 * Fields: int id, String firstName, String firstName, String lastName, String email, String password
 * Each field corresponds to a column in the reservations table
 * 
 * Class has a constructor to initialize each field, as well as getters and setters
 * */
public class User {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	public User(){ } // Added default constructor

	public User(int id, String firstName, String lastName, String email, String password) {
		super();
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
//	
//	@Override
//	public String toString() {
//		return 
//	}
	
}
