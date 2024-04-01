package com.iraychev.booking.DTO;

import com.iraychev.booking.model.Listing;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
public class BookingDTO {

    private UUID id;
    private UserDTO owner;
    private UserDTO renter;
    private Listing listing;
    private LocalDate startDate;
    private int nightsToStay;
    private double price;
}
