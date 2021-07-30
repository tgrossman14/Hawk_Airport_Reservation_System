package models;

/**
 * Model class for the reservations table
 * Fields: int id, int userId, String reservationJSON, String namesJSON
 * Each field corresponds to a column in the reservations table
 * 
 * Class has a constructor to initialize each field, as well as getters and setters
 * */
public class Reservation {
	private int id;
	private int userId;
	private String reservationJSON;
	private String namesJSON;
	
	public Reservation(int id, int userId, String reservationJSON, String namesJSON) {
		super();
		this.id = id;
		this.userId = userId;
		this.reservationJSON = reservationJSON;
		this.namesJSON = namesJSON;
	}
	public String getNamesJSON() {
		return namesJSON;
	}
	public void setNamesJSON(String namesJSON) {
		this.namesJSON = namesJSON;
	}
	public Reservation() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReservationJSON() {
		return reservationJSON;
	}
	public void setReservationJSON(String reservationJSON) {
		this.reservationJSON = reservationJSON;
	}
	
}
