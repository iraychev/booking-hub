package com.iraychev.server.task;

import com.iraychev.server.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingCleanupTask {

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldBookings() {
        LocalDate thresholdDate = LocalDate.now().minusDays(90);
        bookingRepository.deleteOldBookings(thresholdDate);
    }
}