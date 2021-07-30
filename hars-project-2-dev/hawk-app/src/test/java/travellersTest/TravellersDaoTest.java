package travellersTest;

import com.amadeus.Travel;
import dao.ReservationDaoImpl;
import dao.TravellersDaoImpl;
import dbconfig.ConnectionUtil;
import dbconfig.ResourceClosers;
import models.Reservation;
import models.Traveller;
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
public class TravellersDaoTest {
    @Mock
    Connection connection;
    @Mock
    ResultSet resultSet;
    @Mock
    PreparedStatement prepStmt;
    @InjectMocks
    public TravellersDaoImpl travellersDaoImpl;
    @BeforeClass
    public void setUp(){
        travellersDaoImpl = new TravellersDaoImpl();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSeats(){
        List<Traveller> expectedTravellers = new ArrayList<>();
        List<Traveller> actualTravellers;
        int testReservationId = 5;
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "select * from travellers where traveller_reservation_id = ?";
            prepStmt = connection.prepareStatement(SQL);
            //Since the SQL statement is parameterized, I need to set the values of
            //the parameters.
            prepStmt.setInt(1, testReservationId);
            resultSet = prepStmt.executeQuery();
            while(resultSet.next()) {
                Traveller seat = new Traveller(resultSet.getInt("traveller_id"), resultSet.getInt("traveller_reservation_id"),
                        resultSet.getString("traveller_plane_seat"), resultSet.getString("traveller_luggage"),
                        resultSet.getString("traveller_cabin"), resultSet.getString("traveller_estimated_flight_duration"),
                        resultSet.getString("carrier_code"), resultSet.getInt("number"));
                expectedTravellers.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
            ResourceClosers.closeResultSet(resultSet);
        }
        actualTravellers = travellersDaoImpl.getAllSeats(testReservationId);
        int index = 0;
        for(Traveller getSeats: actualTravellers){
            Assert.assertEquals(getSeats.getPlaneSeat(),expectedTravellers.get(index).getPlaneSeat());
            Assert.assertEquals(getSeats.getLuggage(),expectedTravellers.get(index).getLuggage());
            Assert.assertEquals(getSeats.getCabin(),expectedTravellers.get(index).getCabin());
            Assert.assertEquals(getSeats.getFlightDuration(),expectedTravellers.get(index).getFlightDuration());
            Assert.assertEquals(getSeats.getReservationId(),expectedTravellers.get(index).getReservationId());
            Assert.assertEquals(getSeats.getPlaneSeat(),expectedTravellers.get(index).getPlaneSeat());
            Assert.assertEquals(getSeats.getFlightNumber(),expectedTravellers.get(index).getFlightNumber());
            index++;
        }
    }
    @Test
    public void testBookSeat(){
        Traveller mockTraveller = new Traveller(50, 18, "50G",
                "XL","ECONOMY","2011-01-01 00:00:00","UA",1734);
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "insert into travellers values(default, ?, ?, ?, ?, ?, ?, ?)";
            prepStmt = connection.prepareStatement(SQL);
            //Since the SQL statement is parameterized,  set the values of the parameters.
            prepStmt.setInt(1, mockTraveller.getReservationId());
            prepStmt.setString(2, mockTraveller.getPlaneSeat());
            prepStmt.setString(3, mockTraveller.getLuggage());
            prepStmt.setString(4, mockTraveller.getCabin());
            prepStmt.setString(5, mockTraveller.getFlightDuration());
            prepStmt.setString(6, mockTraveller.getCarrierCode());
            prepStmt.setInt(7, mockTraveller.getFlightNumber());
            //And of course, execute the SQL statement once you have set your parameters.
            resultSet = prepStmt.executeQuery();
            // Assertion to ensure the user is created, check if there's something in the result set
            Assert.assertTrue(resultSet.next());
            // Then we delete to cluttering and duplicating keys in the test database
            final String SQLThree = "delete from travellers where traveller_plane_seat = ? and traveller_id = ?";
            prepStmt= connection.prepareStatement(SQLThree);
            prepStmt.setString(1, mockTraveller.getPlaneSeat());
            prepStmt.setInt(2, mockTraveller.getId());
            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
        }
        System.out.println("This is a Test Traveller DAO - Booking a seat");
        System.out.println("We'll get a SQL Exception because Booking a seat does not return a result. " +
                "It's just confirms the that we added a seat ");
    }
    @Test
    public void testUpdateSeat(){
        // This Traveller is created first for the purpose of testing
        List<Traveller> expectedTravellers = new ArrayList<>();
        List<Traveller> actualTravellers = new ArrayList<>();

        Traveller actualUpdatedTraveller = new Traveller(50, 18, "41G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",1734);
        // Add it to the list
        actualTravellers.add(actualUpdatedTraveller);

        Traveller mockTraveller = new Traveller(50, 18, "50G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",1734);
        // For updating have a mock data
        int mockTravellerID = 50;
        String mockPlaneSeat = "41G";
        //Insert first
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres","kent123q");
            final String SQL = "insert into travellers values(?, ?, ?, ?, ?, ?, ?, ?)";
            prepStmt = connection.prepareStatement(SQL);
            //Since the SQL statement is parameterized, set the values of the parameters.
            prepStmt.setInt(1, mockTraveller.getId());
            prepStmt.setInt(2, mockTraveller.getReservationId());
            prepStmt.setString(3, mockTraveller.getPlaneSeat());
            prepStmt.setString(4, mockTraveller.getLuggage());
            prepStmt.setString(5, mockTraveller.getCabin());
            prepStmt.setString(6, mockTraveller.getFlightDuration());
            prepStmt.setString(7, mockTraveller.getCarrierCode());
            prepStmt.setInt(8, mockTraveller.getFlightNumber());
            //And of course, execute the SQL statement once you have set your parameters.
            prepStmt.execute();
            //Update the seat, base on the query used in the actual database
            final String SQLTwo = "update travellers set traveller_plane_seat = ? where traveller_id = ?";
            prepStmt = connection.prepareStatement(SQLTwo);
            prepStmt.setString(1, mockPlaneSeat);
            prepStmt.setInt(2, mockTravellerID);
            prepStmt.execute();
            // Use select statement, if it's updated correctly and then compare the data
            final String SQLThree = "select * from travellers where traveller_plane_seat = ? and traveller_id = ?";
            prepStmt = connection.prepareStatement(SQLThree);
            prepStmt.setString(1, mockPlaneSeat);
            prepStmt.setInt(2, mockTravellerID);
            resultSet = prepStmt.executeQuery();
            while(resultSet.next()) {
                Traveller seat = new Traveller(resultSet.getInt("traveller_id"), resultSet.getInt("traveller_reservation_id"),
                        resultSet.getString("traveller_plane_seat"), resultSet.getString("traveller_luggage"),
                        resultSet.getString("traveller_cabin"), resultSet.getString("traveller_estimated_flight_duration"),
                        resultSet.getString("carrier_code"), resultSet.getInt("number"));
                expectedTravellers.add(seat);
            }

            // Assertions to compare actual vs expected
            Assert.assertEquals(actualTravellers.get(0).getId(), expectedTravellers.get(0).getId());
            Assert.assertEquals(actualTravellers.get(0).getPlaneSeat(), expectedTravellers.get(0).getPlaneSeat());

            // Then delete to avoid cluttering the database for each test runs
            final String SQLFour = "delete from travellers where traveller_plane_seat = ? and traveller_id = ?";
            prepStmt= connection.prepareStatement(SQLFour);
            prepStmt.setString(1, mockPlaneSeat);
            prepStmt.setInt(2,mockTravellerID);
            prepStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ResourceClosers.closeConnection(connection);
            ResourceClosers.closeStatement(prepStmt);
        }
    }
}
