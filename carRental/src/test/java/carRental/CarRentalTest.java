package carRental;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import carRental.DTO.AgencyDto;
import carRental.DTO.CarDto;
import carRental.DTO.CreatingAgencyDto;
import carRental.DTO.CreatingCarDto;
import carRental.comparators.DynamicCarComparator;
import carRental.controllers.AgencyController;
import carRental.controllers.CarController;
import carRental.models.Agency;
import carRental.models.Car;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class CarRentalTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJsonEncoding() {

		int contor = 0;
		try {

			InputStream is = new URL("http://127.0.0.1:8081/cars/1/cars?page=1&pageSize=5").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);

			JSONArray genreJsonObject;

			genreJsonObject = new JSONArray(jsonText);
			contor = genreJsonObject.length();

		} catch (JSONException e) {
			fail("Error, json not valid");
			e.printStackTrace();
		} catch (IOException e) {
			fail("Error, site not responding");
			e.printStackTrace();
		}

		assertEquals("Paging doesn't work or there are no objects in database", (int) 5, (int) contor);
	}

	@Test
	public void testComparators() {
		CarDto car1 = new CarDto();
		CarDto car2 = new CarDto();
		car1.pricePerDay = 12;
		car2.pricePerDay = 21;
		List<CarDto> carList = new ArrayList<CarDto>();
		carList.add(car1);
		carList.add(car2);
		DynamicCarComparator d0 = new DynamicCarComparator(0);
		d0.compareBy(0);
		Collections.sort(carList, d0);
		car1 = (CarDto) carList.get(0);

		assertEquals("Comparator does not work as intended", (int) 12, (int) car1.pricePerDay);

		DynamicCarComparator d1 = new DynamicCarComparator(1);
		d1.compareBy(1);
		Collections.sort(carList, d1);
		car1 = (CarDto) carList.get(0);
		assertEquals("Comparator does not work as intended", (int) 21, (int) car1.pricePerDay);
	}

	@Test
	public void testModel() {

		Car car = new Car();
		Agency agency = new Agency();

		agency.setId((long) 1);
		agency.setName("travis");
		agency.setAddress("22-Bacau");
		List<Car> carList = new ArrayList<Car>();
		carList.add(car);
		agency.setCars(carList);

		car.setId((long) 2);
		car.setType("2-roti");
		car.setModel("BMW");
		car.setNumberOfSeats(2);
		car.setPricePerDay(42);
		car.setAvailability(true);
		car.setAgency(agency);

		assertEquals("Model does not work as intended", (int) 1, (long) agency.getId());
		assertEquals("Model does not work as intended", "travis", (String) agency.getName());
		assertEquals("Model does not work as intended", "22-Bacau", (String) agency.getAddress());

		assertEquals("Model does not work as intended", (int) 2, (long) car.getId());
		assertEquals("Model does not work as intended", "2-roti", (String) car.getType());
		assertEquals("Model does not work as intended", "BMW", (String) car.getModel());
		assertEquals("Model does not work as intended", (int) 2, (long) car.getNumberOfSeats());
		assertEquals("Model does not work as intended", (int) 42, (long) car.getPricePerDay());
		assertEquals("Model does not work as intended", true, (boolean) car.getAvailability());

	}

	@Test
	public void testControllers() {
		CreatingAgencyDto dto = new CreatingAgencyDto();
		dto.name = "CarRental-name";
		dto.address = "CarRental-address";

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			byte[] bytes = objectMapper.writeValueAsBytes(dto);
			this.mockMvc.perform(post("/agencies").contentType(APPLICATION_JSON_UTF8).content(bytes));

			this.mockMvc.perform(get("/agencies")).andExpect(status().isOk()).andDo(print());
			this.mockMvc.perform(get("/agencies/1")).andExpect(status().isOk());
		} catch (Exception e) {
			fail("Error, Agency controller not working");
			e.printStackTrace();
		}

		CreatingCarDto dto_ = new CreatingCarDto();
		dto_.type = "carRental-type";
		dto_.model = "carRental-model";
		dto_.numberOfSeats = 1;
		dto_.pricePerDay = 1;
		dto_.availability = true;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			byte[] bytes = objectMapper.writeValueAsBytes(dto_);
			this.mockMvc.perform(post("/cars/{agencyId}/cars", "1").contentType(APPLICATION_JSON_UTF8).content(bytes));

			this.mockMvc.perform(get("/cars/{agencyId}/cars?page=1&pageSize=1&pricePerDay-ord=asc&pricePerDay-ord=desc&pricePerDay-max=5&pricePerDay-min=1&pricePerDay=5", "1")).andExpect(status().isOk()).andDo(print());

		} catch (Exception e) {
			fail("Error, Car controller not working");
			e.printStackTrace();
		}
	}
}
