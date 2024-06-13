package com.iraychev.model.entities;

import com.iraychev.model.enums.Amenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "listing_id")
    private List<Image> images = new ArrayList<>();


    @Column(name = "property_address", nullable = false)
    private String propertyAddress;

    private double price;

    @ElementCollection(targetClass = Amenity.class)
    @CollectionTable(name = "listing_amenities", joinColumns = @JoinColumn(name = "listing_id"))
    @Column(name = "amenity")
    @Enumerated(EnumType.STRING)
    private List<Amenity> amenities;
}
