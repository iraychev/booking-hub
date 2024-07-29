package com.iraychev.server.repository;

import com.iraychev.model.entities.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID> {
    Optional<List<Listing>> findAllByUserId(UUID userId);
    Page<Listing> findByTitleContainingIgnoreCaseOrPropertyAddressContainingIgnoreCase(String title, String address, Pageable pageable);
}
