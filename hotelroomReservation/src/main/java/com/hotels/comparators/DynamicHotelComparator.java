package com.hotelsamdrooms.comparators;

import com.hotelsamdrooms.DTO.HotelDto;

import java.util.Comparator;


public class DynamicHotelComparator implements Comparator<HotelDto> {

    private static int compareBy = 0;

    public DynamicHotelComparator(int compareBy) {
        this.compareBy = compareBy;
    }

    public DynamicHotelComparator compareBy(int compareBy) {
        this.compareBy = compareBy;
        return this;
    }

    public int compare(HotelDto a, HotelDto b) {
        if (compareBy == 0) {
            return Float.compare(a.category, b.category);
        }
        if (compareBy == 1) {
            return Float.compare(b.category, a.category);
        }
        return 0;
    }

}
