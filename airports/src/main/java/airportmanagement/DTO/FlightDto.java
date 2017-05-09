package airportmanagement.DTO;

public class FlightDto extends CreatingFlightDto {
    public Long id;

    /*public String get(String parameterName) {
        for (Field field:FlightDto.class.getFields()
             ) {
            System.out.println(field.toString());
            System.out.println(field.toGenericString());
            if (field.toString().equals(parameterName))
                return field.getName();

        }
        return  null;
    }*/
}
