package com.iraychev.booking.service;

import com.iraychev.booking.DTO.BookingDTO;
import com.iraychev.booking.exception.BookingDateOverlapException;
import com.iraychev.booking.model.Booking;
import com.iraychev.booking.repository.BookingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BookingService(BookingRepository bookingRepository, ModelMapper modelMapper){
        this.bookingRepository = bookingRepository;
        this.modelMapper = modelMapper;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {

        if(cannotMakeBooking(bookingDTO)){
            throw new BookingDateOverlapException("Booking date overlaps with existing booking/s");
        }
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingDTO.class);
    }

    public boolean cannotMakeBooking(BookingDTO bookingDTO) {
        LocalDate endDate = bookingDTO.getStartDate().plusDays(bookingDTO.getNightsToStay() - 1);

        List<Booking> existingBookings = bookingRepository.findByListingId(bookingDTO.getListing().getId());

        for (Booking existingBooking : existingBookings) {
            LocalDate existingEndDate = existingBooking.getStartDate().plusDays(existingBooking.getNightsToStay() - 1);

            if (!(endDate.isBefore(existingBooking.getStartDate()) || existingBooking.getStartDate().isAfter(existingEndDate))){
                return true;
            }
        }
        return false;
    }
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

    public BookingDTO updateBookingById(UUID bookingId, BookingDTO bookingDTO) {
        if(cannotMakeBooking(bookingDTO)){
            throw new BookingDateOverlapException("Booking date overlaps with existing booking/s");
        }
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return null;
        }
        Booking existingBooking = bookingOptional.get();
        existingBooking.setStartDate(bookingDTO.getStartDate());
        existingBooking.setNightsToStay(bookingDTO.getNightsToStay());
        existingBooking.setPrice(bookingDTO.getPrice());

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return modelMapper.map(updatedBooking, BookingDTO.class);
    }

    public boolean deleteBookingById(UUID bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            bookingRepository.delete(bookingOptional.get());
            return true;
        }
        return false;
    }
}
