package airportmanagement;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//import org.json.JSONObject;
public class AirportGatherInfo {

    public static void main(String argv[]) {
        AirportGatherInfo gth = new AirportGatherInfo();
        gth.mainGather();


    }

    public void gatherObject(String day, String flightNumber, String company, String destination, String city, String departureHour, String arrivalHour,  String airport) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(("http://127.0.0.1:8081/airports/" + airport + "/flights"));

        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("Accept", "application/json");
        JSONObject obj = new JSONObject();


        try {
            obj.put("day", day);
            obj.put("flightNumber", flightNumber);
            obj.put("company", company);
            obj.put("arrivalCity", destination);
            obj.put("departureCity", city);
            obj.put("departureHour", departureHour);
            obj.put("arrivalHour", arrivalHour);
            httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gatherAirport(String location) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://127.0.0.1:8081/airports");

        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("Accept", "application/json");
        JSONObject obj = new JSONObject();


        try {
            obj.put("location", location);
            obj.put("name", location);
            httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void mainGather() {

        gatherAirport("suceava");

        gatherAirport("chisinau");

        gatherAirport("bacau");
        gatherAirport("iasi");
        suceavaGather();
        chisinauGather();
        bacauGather();
        iasiGather();
    }

    private void iasiGather() {

        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath);
        JSONObject obj;

        FileReader fileReader = null;
        try {
            fileReader = new FileReader( filePath +"\\src\\main\\resources\\iasi.json");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while((line = bufferedReader.readLine()) != null) {

                obj = (JSONObject) new JSONParser().parse(line);
                gatherObject(obj.get("day").toString(), obj.get("flightNumber").toString(),obj.get("company").toString(),obj.get("arrivalCity").toString(),obj.get("departureCity").toString(),obj.get("departureHour").toString(),obj.get("arrivalHour").toString(),  "iasi");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        g
    }

    private void bacauGather() {
        try {
            Document doc = Jsoup.connect("http://bacauairport.ro/").get();
            Elements parti;
            parti = (doc).select(".vc_tta-panel");
            int nr = 0;
            Elements linii;
            for (Element parte : parti) {
                nr++;
                if (nr <= 7) {
                    String day = parte.select("span").text();
                    linii = parte.select(" tbody tr");
                    for (Element linie : linii) {
                        String departureHour = linie.select("td:eq(2) h6:eq(0)").text();
                        departureHour = departureHour.substring(15);
                        String arrivalHour = linie.select("td:eq(3) h6:eq(0)").text();
                        if (arrivalHour.isEmpty())
                            arrivalHour = linie.select("td:eq(3) h6:eq(0) strong").text();

                        if (departureHour.isEmpty())
                            departureHour = linie.select("td:eq(3) h6:eq(1)").text();
                        if (arrivalHour.length() > 17)
                            arrivalHour = arrivalHour.substring(17);
                        else
                            arrivalHour=arrivalHour.substring(15);
                        departureHour = departureHour.substring(1);
                        String arrivalCity = linie.select("td:eq(0) h6:eq(1)").text();
                        if (arrivalCity.isEmpty()) {
                            arrivalCity = linie.select("td:eq(0) p:eq(1)").text();
                        }
                        String departureCity = "Bacau";
                        String company = linie.select("td:eq(5) h6:eq(0)").text();

                        if (company.length() > 10)
                            company = company.substring(11);
                        String flightNumber = linie.select("td:eq(1) h6:eq(0)").text();
                        flightNumber = flightNumber.substring(10);

                        gatherObject(day, flightNumber, company, arrivalCity, departureCity, departureHour, arrivalHour, "bacau");
                    }
                } else {
                    String day = parte.select("span").text();
                    linii = parte.select(" tbody tr");
                    for (Element linie : linii) {
                        String departureHour = linie.select("td:eq(2) h6:eq(0)").text();
                        if (departureHour.length() > 15)
                            departureHour = departureHour.substring(15);
                        String arrivalHour = linie.select("td:eq(3) h6:eq(1)").text();
                        if(arrivalHour.isEmpty())
                            arrivalHour=linie.select("td:eq(3) h6:eq(0)").text();
                        if (arrivalHour.length() > 16)
                            arrivalHour = arrivalHour.substring(16);
                        String arrivalCity = "Bacau";
                        String departureCity = linie.select("td:eq(0) p:lt(2)").text();

                        String company = linie.select("td:eq(5) h6:eq(0)").text();
                        if (company.length() > 10)
                            company = company.substring(11);
                        String flightNumber = linie.select("td:eq(1) h6:eq(0)").text();
                        flightNumber = flightNumber.substring(10);

                        gatherObject(day, flightNumber, company, arrivalCity, departureCity, departureHour, arrivalHour,  "bacau");
                    }
                }
            }


        } catch (Exception e) {
            System.err.println("Eroare de procesare DOM: ");
            e.printStackTrace();
        }
    }

    private void suceavaGather() {
        try {
            Document doc = Jsoup.connect("http://www.aeroportsuceava.ro/ro/").get();
            Elements linii;
            for (int i = 1; i <= 2; i++) {

                if (i == 1)
                    linii = (doc).select("#panel1  tbody  tr");
                else
                    linii = (doc).select("#panel2  tbody  tr");

                for (Element linie : linii) {
                    String day = linie.select("td:eq(0) ").text();
                    String departureHour = linie.select("td:eq(6) ").text();
                    String arrivalHour = linie.select("td:eq(7)").text();
                    String arrivalCity = linie.select("td:eq(5)").text();
                    String departureCity = linie.select("td:eq(4)").text();
                    String company = linie.select("td:eq(2) a").text();
                    String flightNumber = linie.select("td:eq(1)").text();

                    gatherObject(day, flightNumber, company, arrivalCity, departureCity, departureHour, arrivalHour,  "suceava");

                }

            }
        } catch (Exception e) {
            System.err.println("Eroare de procesare DOM: ");
            e.printStackTrace();
        }
    }

    private void chisinauGather() {
        try {
            Document doc = Jsoup.connect("http://www.airport.md/full-flight-schedule-ro/").get();
            Elements linii = (doc).select("table#full_flight_schedule  tbody  tr");
            for (Element linie : linii) {  //sosiri
                String day = linie.select("td:eq(1) ").text();
                String departureHour = linie.select("td:eq(6) ").text();
                String arrivalHour = linie.select("td:eq(7)").text();
                String arrivalCity = "Chisinau";
                String departureCity = linie.select("td:eq(0)").text();
                String company = " ";
                String flightNumber = linie.select("td:eq(3)").text();

                gatherObject(day, flightNumber, company, arrivalCity, departureCity, departureHour, arrivalHour,  "chisinau");
            }
            for (Element linie : linii) {   //plecari
                String day = linie.select("td:eq(1) ").text();
                String departureHour = linie.select("td:eq(4) ").text();
                String arrivalHour = linie.select("td:eq(5)").text();
                String arrivalCity = linie.select("td:eq(0)").text();
                String departureCity = "Chisinau";
                String company = " ";
                String flightNumber = linie.select("td:eq(3)").text();

                gatherObject(day, flightNumber, company, arrivalCity, departureCity, departureHour, arrivalHour, "chisinau");
            }
        } catch (Exception e) {
            System.err.println("Eroare de procesare DOM: ");
            e.printStackTrace();
        }
    }
}
