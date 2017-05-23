package com.hotelsamdrooms.models;

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

    @Column(length = 500, nullable = false)
    private String name;

    @Column(length = 500, nullable=false)
    private String address;

    @Column(length = 500, nullable=false)
    private String phone;

    @Column(length=500)
    private float category;

    @Column(length = 5000)
    private String description;

    @Column
    @ElementCollection(targetClass=String.class)
    private List<String> rooms;

   @Column
   @ElementCollection(targetClass=String.class)
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getCategory() {
        return category;
    }

    public void setCategory(float category) {
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

    public List<String> getRoom() {return rooms;    }

    public void setRoom(List<String> rooms) {this.rooms = rooms;    }

    public List<String> getFacility() {
        return facilities;
    }

    public void setFacility(List<String> facilities) {
        this.facilities = facilities;
    }
}
