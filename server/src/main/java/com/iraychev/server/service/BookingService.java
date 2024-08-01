package com.iraychev.server.service;

import com.iraychev.model.DTO.BookingDTO;
import com.iraychev.server.exception.BookingDateOverlapException;
import com.iraychev.model.entities.Booking;
import com.iraychev.server.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('RENTER'))")
    public BookingDTO createBooking(BookingDTO bookingDTO) {

        if (cantMakeBooking(bookingDTO)) {
            throw new BookingDateOverlapException("Booking date overlaps with existing booking/s");
        }
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    private boolean cantMakeBooking(BookingDTO bookingDTO) {
        LocalDate startDate = bookingDTO.getStartDate();
        LocalDate endDate = startDate.plusDays(bookingDTO.getNightsToStay() - 1);

        List<Booking> existingBookings = bookingRepository.findAllByListingId(bookingDTO.getListing().getId());

        for (Booking existingBooking : existingBookings) {
            LocalDate existingStartDate = existingBooking.getStartDate();
            LocalDate existingEndDate = existingStartDate.plusDays(existingBooking.getNightsToStay() - 1);

            if (!(endDate.isBefore(existingStartDate) || startDate.isAfter(existingEndDate))) {
                return true;
            }
        }
        return false;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        return bookingOptional.map(booking -> modelMapper.map(booking, BookingDTO.class)).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('RENTER') and @userSecurityService.canAccessBooking(principal, #bookingId))")
    public BookingDTO updateBookingById(UUID bookingId, BookingDTO bookingDTO) {
        if (cantMakeBooking(bookingDTO)) {
            throw new BookingDateOverlapException("Booking date overlaps with existing booking/s");
        }
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return null;
        }
        Booking existingBooking = bookingOptional.get();

        modelMapper.map(BookingDTO.class, existingBooking);
        Booking updatedBooking = bookingRepository.save(existingBooking);
        return modelMapper.map(updatedBooking, BookingDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('RENTER') and @userSecurityService.canAccessBooking(principal, #bookingId))")
    public boolean deleteBookingById(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            bookingRepository.delete(bookingOptional.get());
            return true;
        }
        return false;
    }

    public List<BookingDTO> geAllByListingId(UUID listingId) {
        List<Booking> bookings = bookingRepository.findAllByListingId(listingId);
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }
}
