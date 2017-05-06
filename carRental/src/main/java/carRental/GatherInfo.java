package carRental;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

	public static void gatherTravis(String type, String model, String pricePerDay) {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://127.0.0.1:8081/v1/cars/1/cars");

		httppost.setHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json");
		JSONObject obj = new JSONObject();

		obj.put("type", type);
		obj.put("model", model);
		obj.put("pricePerDay", "22");
		obj.put("availability", true);
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

	public void gatherTravis() {

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://127.0.0.1:8081/v1/agencies");

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

	public static void main(String argv[]) {
		GatherInfo gh = new GatherInfo();
		gh.gatherTravis();

		try {
			Document doc = Jsoup.connect("http://www.travis.ro/rentacar/flota-auto/c1#suv").get();
			//Elements links =  (doc).select(".col-md-3 h4 a");
			//Elements links =  (doc).select(".col-md-3 h4 span");
			Elements links =  (doc).select(".col-md-3");
			for (Element link : links) {
			  String masina = link.select("h4 span").text();
			  String cost = link.select("h4 a").text();
			  cost = cost.replaceAll("[^\\d.]", "");
			  if(masina.length()>5 && cost.length()>1)
			  System.out.println(
						"Masina " + masina + " " + cost );
			  //gh.gatherTravis("suv", masina, cost);
			}
		} catch (Exception e) {
			System.err.println("Eroare de procesare DOM: ");
			e.printStackTrace();
		}
	}
}
