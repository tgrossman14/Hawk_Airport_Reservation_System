package dao;

import models.Reservation;
import java.util.List;

/**
 * Interface for the Reservation DAO methods
 * Methods specified:
 * - getAllReservations to get all reservations for a given userId
 * - getReservation to get a specific reservation with a given reservationId
 * - createReservation to create a new reservation for a given userId, with two JSON string params: json and names
 * - deleteReservation to delete a specific reservation with a given reservationId
 * */
public interface ReservationDao {
	List<Reservation> getAllReservations(int userId);
	Reservation getReservation(int reservationId);
	void createReservation(int userId, String json, String names);
	void deleteReservation(int reservationId);
}
