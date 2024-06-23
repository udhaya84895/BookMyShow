package com.bookmyshow.demo.controllers;

import com.bookmyshow.demo.dtos.ResponseStatus;
import com.bookmyshow.demo.exceptions.SeatNotAvailable;
import com.bookmyshow.demo.exceptions.ShowNotFound;
import com.bookmyshow.demo.models.Booking;
import com.bookmyshow.demo.dtos.BookShowRequestDto;
import com.bookmyshow.demo.dtos.BookShowResponseDto;
import com.bookmyshow.demo.exceptions.UserIsNotValid;
import com.bookmyshow.demo.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private BookingService bookingService;
    private static final String USER_INVALID_MESSAGE  = "User id invalid";
    private static final String SHOW_INVALID_MESSAGE  = "Show id invalid";
    private static final String SOMETHING_WENT_WRONG = "Something went wrong";

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookShowResponseDto bookShow(BookShowRequestDto request) {

        try {
            Booking booking =  bookingService.bookShow(request);
            return new BookShowResponseDto(booking.getId(),booking.getAmount(), ResponseStatus.SUCCESS,USER_INVALID_MESSAGE);
        }
        catch (UserIsNotValid e) {
            return new BookShowResponseDto(null,0,ResponseStatus.FAILURE,USER_INVALID_MESSAGE);
        }
        catch (ShowNotFound e) {
            return new BookShowResponseDto(null,0,ResponseStatus.FAILURE,SHOW_INVALID_MESSAGE);
        }
        catch (SeatNotAvailable e) {
            return new BookShowResponseDto(null,0,ResponseStatus.FAILURE,SOMETHING_WENT_WRONG);
        }

    }
}
