package com.iraychev.server.controller;

import com.iraychev.model.DTO.ListingDTO;
import com.iraychev.server.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/booking-api/listings")
public class ListingController implements Controller<ListingDTO> {
    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ListingDTO listingDTO) {
        ListingDTO createdListing = listingService.createListing(listingDTO);
        return new ResponseEntity<>(createdListing, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Page<ListingDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ListingDTO> listings = listingService.getAllListings(page, size);
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ListingDTO>> getAll() {
        return null;
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ListingDTO> getById(@PathVariable UUID listingId) {
        ListingDTO listingDTO = listingService.getListingById(listingId);
        if (listingDTO != null) {
            return new ResponseEntity<>(listingDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<?> update(@PathVariable UUID listingId, @RequestBody ListingDTO listingDTO) {
        ListingDTO updatedListing = listingService.updateListingById(listingId, listingDTO);
        if (updatedListing != null) {
            return new ResponseEntity<>(updatedListing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<Void> delete(@PathVariable UUID listingId) {
        if (listingService.deleteListingById(listingId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
