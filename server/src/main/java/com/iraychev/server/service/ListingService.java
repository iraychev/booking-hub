package com.iraychev.server.service;

import com.iraychev.model.DTO.ListingDTO;
import com.iraychev.model.entities.Listing;
import com.iraychev.model.entities.Image;
import com.iraychev.server.repository.ListingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ListingDTO> getAllByUserId(UUID userId) {
        Optional<List<Listing>> listingsOptional = listingRepository.findAllByUserId(userId);
        if (listingsOptional.isEmpty()) {
            return new ArrayList<>();
        }
        List<Listing> listings = listingsOptional.get();
        return listings.stream()
                .map(listing -> modelMapper.map(listing, ListingDTO.class))
                .collect(Collectors.toList());
    }

    public Page<ListingDTO> getListingsByPage(int page, int size, String searchTerm) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Listing> listingsPage;

        if (searchTerm != null && !searchTerm.isEmpty()) {
            listingsPage = listingRepository.findByTitleContainingIgnoreCaseOrPropertyAddressContainingIgnoreCase(
                    searchTerm, searchTerm, pageable);
        } else {
            listingsPage = listingRepository.findAll(pageable);
        }

        return listingsPage.map(listing -> modelMapper.map(listing, ListingDTO.class));
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

    @PreAuthorize("hasRole('ADMIN') or ((hasRole('PROPERTY_OWNER') and @userSecurityService.canAccessListing(authentication.getPrincipal().getUsername(), #listingId)))")
    public ListingDTO updateListingById(UUID listingId, ListingDTO listingDTO) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);
        if (listingOptional.isEmpty()) {
            return null;
        }
        Listing existingListing = listingOptional.get();
        if (listingDTO.getImages() != null && !listingDTO.getImages().isEmpty()) {
            existingListing.getImages().clear();

            for (Image image : listingDTO.getImages()) {
                existingListing.getImages().add(image);
            }
        }
        modelMapper.map(listingDTO, existingListing);
        Listing updatedListing = listingRepository.save(existingListing);
        return modelMapper.map(updatedListing, ListingDTO.class);

    }

    @PreAuthorize("hasRole('ADMIN') or ((hasRole('PROPERTY_OWNER') and @userSecurityService.canAccessListing(authentication.getPrincipal().getUsername(), #listingId)))")
    public boolean deleteListingById(UUID listingId) {
        Optional<Listing> listingOptional = listingRepository.findById(listingId);
        if (listingOptional.isPresent()) {
            listingRepository.delete(listingOptional.get());
            return true;
        }
        return false;
    }
}
