package airportmanagement.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "airport")
public class Airport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String location;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airport", cascade = {CascadeType.ALL})
    private List<Flight> flights;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
