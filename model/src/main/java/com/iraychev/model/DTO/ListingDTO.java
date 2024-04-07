package com.iraychev.model.DTO;

import com.iraychev.model.enums.Amenity;
import com.iraychev.model.entities.Booking;
import com.iraychev.model.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class ListingDTO {
    private UUID id;
    private User user;
    private String title;
    private String description;
    private String propertyAddress;
    private double price;
    private List<Booking> bookings;
    private List<Amenity> amenities;
}
