package com.iraychev.model.DTO;

import com.iraychev.model.entities.Image;
import com.iraychev.model.enums.Amenity;
import com.iraychev.model.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter @ToString
public class ListingDTO {
    private UUID id;
    private User user;
    private String title;
    private String description;
    private List<Image> images;
    private String propertyAddress;
    private double price;
    private List<Amenity> amenities;
}
