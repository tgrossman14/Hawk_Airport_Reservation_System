package reservationTest;


import dao.ReservationDaoImpl;

import dbconfig.ConnectionUtil;
import dbconfig.ResourceClosers;
import models.Reservation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

// Using a local test database with the same structure as the production database
// Then mock the connection and results
public class ReservationDaoTest {
    @Mock
    Connection connection;
    @Mock
    ResultSet resultSet;
    @Mock
    PreparedStatement prepStmt;
    @InjectMocks
    public ReservationDaoImpl reservationDaoImpl;

    @BeforeClass
    public void setUp(){
        reservationDaoImpl = new ReservationDaoImpl();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllReservations(){
        List<Reservation> expectedReservations = new ArrayList<>();
        List<Reservation> actualReservations;
        int expectedUserId = 4;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "select * from reservations where reservation_user_id = ?";
            prepStmt = connection.prepareStatement(SQL);
            //Since the SQL statement is parameterized, I need to set the values of
            //the parameters.
            prepStmt.setInt(1, expectedUserId);
            resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = new Reservation(resultSet.getInt("reservation_id"),
                        resultSet.getInt("reservation_user_id"), resultSet.getString("reservation_json"),
                        resultSet.getString("names_json"));
                expectedReservations.add(reservation);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
            ResourceClosers.closeResultSet(resultSet);
        }

        actualReservations = reservationDaoImpl.getAllReservations(expectedUserId);
        // Only Testing the reservation ID and user ID because reservation json cannot be replicated in a test database
        int index = 0;
        for(Reservation getReservation: actualReservations){
            Assert.assertEquals(getReservation.getUserId(), expectedReservations.get(index).getUserId());
            Assert.assertEquals(getReservation.getId(), expectedReservations.get(index).getId());
            index++;
        }

    }
    @Test
    public void testGetReservation(){
        Reservation expectedReservation = new Reservation();
        Reservation actualReservation;
        int expectedReservationId = 5;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "select * from reservations where reservation_id = ?";
            prepStmt = connection.prepareStatement(SQL);
            prepStmt.setInt(1, expectedReservationId);
            resultSet = prepStmt.executeQuery();

            while(resultSet.next()) {
                expectedReservation.setId(resultSet.getInt(1));
                expectedReservation.setUserId(resultSet.getInt(2));
                expectedReservation.setReservationJSON(resultSet.getString(3));
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
            ResourceClosers.closeResultSet(resultSet);
        }
        actualReservation = reservationDaoImpl.getReservation(expectedReservationId);
        Assert.assertEquals(actualReservation.getUserId(),expectedReservation.getUserId());
    }
    @Test
    public void testCreateReservation(){
        Reservation reservation = new Reservation(11, 6,"Test JSON","Passenger Test Name");
        // Create using the same procedure use in the actual data base
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "insert into reservations values(default, ?, ?)";
            prepStmt = connection.prepareStatement(SQL);
            prepStmt.setInt(1, reservation.getUserId());
            prepStmt.setString(2, reservation.getReservationJSON());
            //And of course, execute the SQL statement once you have set your parameters.
            prepStmt.execute();
            // After the SQL statement is created, we used select statement to confirm if It's created in the test database
            final String SQLTwo = "select * from reservations where reservation_user_id = ? and reservation_json = ?";
            prepStmt= connection.prepareStatement(SQLTwo);
            prepStmt.setInt(1, reservation.getUserId());
            prepStmt.setString(2, reservation.getReservationJSON());
            resultSet = prepStmt.executeQuery();
            // Assertion to ensure the user is created, check if there's something in the result set
            Assert.assertTrue(resultSet.next());
            // Then we delete to cluttering and duplicating keys in the test database
            final String SQLThree = "delete from reservations where reservation_user_id = ? and reservation_json = ?";
            prepStmt= connection.prepareStatement(SQLThree);
            prepStmt.setInt(1, reservation.getUserId());
            prepStmt.setString(2, reservation.getReservationJSON());
            prepStmt.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
        }
    }
    @Test
    public void testDeleteReservation(){
        Reservation expectedReservation = new Reservation(90, 6,"Test JSON","Passenger Test Name");
        // Create a reservation
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "insert into reservations values(?, ?, ?)";
            prepStmt = connection.prepareStatement(SQL);
            prepStmt.setInt(1,expectedReservation.getId());
            prepStmt.setInt(2, expectedReservation.getUserId());
            prepStmt.setString(3, expectedReservation.getReservationJSON());
            //And of course, execute the SQL statement once you have set your parameters.
            prepStmt.execute();
            // After the SQL statement is created, we used select statement to confirm if It's created in the test database
            final String SQLTwo = "select * from reservations where reservation_user_id = ? and reservation_json = ?";
            prepStmt= connection.prepareStatement(SQLTwo);
            prepStmt.setInt(1, expectedReservation.getUserId());
            prepStmt.setString(2, expectedReservation.getReservationJSON());
            resultSet = prepStmt.executeQuery();
            // Assertion to ensure the user is created, check if there's something in the result set
            Assert.assertTrue(resultSet.next());
            // Then use the same delete procedure that is being used in the actual database
            final String SQLThree = "delete from reservations where reservation_user_id = ?";
            prepStmt= connection.prepareStatement(SQLThree);
            prepStmt.setInt(1, expectedReservation.getUserId());
            prepStmt.execute();
            final String SQLFour = "select * from reservations where reservation_user_id = ? and reservation_json = ?";
            prepStmt= connection.prepareStatement(SQLFour);
            prepStmt.setInt(1, expectedReservation.getUserId());
            prepStmt.setString(2, expectedReservation.getReservationJSON());
            resultSet = prepStmt.executeQuery();
            // Assertion to ensure the reservation is deleted, check if there's something in the result set
            Assert.assertFalse(resultSet.next());

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
        }
        System.out.println("This test is for the Reservation DAO Deleting Reservation");
    }
}
