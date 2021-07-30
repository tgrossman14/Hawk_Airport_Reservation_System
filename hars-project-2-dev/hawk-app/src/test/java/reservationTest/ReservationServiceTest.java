package reservationTest;

import dao.ReservationDao;
import models.Reservation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.ReservationServiceImpl;


import java.util.ArrayList;
import java.util.List;

public class ReservationServiceTest {
    @Mock
    public ReservationDao reservationDaoImpl;

    @InjectMocks
    public ReservationServiceImpl reservationServiceImpl;

    public List<Reservation> dummyReservations;
    Reservation mockReservation;
    @BeforeClass
    public void beforeClassSetUp(){
        reservationServiceImpl = new ReservationServiceImpl();
        MockitoAnnotations.openMocks(this);
        dummyReservations = new ArrayList<>();
        dummyReservations.add(new Reservation(1,2,"Testing JSON","Passenger Test Name Wan"));
        dummyReservations.add(new Reservation(2,2,"Reservation JSON","Passenger Test Name Wong"));
        dummyReservations.add(new Reservation(3,2,"Positive Testing JSON","Passenger Test Name Wing"));
        int mockUserId = 2;
        Mockito.when(reservationDaoImpl.getAllReservations(mockUserId)).thenReturn(dummyReservations);
        int mockReservationId = 2;
        mockReservation = new Reservation(2,3,"Get Reservation JSON","Passenger Test Name Win");
        Mockito.when(reservationDaoImpl.getReservation(mockReservationId)).thenReturn(mockReservation);
    }

    @Test
    public void testGetAllReservations(){
        int userId = 2;
        List<Reservation> retrievedReservations = reservationServiceImpl.getAllReservations(userId);
        Assert.assertEquals(retrievedReservations.get(0).getId(),1);
        Assert.assertEquals(retrievedReservations.get(1).getId(),2);
        Assert.assertEquals(retrievedReservations.get(2).getId(),3);
        Assert.assertEquals(retrievedReservations.get(0).getReservationJSON(),"Testing JSON");
        Assert.assertEquals(retrievedReservations.get(1).getReservationJSON(),"Reservation JSON");
        Assert.assertEquals(retrievedReservations.get(2).getReservationJSON(),"Positive Testing JSON");
    }

    @Test
    public void testGetReservation(){
        int reservationId = 2;
        Reservation reservation = reservationDaoImpl.getReservation(reservationId);
        Assert.assertEquals(reservation.getId(),2);
        Assert.assertEquals(reservation.getUserId(),3);
        Assert.assertEquals(reservation.getReservationJSON(),"Get Reservation JSON");
    }
}
