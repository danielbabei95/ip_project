package com.hotels.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hotel")
public class Hotel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    private int phone;

    @Column(nullable = false)
    private int category;

    @Column(length = 50, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade={CascadeType.ALL})
    private List<Room> rooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade={CascadeType.ALL})
    private List<String> facilities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone=phone;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category=category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Room> getRoomNr() {
        return rooms;
    }

    public void setRoomNr(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }
}
