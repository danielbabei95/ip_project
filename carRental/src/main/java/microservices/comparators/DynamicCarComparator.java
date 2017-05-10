package microservices.comparators;

import java.util.Comparator;

import microservices.DTO.CarDto;

public class DynamicCarComparator implements Comparator<CarDto> {

	private static int compareBy = 0;

	public DynamicCarComparator(int compareBy) {
		this.compareBy = compareBy;
	}

	public DynamicCarComparator compareBy(int compareBy) {
		this.compareBy = compareBy;
		return this;
	}

	public int compare(CarDto a, CarDto b) {
		if (compareBy == 0) {
			return Integer.compare(a.pricePerDay, b.pricePerDay);
		}
		if (compareBy == 1) {
			return Integer.compare(b.pricePerDay, a.pricePerDay);
		}
		return 0;
	}
}
