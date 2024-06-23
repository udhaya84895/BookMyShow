package com.bookmyshow.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

public class ShowSeatType extends BaseModel{

    //    here showseat type is ticket type

    /*
    ShowSeatType  Show
        1          1
        M          1

        showseattype table
        1 | GOLD | 150
        1 | DIAMOND | 250
        2 | GOLD | 200
   */
    @ManyToOne
    private Show show;

    private String seatType;
    private int price;
}
