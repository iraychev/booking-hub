package com.iraychev.server.security;

import com.iraychev.model.entities.Listing;
import com.iraychev.model.entities.Booking;
import com.iraychev.server.repository.BookingRepository;
import com.iraychev.server.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserSecurityService {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public boolean canAccessListing(String username, UUID listingId) {
        Optional<Listing> listing = listingRepository.findById(listingId);
        return listing.map(value -> value.getUser().getUsername().equals(username)).orElse(false);
    }

    public boolean canAccessBooking(String username, UUID bookingId) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return booking.map(value -> value.getOwner().getUsername().equals(username)).orElse(false);
    }
}
