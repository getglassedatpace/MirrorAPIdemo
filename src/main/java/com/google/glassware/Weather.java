package com.google.glassware;

/**
 * Created by Diana Melara on 2/2/15.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class Weather {

    private final String units = "imperial";      //Imperial, metric
    private String cityName;
    private String weather;
    private String humidity;
    private String icon;
    private String temperature;
    private String windSpeed;

    public String getWeather(){
        return weather;
    }

    public String getHumidity(){
        return humidity;
    }

    public String getIcon(){
        return icon;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getWindSpeed(){
        return windSpeed;
    }

    public String getCityName(){
        return cityName;
    }



    public Weather(String zipCode){


        String s = "";
        try{
            s = "["+readUrl("http://api.openweathermap.org/data/2.5/find?q="+zipCode+",USA&units="+units)+"]";
        } catch(Exception e){

        }

        JSONParser parser = new JSONParser();

        try{

            Object obj = parser.parse(s);
            JSONArray array = (JSONArray)obj;

            JSONObject obj2 = (JSONObject)array.get(0);
            array = (JSONArray)(parser.parse(obj2.get("list").toString()));

            JSONObject obj3 = (JSONObject)( array.get(0));
            cityName = obj3.get("name").toString();

         /*---------------------weather---------------------*/
            array = (JSONArray)(parser.parse(obj3.get("weather").toString()));
            JSONObject obj4 = (JSONObject)array.get(0);

            weather = obj4.get("description").toString();
            icon = "http://openweathermap.org/img/w/"+obj4.get("icon")+".png";

         /*---------------------main---------------------*/
            String temp = "["+obj3.get("main").toString()+"]";
            array = (JSONArray)(parser.parse(temp));
            JSONObject obj6 = (JSONObject)array.get(0);

            humidity = obj6.get("humidity")+"%";
            temperature = obj6.get("temp")+" F";

         /*---------------------wind---------------------*/
            temp = "["+obj3.get("wind").toString()+"]";
            array = (JSONArray)(parser.parse(temp));
            JSONObject obj8 = (JSONObject)array.get(0);

            windSpeed = obj8.get("speed")+" mph";

        }catch(ParseException pe){
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }


    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }


}
