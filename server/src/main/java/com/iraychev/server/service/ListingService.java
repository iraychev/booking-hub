package com.iraychev.server.service;

import com.iraychev.model.DTO.ListingDTO;
import com.iraychev.model.entities.Listing;
import com.iraychev.server.repository.ListingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ListingService(ListingRepository listingRepository, ModelMapper modelMapper) {
        this.listingRepository = listingRepository;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROPERTY_OWNER'))")
    public ListingDTO createListing(ListingDTO listingDTO) {
        Listing listing = modelMapper.map(listingDTO, Listing.class);
        Listing savedListing = listingRepository.save(listing);
        return modelMapper.map(savedListing, ListingDTO.class);
    }

    public List<ListingDTO> getAllListings() {
        List<Listing> listings = listingRepository.findAll();
        return listings.stream()
                .map(listing -> modelMapper.map(listing, ListingDTO.class))
                .collect(Collectors.toList());
    }

    public ListingDTO getListingById(UUID listingId) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);
        return listingOptional.map(listing -> modelMapper.map(listing, ListingDTO.class)).orElse(null);
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROPERTY_OWNER') and @userSecurityService.canAccessListing(principal, #listingId))")
    public ListingDTO updateListingById(UUID listingId, ListingDTO listingDTO) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);
        if (listingOptional.isPresent()) {
            Listing existingListing = listingOptional.get();
            existingListing.setTitle(listingDTO.getTitle());
            existingListing.setDescription(listingDTO.getDescription());
            Listing updatedListing = listingRepository.save(existingListing);
            return modelMapper.map(updatedListing, ListingDTO.class);
        }
        return null;
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROPERTY_OWNER') and @userSecurityService.canAccessListing(principal, #listingId))")
    public boolean deleteListingById(UUID listingId) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);
        if (listingOptional.isPresent()) {
            listingRepository.delete(listingOptional.get());
            return true;
        }
        return false;
    }
}
