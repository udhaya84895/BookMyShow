package com.bookmyshow.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel {

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    /*
          User       Booking
          1           M
          1           1

          Unless, you really required the List<Booking> on users, don't keep that variable in User.
          Always keep the id of 1 side on m side -> Follow this for classes also.
     */

    @ManyToOne
    private User user;

    // to handle cancellation of booking, then it'll be m:m

    @OneToMany
    private List<ShowSeat> showSeatList;

    private int amount;

    @OneToMany
    private List<Payment> payments;
}
