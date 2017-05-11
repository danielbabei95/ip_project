
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.net.URL;
import java.nio.charset.Charset;


import org.json.JSONArray;
import org.json.JSONException;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class JsonFilterTest {

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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(true);
		int contor = 0;
		try {

			InputStream is = new URL("http://127.0.0.1:8081/v1/cars/1/cars?page=1&pageSize=5").openStream();
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

		if (contor != 5)
			fail("Paging doesn't work or there are no objects in database " + contor);
	}

}
