package com.iraychev.server.repository;

import com.iraychev.model.entities.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ListingRepository extends JpaRepository<Listing, UUID> {
}
