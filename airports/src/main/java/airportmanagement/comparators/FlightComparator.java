package airportmanagement.comparators;

import airportmanagement.DTO.FlightDto;

import java.util.Comparator;

public class FlightComparator implements Comparator<FlightDto> {
    private static String compareBy = "departureHour";

    public FlightComparator(String compareBy) {
        this.compareBy = compareBy;
    }

    public int compare(FlightDto a, FlightDto b) {
        if (compareBy.equals( "departureHour")) {
            return a.departureHour.compareTo(b.departureHour);
        }
        else if (compareBy.equals("arrivalHour")) {
            return a.arrivalHour.compareTo(b.arrivalHour);
        }
        else if (compareBy.equals("arrivalCity")) {
            return a.arrivalCity.compareTo(b.arrivalCity);
        }
        else if (compareBy.equals("departureCity")) {
            return a.departureCity.compareTo(b.departureCity);
        }
        else if (compareBy.equals("company")) {
            return a.company.compareTo(b.company);
        }
        else if (compareBy.equals("day")) {
            return a.day.compareTo(b.day);
        }
        else
        {
            return a.flightNumber.compareTo(b.flightNumber);
        }
    }


}