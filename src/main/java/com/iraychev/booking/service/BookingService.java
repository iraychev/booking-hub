package com.iraychev.booking.service;

import com.iraychev.booking.DTO.BookingDTO;
import com.iraychev.booking.model.Booking;
import com.iraychev.booking.repository.BookingRepository;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<BookingDTO> createBooking(BookingDTO bookingDTO) {

        if(!canMakeBooking(bookingDTO)){
            return ResponseEntity.badRequest().body(new BookingDTO());
        }
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        Booking savedBooking = bookingRepository.save(booking);
        BookingDTO savedBookingDTO = modelMapper.map(savedBooking, BookingDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBookingDTO);
    }

    public boolean canMakeBooking(BookingDTO bookingDTO) {
        LocalDate endDate = bookingDTO.getStartDate().plusDays(bookingDTO.getNightsToStay() - 1);

        List<Booking> existingBookings = bookingRepository.findByListingId(bookingDTO.getListing().getId());

        for (Booking existingBooking : existingBookings) {
            LocalDate existingEndDate = existingBooking.getStartDate().plusDays(existingBooking.getNightsToStay() - 1);

            if (!(endDate.isBefore(existingBooking.getStartDate()) || existingBooking.getStartDate().isAfter(existingEndDate))){
                return false;
            }
        }
        return true;
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

    public ResponseEntity<BookingDTO> updateBookingById(UUID bookingId, BookingDTO bookingDTO) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return null;
        }
        Booking existingBooking = bookingOptional.get();
        existingBooking.setStartDate(bookingDTO.getStartDate());
        existingBooking.setNightsToStay(bookingDTO.getNightsToStay());
        existingBooking.setPrice(bookingDTO.getPrice());

        Booking updatedBooking = bookingRepository.save(existingBooking);
        BookingDTO updatedBookingDTO = modelMapper.map(updatedBooking, BookingDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedBookingDTO);
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
