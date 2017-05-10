package com.hotels.models;

import javax.persistence.*;
import java.io.Serializable;



@Entity
@Table(name = "room")


public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer nrBeds;

    @Column(nullable = false)
    private Integer nrRooms ;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean availability;


    public Long getRId()
    {
        return id;
    }

    public void setRId(Long id)
    {
        this.id = id;
    }


    public Integer getNrBeds() {
        return nrBeds;
    }

    public void setNrBeds(Integer nrBeds) {
        this.nrBeds = nrBeds;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }


    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }


}
