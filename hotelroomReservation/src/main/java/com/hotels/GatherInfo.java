package com.hotelsamdrooms;

import com.hotelsamdrooms.models.Hotel;
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


    public void gatherInfo(String name,String adr,float categ, String phone, String descrip, List<String> faci, List<String> roomsss ) {

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://127.0.0.1:8081/hotels");

        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("Accept", "application/json");
        JSONObject obj = new JSONObject();
        if(adr.isEmpty())
            adr=" ";
        obj.put("name", name);
        obj.put("address", adr);
        obj.put("phone", phone);
        obj.put("category", categ);
        obj.put("description", descrip);
        obj.put("facilities", faci);
        obj.put("rooms",roomsss);
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


        try {
            List<String> hotelList = new ArrayList<>();
            String hotel1 = "http://www.travelro.ro/hotel-eden_iasi.html";
            String hotel2 = "http://www.travelro.ro/hotel-tudor-palace_iasi.html";
            String hotel3="http://www.travelro.ro/hotel-premier-class_iasi.html";
            String hotel4 = "http://www.travelro.ro/hotel-unirea_iasi.html";
            String hotel5 ="http://www.travelro.ro/hotel-europa_iasi.html";
            String hotel6 = "http://www.travelro.ro/hotel-moldova_iasi.html";
            String hotel7 = "http://www.travelro.ro/hotel-studis_iasi.html";
            String hotel8 = "http://www.travelro.ro/hotel-pensiunea-alessia_suceava.html";
            String hotel9 = "http://www.travelro.ro/hotel-rubin_gura-humorului.html";
            hotelList.add(hotel1);
            hotelList.add(hotel2);
            hotelList.add(hotel3);
            hotelList.add(hotel4);
            hotelList.add(hotel5);
            hotelList.add(hotel6);
            hotelList.add(hotel7);
            hotelList.add(hotel8);
            hotelList.add(hotel9);

            for (int i = 0; i < hotelList.size(); i++) {

                Document doc = Jsoup.connect(hotelList.get(i)).get();
                Elements facilities = doc.getElementsByTag("img");
                Elements hotelName = doc.select("#overview > div:nth-child(1) > div.hotel_name_left.paddingleft2.marginbottom4 > h2 > span:nth-child(1)");
                String hotelN = hotelName.text();
                Elements hotelAdress = doc.select("#overview > div:nth-child(1) > div.hotel_name_left.paddingleft2.marginbottom4 > div > span > span");
                String hotelAdr = hotelAdress.text();
                Elements hotelPhone = doc.select("body > div.layout_wrapper > div.header_wrapper > div > div.phone_wrapper > div > a");
                String hotelPh = "0317.146.835";
                Elements hotelDescription = doc.select("#overview > div.hotel_info.page_hotel_pad > div.summary.description");
                String hotelDesc=hotelDescription.text();
                Elements score = doc.select("#hotel_rating > div.client_hotel_grade > a > span > span > span:nth-child(1)");
                float scoree=0;
                if(score.hasText()){
                 scoree = Float.parseFloat(score.text());}
                 else{
                    scoree=0;
                }
                ArrayList<String> fac = new ArrayList<>();
                for (Element facility : facilities) {
                    String l = facility.attr("data-title");
                    if (l.length() > 1) {
                        fac.add(l);
                    }
                }
                System.out.println(fac);
                ArrayList<String> roomsss = new ArrayList<>();
                Elements rooms = doc.select("#overview > div.hotel_oferta > div > table");
                if(rooms.hasText()) {
                    Elements rows = doc.select("#overview > div.hotel_oferta > div > table > tbody > tr");
                    for (int j = 1; j < rows.size(); j++) {
                        Element row = rows.get(j);
                        Elements type = row.select("td");
                        String typee = type.get(0).text();
                        String nr = type.get(1).text();
                        String price = type.get(2).text();
                        String info = "Type of the room: " + typee + " Number of beds: " + nr + " Price:  " + price;
                        roomsss.add(info);
                    }
                }
                else{
                    String noData="No data to display! Contact the hotel for more information!";
                    roomsss.add(noData);
                }
                gh.gatherInfo(hotelN, hotelAdr, scoree, hotelPh, hotelDesc, fac, roomsss);
            }
            List<String> hotelListMoldova = new ArrayList<>();
            String hotelMd1 = "http://www.hotels.md/ro/chisinau-hotel/jumbo/";
            String hotelMd2 = "http://www.hotels.md/ro/chisinau-hotel/club-royal-park/";
            String hotelMd3 = "http://www.hotels.md/ro/chisinau-hotel/nobil/";
            String hotelMd4 = "http://www.hotels.md/ro/chisinau-hotel/SAVOY/";
            String hotelMd5 = "http://www.hotels.md/ro/chisinau-hotel/edem-hotel/";
            hotelListMoldova.add(hotelMd1);
            hotelListMoldova.add(hotelMd2);
            hotelListMoldova.add(hotelMd3);
            hotelListMoldova.add(hotelMd4);
            hotelListMoldova.add(hotelMd5);

            for (int i = 0; i < hotelListMoldova.size(); i++) {

                Document doc = Jsoup.connect(hotelListMoldova.get(i)).get();
                Elements hotelNameM = doc.select("#main > h1");
                String hotelNM = hotelNameM.text();
                System.out.println(hotelNM);
                Elements hotelAdressM = doc.select("#main > p");
                String hotelAdrM = hotelAdressM.text();
                System.out.println(hotelAdrM);
                Elements hotelPhoneM = doc.select("#hotAdd > p.contacts");
                String hotelPhM = hotelPhoneM.text();
                System.out.println(hotelPhM);
                Elements hotelDescriptionM = doc.select("#infoBasic > p");
                String hotelDescM = hotelDescriptionM.text();
                System.out.println(hotelDescM);
                Elements scoreM = doc.select("#hotAdd > p:nth-child(5) > span:nth-child(3)");
                float scoreeM = Float.parseFloat(scoreM.text());
                System.out.println(scoreeM);
                Elements facilitiesM = doc.select("#infoAmenities > ul > li");
                ArrayList<String> facM = new ArrayList<>();
                for (Element facil : facilitiesM) {
                    String lM = facil.text();
                    System.out.println(lM);
                    facM.add(lM);
                }
                ArrayList<String> roomsssM = new ArrayList<>();
                Elements roomsM = doc.select("#infoPrices > table");
                if (roomsM.hasText()) {
                    Elements rowsM = doc.select("#infoPrices > table > tbody > tr");
                    for (int j = 1; j < rowsM.size(); j++) {
                        Element rowM = rowsM.get(j);
                        Elements typeM = rowM.select("td");
                        String typeeM = typeM.get(0).text();
                        String priceM = typeM.get(2).text();
                        String infoM = "Type of the room: " + typeeM  + " Price:  " + priceM;
                        System.out.println(infoM);

                        roomsssM.add(infoM);
                    }
                } else {
                    String noDataM = "No data to display! Contact the hotel for more information!";
                    roomsssM.add(noDataM);
                }
                gh.gatherInfo(hotelNM, hotelAdrM, scoreeM, hotelPhM, hotelDescM, facM, roomsssM);
            }
        } catch (Exception e) {
            System.err.println("Eroare de procesare DOM: ");
            e.printStackTrace();
        }
    }
    }
