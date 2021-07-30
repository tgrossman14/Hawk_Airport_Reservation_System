package travellersTest;

import dao.TravellersDao;
import models.Traveller;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import services.TravellersServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class TravellersServiceTest {
    @Mock
    public TravellersDao travellersDaoImpl;
    @InjectMocks
    public TravellersServiceImpl travellersServiceImpl;
    public List<Traveller> dummyTravellers;

    @BeforeClass
    public void beforeClass(){
        travellersServiceImpl = new TravellersServiceImpl();
        MockitoAnnotations.openMocks(this);
        dummyTravellers = new ArrayList<>();
        dummyTravellers.add(new Traveller(1,3,"13G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",1738));
        dummyTravellers.add(new Traveller(2,3,"14G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",1738));
        dummyTravellers.add(new Traveller(3,3,"15G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",420));
        dummyTravellers.add(new Traveller(4,3,"16G","XL",
                "ECONOMY","2011-01-01 00:00:00","UA",420));
        int mockReservationId = 3;
        Mockito.when(travellersDaoImpl.getAllSeats(mockReservationId)).thenReturn(dummyTravellers);
    }
    @Test
    public void testGetAllSeats(){
        int reservationId = 3;
        List<Traveller> retrievedSeats = travellersServiceImpl.getAllSeats(reservationId);
        Assert.assertEquals(retrievedSeats.get(0).getId(),1);
        Assert.assertEquals(retrievedSeats.get(1).getId(),2);
        Assert.assertEquals(retrievedSeats.get(2).getId(),3);
        Assert.assertEquals(retrievedSeats.get(3).getId(),4);
        Assert.assertEquals(retrievedSeats.get(0).getFlightNumber(),1738);
        Assert.assertEquals(retrievedSeats.get(1).getFlightNumber(),1738);
        Assert.assertEquals(retrievedSeats.get(2).getFlightNumber(),420);
        Assert.assertEquals(retrievedSeats.get(3).getFlightNumber(),420);
        Assert.assertEquals(retrievedSeats.get(0).getPlaneSeat(),"13G");
        Assert.assertEquals(retrievedSeats.get(1).getPlaneSeat(),"14G");
        Assert.assertEquals(retrievedSeats.get(2).getPlaneSeat(),"15G");
        Assert.assertEquals(retrievedSeats.get(3).getPlaneSeat(),"16G");
    }

}
