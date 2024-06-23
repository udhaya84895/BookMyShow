package com.bookmyshow.demo.services;

import com.bookmyshow.demo.exceptions.SeatNotAvailable;
import com.bookmyshow.demo.exceptions.ShowNotFound;
import com.bookmyshow.demo.models.*;
import com.bookmyshow.demo.dtos.BookShowRequestDto;
import com.bookmyshow.demo.exceptions.UserIsNotValid;
import com.bookmyshow.demo.repositories.ShowRepository;
import com.bookmyshow.demo.repositories.ShowSeatRepository;
import com.bookmyshow.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;

    public Booking bookShow(BookShowRequestDto request) throws UserIsNotValid, ShowNotFound, SeatNotAvailable {

        // get user with userId
        Optional<User> user = userRepository.findById(request.getUserId());
        if(!user.isPresent()){
            throw new UserIsNotValid();
        }

        // get show with showId
        Optional<Show> show = showRepository.findById(request.getShowId());
        if(!show.isPresent()){
            throw new ShowNotFound();
        }

        List<ShowSeat> reserveShowSeats = reserveShowSeats(request.getShowSeatIds());

        return reserveBooking(request, user, reserveShowSeats);
    }

    private Booking reserveBooking(BookShowRequestDto request, Optional<User> user, List<ShowSeat> reserveShowSeats) {
        Booking booking  = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(priceCalculator(request.getShowSeatIds(), request.getShowId()));
        booking.setUser(user.get());  // optional thing
        booking.setShowSeatList(reserveShowSeats);
        booking.setPayments(new ArrayList<>());
        return booking;
    }


    
//    List<ShowSeat> reserveShowSeats = reserveShowSeats(request.getShowSeatIds());
//
//    Booking booking  = new Booking();
//        booking.setBookingStatus(BookingStatus.PENDING);
//        booking.setAmount(priceCalculator(request.getShowSeatIds(),request.getShowId()));
//        booking.setUser(user.get());  // optional thing
//        booking.setShowSeatList(reserveShowSeats);
//        booking.setPayments(new ArrayList<>());
//        return booking;



    private int priceCalculator(List<Long> showSeatIds, Long showId) {
        // get the show
        // get the seats
        // for the seats_ids, you can find the seat type
        // for the pair (showId, seatType) -> price

        // sum it up for all seats selected.

        return 0;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<ShowSeat> reserveShowSeats(List<Long> showSeatIds) throws SeatNotAvailable {

        // get list<showSeat> for showSeatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        // checking if any of the showSeats are already reserved -> Throw an error
        for(ShowSeat showSeat :  showSeats){
            seatNotAvaliableForBooking(showSeat);
        }

        List<ShowSeat> reservedShowSeats = new ArrayList<>();

        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            reservedShowSeats.add(showSeatRepository.save(showSeat));
        }
        return reservedShowSeats;
    }


    private static boolean seatNotAvaliableForBooking(ShowSeat showSeat) throws SeatNotAvailable{

        // refactor and make it understandeable

        /* here we do locking, only if
                1. all the seats are available OR
                2. if all the seats are locked and lockedDuration > 10
         */
        if(!ShowSeatStatus.AVAILABLE.equals(showSeat.getShowSeatStatus())){
            if(ShowSeatStatus.BOOKED.equals(showSeat.getShowSeatStatus())){
                throw new SeatNotAvailable();
            }

            if(ShowSeatStatus.LOCKED.equals(showSeat.getShowSeatStatus())){
                Long lockedDuration = Duration.between(showSeat.getLockedAt().toInstant(), new Date().toInstant()).toMinutes();
                if(lockedDuration < 10) {
                    throw new SeatNotAvailable();
                }
            }
        }

        return true;
    }
}
