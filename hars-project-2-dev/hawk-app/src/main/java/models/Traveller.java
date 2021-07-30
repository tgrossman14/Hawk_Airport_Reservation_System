package models;

/**
 * Model class for the travellers table
 * Fields: int id, int reservationId, String planeSeat, String luggage, String cabin, String flightDuration, String carrierCode, int flightNumber
 * Each field corresponds to a column in the reservations table
 * 
 * Class has a no-args constructor, a constructor to initialize each field, as well as getters and setters
 * */
public class Traveller {
	private int id;
	private int reservationId;
	private String planeSeat;
	private String luggage;
	private String cabin;
	private String flightDuration;
	private String carrierCode;
	private int flightNumber;
	public Traveller() {
		super();
	}
	public Traveller(int id, int reservationId, String planeSeat, String luggage, String cabin, String flightDuration, String carrierCode, int flightNumber) {
		super();
		this.id = id;
		this.reservationId = reservationId;
		this.planeSeat = planeSeat;
		this.luggage = luggage;
		this.cabin = cabin;
		this.flightDuration = flightDuration;
		this.carrierCode = carrierCode;
		this.flightNumber = flightNumber;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public String getPlaneSeat() {
		return planeSeat;
	}
	public void setPlaneSeat(String planeSeat) {
		this.planeSeat = planeSeat;
	}
	public String getCabin() {
		return cabin;
	}
	public void setCabin(String cabin) {
		this.cabin = cabin;
	}
	public String getFlightDuration() {
		return flightDuration;
	}
	public void setFlightDuration(String flightDuration) {
		this.flightDuration = flightDuration;
	}
	public String getLuggage() {
		return luggage;
	}
	public void setLuggage(String luggage) {
		this.luggage = luggage;
	}
}
