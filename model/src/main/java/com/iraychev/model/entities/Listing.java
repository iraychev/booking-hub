package com.iraychev.model.entities;

import com.iraychev.model.enums.Amenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "property_address", nullable = false)
    private String propertyAddress;

    private double price;

    @OneToMany(mappedBy = "listing")
    private List<Booking> bookings;

    @ElementCollection(targetClass = Amenity.class)
    @CollectionTable(name = "entity_amenities", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "amenity")
    @Enumerated(EnumType.STRING)
    private List<Amenity> amenities;
}
