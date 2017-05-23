package carRental;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


public class GatherInfo {

	public static void gatherTravis(String type, String model, Integer pricePerDay) {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://127.0.0.1:8081/cars/1/cars");

		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json");
		JSONObject obj = new JSONObject();

		obj.put("availability", true);
		obj.put("model", model);
		obj.put("numberOfSeats", 4);
		obj.put("pricePerDay", pricePerDay);
		obj.put("type", type);
		System.out.println(obj.toString());

		httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
		//httppost.setEntity(obj.toString());
		try {
			HttpResponse response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void gatherTravis() {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://127.0.0.1:8081/agencies");

		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json");
		JSONObject obj = new JSONObject();

		obj.put("address", "var");
		obj.put("name", "travis");
		httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
		try {
			HttpResponse response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void gather() throws Exception{
		GatherInfo gh = new GatherInfo();
		gh.gatherTravis();

		try {
			Document doc = Jsoup.connect("http://www.travis.ro/rentacar/flota-auto/c1#suv").get();
			Elements links =  (doc).select(".col-md-3");
			for (Element link : links) {
			  String masina = link.select("h4 span").text();
			  masina = masina.replaceAll("(Nou)","");
			  //masina = masina.replaceAll("(NOU)","");
			  //masina = masina.replaceAll("()","");
			  String cost = link.select("h4 a").text();
			  cost = cost.replaceAll("[^\\d.]", "");
			  if(masina.length()>5 && Integer.parseInt(cost)>0 )
			  {
				  //System.out.println(
						//"Masina " + masina + " " + Integer.parseInt(cost) );
				  gh.gatherTravis("suv", masina, Integer.parseInt(cost));
			  }
			}
		} catch (Exception e) {
			System.err.println("Eroare de procesare DOM: ");
			e.printStackTrace();
			throw e;
		}

		try {
			Document doc = Jsoup.connect("http://www.travis.ro/rentacar/flota-auto/c1#economic").get();
			Elements links =  (doc).select(".col-md-3");
			for (Element link : links) {
			  String masina = link.select("h4 span").text();
			  masina = masina.replaceAll("(Nou)","");
			  String cost = link.select("h4 a").text();
			  cost = cost.replaceAll("[^\\d.]", "");
			  if(masina.length()>5 && Integer.parseInt(cost)>0 )
			  {
				  //System.out.println(
						//"Masina " + masina + " " + Integer.parseInt(cost) );
				  gh.gatherTravis("economic", masina, Integer.parseInt(cost));
			  }
			}
		} catch (Exception e) {
			System.err.println("Eroare de procesare DOM: ");
			e.printStackTrace();
		}

		try {
			Document doc = Jsoup.connect("http://www.travis.ro/rentacar/flota-auto/c1#mini").get();
			Elements links =  (doc).select(".col-md-3");
			for (Element link : links) {
			  String masina = link.select("h4 span").text();
			  masina = masina.replaceAll("(Nou)","");
			  masina = masina.replaceAll("(NOU)","");
			  String cost = link.select("h4 a").text();
			  cost = cost.replaceAll("[^\\d.]", "");
			  if(masina.length()>5 && Integer.parseInt(cost)>0 )
			  {
				  //System.out.println(
						//"Masina " + masina + " " + Integer.parseInt(cost) );
				  gh.gatherTravis("mini", masina, Integer.parseInt(cost));
			  }
			}
		} catch (Exception e) {
			System.err.println("Eroare de procesare DOM: ");
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) throws Exception {
		try {
			gather();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
