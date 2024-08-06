package com.iraychev.server;

import com.iraychev.model.DTO.BookingDTO;
import com.iraychev.server.controller.BookingController;
import com.iraychev.server.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class BookingControllerTests {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        BookingDTO bookingDTO = new BookingDTO();
        when(bookingService.createBooking(bookingDTO)).thenReturn(bookingDTO);

        ResponseEntity<?> response = bookingController.create(bookingDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookingDTO, response.getBody());
    }

    @Test
    void testGetAllBookings() {
        List<BookingDTO> bookings = new ArrayList<>();
        bookings.add(new BookingDTO());
        when(bookingService.getAllBookings()).thenReturn(bookings);

        ResponseEntity<List<BookingDTO>> response = bookingController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
    }

    @Test
    void testGetAllBookingsWhenEmpty() {
        when(bookingService.getAllBookings()).thenReturn(new ArrayList<>());

        ResponseEntity<List<BookingDTO>> response = bookingController.getAll();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBookingById() {
        UUID bookingId = UUID.randomUUID();
        BookingDTO bookingDTO = new BookingDTO();
        when(bookingService.getBookingById(bookingId)).thenReturn(bookingDTO);

        ResponseEntity<BookingDTO> response = bookingController.getById(bookingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingDTO, response.getBody());
    }

    @Test
    void testGetBookingByIdNotFound() {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.getBookingById(bookingId)).thenReturn(null);

        ResponseEntity<BookingDTO> response = bookingController.getById(bookingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllByListingId() {
        UUID listingId = UUID.randomUUID();
        List<BookingDTO> bookings = new ArrayList<>();
        bookings.add(new BookingDTO());
        when(bookingService.geAllByListingId(listingId)).thenReturn(bookings);

        ResponseEntity<List<BookingDTO>> response = bookingController.geAllByListingId(listingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
    }

    @Test
    void testGetAllByListingIdWhenEmpty() {
        UUID listingId = UUID.randomUUID();
        when(bookingService.geAllByListingId(listingId)).thenReturn(new ArrayList<>());

        ResponseEntity<List<BookingDTO>> response = bookingController.geAllByListingId(listingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateBooking() {
        UUID bookingId = UUID.randomUUID();
        BookingDTO bookingDTO = new BookingDTO();
        when(bookingService.updateBookingById(bookingId, bookingDTO)).thenReturn(bookingDTO);

        ResponseEntity<?> response = bookingController.update(bookingId, bookingDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingDTO, response.getBody());
    }

    @Test
    void testUpdateBookingNotFound() {
        UUID bookingId = UUID.randomUUID();
        BookingDTO bookingDTO = new BookingDTO();
        when(bookingService.updateBookingById(bookingId, bookingDTO)).thenReturn(null);

        ResponseEntity<?> response = bookingController.update(bookingId, bookingDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBooking() {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.deleteBookingById(bookingId)).thenReturn(true);

        ResponseEntity<Void> response = bookingController.delete(bookingId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteBookingNotFound() {
        UUID bookingId = UUID.randomUUID();
        when(bookingService.deleteBookingById(bookingId)).thenReturn(false);

        ResponseEntity<Void> response = bookingController.delete(bookingId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}