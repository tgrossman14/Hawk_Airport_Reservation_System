package services;

import java.util.List;
import dao.TravellersDao;
import dao.TravellersDaoImpl;
import models.Traveller;

/**
 * Implementation class for the TravellersService interface
 * Each method simply calls the DAO's associated method and passes in the parameters
 * */
public class TravellersServiceImpl implements TravellersService {
	TravellersDao travellersDao;

	public TravellersServiceImpl() {
		this.travellersDao = new TravellersDaoImpl();
	}

	@Override
	public List<Traveller> getAllSeats(int reservationId) {
		return this.travellersDao.getAllSeats(reservationId);
	}

	@Override
	public void bookSeat(int reservationId, String planeSeat, String luggage, String cabin, String flightDuration, String carrierCode, int flightNumber) {
		this.travellersDao.bookSeat(reservationId, planeSeat, luggage, cabin, flightDuration, carrierCode, flightNumber);
	}

	@Override
	public void updateSeat(int travellerId, String planeSeat) {
		this.travellersDao.updateSeat(travellerId, planeSeat);
	}

}
