package airportmanagement.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flight")
public class Flight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String day;
    @Column(nullable = false)
    private String departureHour ;

    @Column(nullable = false)
    private String arrivalHour;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String company;
    @Column(nullable = false)
    private String flightNumber;

    @Column(nullable = true)
    private String status;

    @ManyToOne
    @JoinColumn(name = "airport")
    private Airport airport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    public String getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
