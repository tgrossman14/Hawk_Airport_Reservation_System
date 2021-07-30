package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconfig.ConnectionUtil;
import dbconfig.ResourceClosers;
import models.Reservation;
/**
 * Implementation class for the ReservationDao interface
 * */
public class ReservationDaoImpl implements ReservationDao {

	/**
	 * Method to get all reservations with given user ID
	 * */
	@Override
	public List<Reservation> getAllReservations(int userId) {
		List<Reservation> reservations = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionUtil.getConnection();

			//Now that we've gotten a connection, we want to execute our SQL
			//statement. In order to execute the SQL statement, we will need to use
			//the Statement interface.
			final String SQL = "select * from reservations where reservation_user_id = ?";
			stmt = conn.prepareStatement(SQL);
			//Since the SQL statement is parameterized, I need to set the values of
			//the parameters.
			stmt.setInt(1, userId);
			set = stmt.executeQuery();
			while (set.next()) {
				Reservation reservation = new Reservation(set.getInt(1), set.getInt(2), set.getString(3), set.getString(4));
				reservations.add(reservation);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		return reservations;
	}
	/**
	 * Method to get a specific reservation by reservation ID
	 * */
	@Override
	public Reservation getReservation(int reservationId) {
		Reservation reservation = new Reservation();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try {
			conn = ConnectionUtil.getConnection();

			//Now that we've gotten a connection, we want to execute our SQL
			//statement. In order to execute the SQL statement, we will need to use
			//the Statement interface.
			final String SQL = "select * from reservations where reservation_id = ?";
			stmt = conn.prepareStatement(SQL);
			
			//Since the SQL statement is parameterized, I need to set the values of
			//the parameters.
			stmt.setInt(1, reservationId);
			set = stmt.executeQuery();
			
			while(set.next()) {
				reservation.setId(set.getInt(1));
				reservation.setUserId(set.getInt(2));
				reservation.setReservationJSON(set.getString(3));
				reservation.setNamesJSON(set.getString(4));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeResultSet(set);
		}
		return reservation;
	}
	/**
	 * Method to create a reservation for a given userId, with two JSON string params: json and names
	 * */
	@Override
	public void createReservation(int userId, String json, String names) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			final String SQL = "insert into reservations values(default, ?, ?, ?)";
			stmt = conn.prepareStatement(SQL);
			
			//Since the SQL statement is parameterized, I need to set the values of
			//the parameters.
			stmt.setInt(1, userId);
			stmt.setString(2, json);
			stmt.setString(3, names);
			System.out.println(names);
			//And of course, execute the SQL statement once you have set your parameters.
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
		}
	}
	/**
	 * Updates a seat for a given travellerId, with a String parameter planeSeat to specify the new seat
	 * */
	@Override
	public void deleteReservation(int reservationId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement cascadeDeleteStmt = null;
		
		try {
			conn = ConnectionUtil.getConnection();
			final String cascadeDelete = "delete from travellers where traveller_reservation_id = ?";
			cascadeDeleteStmt = conn.prepareStatement(cascadeDelete);
			cascadeDeleteStmt.setInt(1, reservationId);
			cascadeDeleteStmt.execute();
			
			final String SQL = "delete from reservations where reservation_id = ?";
			stmt = conn.prepareStatement(SQL);
			
			//Since the SQL statement is parameterized, I need to set the values of
			//the parameters.
			stmt.setInt(1, reservationId);
			
			//And of course, execute the SQL statement once you have set your parameters.
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			ResourceClosers.closeConnection(conn);
			ResourceClosers.closeStatement(stmt);
			ResourceClosers.closeStatement(cascadeDeleteStmt);
		}
	}

}
