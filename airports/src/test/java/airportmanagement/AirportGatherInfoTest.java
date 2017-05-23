package airportmanagement;

import airportmanagement.DTO.CreatingAirportDto;
import airportmanagement.DTO.CreatingFlightDto;
import airportmanagement.DTO.FlightDto;
import airportmanagement.comparators.FlightComparator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AirportGatherInfoTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Before
    // Will be performed before each test.
    public void testSetup()
    {
        System.out.println("Setup for test complete.");
    }

    @After
    // Will be performed after each test.
    public void testComplete()
    {
        System.out.println("Test complete.");
    }
    @Test
    public void test_air_cont(){
        FlightDto d1= new FlightDto();
        d1.day="Luni";
        FlightDto d2= new FlightDto();
        d2.day="Viner";
        FlightComparator flightComparator=new FlightComparator("day");
        flightComparator.compare(d1,d2);
        CreatingAirportDto dto =new CreatingAirportDto();
        dto.location=dto.name="padure";
        try {ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            this.mockMvc.perform(post("/airports").contentType(APPLICATION_JSON_UTF8).content(bytes));

            this.mockMvc.perform(get("/airports"))
                    .andExpect(status().isOk()).andDo(print());
            this.mockMvc.perform(get("/airports/padure"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test_fli_cont(){
        CreatingFlightDto dto =new CreatingFlightDto();
        dto.arrivalCity=dto.departureCity=dto.departureHour=dto.arrivalHour=dto.company=dto.day=dto.flightNumber="nop";
        try {ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            this.mockMvc.perform(post("/airports/{location}/flights","padure").contentType(APPLICATION_JSON_UTF8).content(bytes))
                   ;
            this.mockMvc.perform(get("/airports/{location}/flights?day=nop&page=1&pageSize=2","padure"))
                    .andExpect(status().isOk()).andDo(print());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test_nr_JsonObjects(){
        /* testing insert*/
        InputStream is = null;
        InputStream is1= null;
        try {

            JSONObject obj = new JSONObject();
            obj.put("name", "student");
            obj.put("day","Luni");
            obj.put("flightNumber", "Luni");
            obj.put("company", "Blue");
            obj.put("arrivalCity", "Bacau");
            obj.put("departureCity", "Bacau");
            obj.put("departureHour", "10:44");
            obj.put("arrivalHour", "10:44");
            obj.put("status", "done");

            is = new URL("http://localhost:8081/airports/bacau/flights").openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray arr=new JSONArray(jsonText);
            int contor = arr.length();

            AirportGatherInfo gth = new AirportGatherInfo();
           gth.gatherObject("Luni","1111", "Blue", "Bacau","Bacau", "10:44", "10:44","bacau") ;
            is1 = new URL("http://localhost:8081/airports/bacau/flights").openStream();
            BufferedReader rd1 = new BufferedReader(new InputStreamReader(is1, Charset.forName("UTF-8")));

            String jsonText1 = readAll(rd1);
            JSONArray arr1=new JSONArray(jsonText1);
            int contor1 = arr1.length();
            assertEquals("Testing the insert",contor + 1,contor1);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test_uniqueness() {
        InputStream is = null;
        InputStream is1 =null;
        try {
            is = new URL("http://localhost:8081/airports/bacau/flights").openStream();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String jsonText = readAll(rd);
        JSONArray arr=new JSONArray(jsonText);
        int contor = arr.length();
            AirportGatherInfo.main(null);
            is1 = new URL("http://localhost:8081/airports/bacau/flights").openStream();
            BufferedReader rd1 = new BufferedReader(new InputStreamReader(is1, Charset.forName("UTF-8")));

            String jsonText1 = readAll(rd1);
            JSONArray arr1=new JSONArray(jsonText1);
            int contor1 = arr1.length();
            assertNotEquals("Testing the unique values",contor,contor1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
