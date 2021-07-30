package dao;

import java.util.List;
import models.Traveller;

/**
 * Interface for the Travellers DAO methods
 * Methods specified:
 * - getAllSeats: Get all seats data for a given reservationId
 * - bookSeat: Reserve a new seat for a given reservationId, with parameters for the Travellers model
 * - updateSeat: Updates a seat for a given travellerId, with a String parameter planeSeat to specify the new seat
 * */
public interface TravellersDao {
	List<Traveller> getAllSeats(int reservationId);
	void bookSeat(int reservationId, String planeSeat, String luggage,String cabin, String flightDuration, String carrierCode, int flightNumber);
	void updateSeat(int travellerId, String planeSeat);
}
