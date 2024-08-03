package com.iraychev.server.repository;

import com.iraychev.model.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByListingId(UUID listingId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM bookings WHERE start_date + INTERVAL '1 day' * nights_to_stay < :thresholdDate", nativeQuery = true)
    void deleteOldBookings(LocalDate thresholdDate);
}
