package com.example.weatherapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class FragmentCurrent extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static TextView cityText;
    private static String jsonStr;
    private static TextView currentTemperature;
    private static TextView currentSummary;
    private static ImageView currentIcon;
    private static TextView currentHumidity;
    private static TextView currentWindSpeed;
    private static TextView currentVisibility;
    private static TextView currentPressure;
    private static TextView day0Date;
    private static ImageView day0Image;
    private static TextView day0TLow;
    private static TextView day0THigh;
    private static TextView day1Date;
    private static ImageView day1Image;
    private static TextView day1TLow;
    private static TextView day1THigh;
    private static TextView day2Date;
    private static ImageView day2Image;
    private static TextView day2TLow;
    private static TextView day2THigh;
    private static TextView day3Date;
    private static ImageView day3Image;
    private static TextView day3TLow;
    private static TextView day3THigh;
    private static TextView day4Date;
    private static ImageView day4Image;
    private static TextView day4TLow;
    private static TextView day4THigh;
    private static TextView day5Date;
    private static ImageView day5Image;
    private static TextView day5TLow;
    private static TextView day5THigh;
    private static TextView day6Date;
    private static ImageView day6Image;
    private static TextView day6TLow;
    private static TextView day6THigh;
    private static TextView day7Date;
    private static ImageView day7Image;
    private static TextView day7TLow;
    private static TextView day7THigh;
    private static RequestQueue myQueue;
    private static CardView currentCard1;
    public static String cityOnly;
    public static String cityString;

    public static JSONObject jsonObj;

    //public static ConstraintLayout progress;
    //public static ConstraintLayout currentView;

    public FragmentCurrent() {
        // Required empty public constructor
    }

    public static FragmentCurrent newInstance() {
        FragmentCurrent fragment = new FragmentCurrent();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myQueue = Volley.newRequestQueue(MainActivity.context);
        //jsonParse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        cityText = getView().findViewById(R.id.cityText);
        currentTemperature = getView().findViewById(R.id.currentTemperature);
        currentSummary = getView().findViewById(R.id.currentSummary);
        currentIcon = getView().findViewById(R.id.iconImage);
        currentHumidity = getView().findViewById(R.id.humidity);
        currentWindSpeed = getView().findViewById(R.id.windSpeed);
        currentVisibility = getView().findViewById(R.id.visibility);
        currentPressure = getView().findViewById(R.id.pressure);
        day0Date = getView().findViewById(R.id.day0Date);
        day0Image = getView().findViewById(R.id.day0Image);
        day0TLow = getView().findViewById(R.id.day0TLow);
        day0THigh = getView().findViewById(R.id.day0THigh);
        day1Date = getView().findViewById(R.id.day1Date);
        day1Image = getView().findViewById(R.id.day1Image);
        day1TLow = getView().findViewById(R.id.day1TLow);
        day1THigh = getView().findViewById(R.id.day1THigh);
        day2Date = getView().findViewById(R.id.day2Date);
        day2Image = getView().findViewById(R.id.day2Image);
        day2TLow = getView().findViewById(R.id.day2TLow);
        day2THigh = getView().findViewById(R.id.day2THigh);
        day3Date = getView().findViewById(R.id.day3Date);
        day3Image = getView().findViewById(R.id.day3Image);
        day3TLow = getView().findViewById(R.id.day3TLow);
        day3THigh = getView().findViewById(R.id.day3THigh);
        day4Date = getView().findViewById(R.id.day4Date);
        day4Image = getView().findViewById(R.id.day4Image);
        day4TLow = getView().findViewById(R.id.day4TLow);
        day4THigh = getView().findViewById(R.id.day4THigh);
        day5Date = getView().findViewById(R.id.day5Date);
        day5Image = getView().findViewById(R.id.day5Image);
        day5TLow = getView().findViewById(R.id.day5TLow);
        day5THigh = getView().findViewById(R.id.day5THigh);
        day6Date = getView().findViewById(R.id.day6Date);
        day6Image = getView().findViewById(R.id.day6Image);
        day6TLow = getView().findViewById(R.id.day6TLow);
        day6THigh = getView().findViewById(R.id.day6THigh);
        day7Date = getView().findViewById(R.id.day7Date);
        day7Image = getView().findViewById(R.id.day7Image);
        day7TLow = getView().findViewById(R.id.day7TLow);
        day7THigh = getView().findViewById(R.id.day7THigh);
        currentCard1 = getView().findViewById(R.id.currentCard1);

        currentCard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetailActivity();
            }
        });

        if(jsonObj != null){
            setJSON();
        }
    }

    public void openDetailActivity(){
        Intent intent = new Intent(MainActivity.context,DetailActivity.class);
        intent.putExtra("cityText",cityText.getText());
        intent.putExtra("jsonStr",jsonStr);
        intent.putExtra("cityOnly",cityOnly);
        startActivity(intent);
    }


    private void jsonParse(){
        String url = "http://ip-api.com/json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("city");
                    cityOnly = city;
                    String state = response.getString("region");
                    String country = response.getString("countryCode");
                    String lat = response.getString("lat");
                    String lon = response.getString("lon");
                    String cityString = city + ", " + state + ", " + country;
                    cityText.setText(cityString);
                    getWeather(lat,lon);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue.add(jsonObjectRequest);
    }

    private void getWeather(String lat, String lon){
        String url = "https://weatherapp-csci571.appspot.com/current?lat=" + lat + "&lng=" + lon;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonObj = response;
                    jsonStr = response.toString();
                    float temp = Float.parseFloat(response.getJSONObject("currently").getString("temperature"));
                    String temperature = Integer.toString(Math.round(temp));
                    currentTemperature.setText(temperature+"°F");
                    String summary = response.getJSONObject("currently").getString("summary");
                    currentSummary.setText(summary);
                    String weatherIcon = response.getJSONObject("currently").getString("icon");
                    switch (weatherIcon){
                        case "clear-day":
                            currentIcon.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            currentIcon.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            currentIcon.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            currentIcon.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            currentIcon.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            currentIcon.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            currentIcon.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            currentIcon.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            currentIcon.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            currentIcon.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            currentIcon.setImageResource(R.drawable.weather_sunny);
                    }
                    float humidity = Math.round(Float.parseFloat(response.getJSONObject("currently").getString("humidity"))*10000);
                    humidity /= 100;
                    currentHumidity.setText(Float.toString(humidity) + "%");
                    float windSpeed =  Math.round(Float.parseFloat(response.getJSONObject("currently").getString("windSpeed"))*100);
                    windSpeed /= 100;
                    currentWindSpeed.setText(Float.toString(windSpeed) + " mph");
                    float visibility =  Math.round(Float.parseFloat(response.getJSONObject("currently").getString("visibility"))*100);
                    visibility /= 100;
                    currentVisibility.setText(Float.toString(visibility) + " km");
                    float pressure =  Math.round(Float.parseFloat(response.getJSONObject("currently").getString("pressure"))*100);
                    pressure /= 100;
                    currentPressure.setText(Float.toString(pressure) + " mb");

                    String time0 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("time");
                    Date d0 = new Date(Long.parseLong(time0) * 1000);
                    Calendar cal0 = Calendar.getInstance();
                    cal0.setTime(d0);
                    String month0 = Integer.toString(cal0.get(Calendar.MONTH) + 1);
                    String day0 = Integer.toString(cal0.get(Calendar.DAY_OF_MONTH));
                    String year0 = Integer.toString(cal0.get(Calendar.YEAR));
                    if (month0.length() < 2){
                        month0 = "0" + month0;
                    }
                    if (day0.length() < 2){
                        day0 = "0" + day0;
                    }
                    String mmddyyyy0 = month0 + "/" + day0 + "/" + year0;
                    day0Date.setText(mmddyyyy0);
                    String icon0 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("icon");
                    switch (icon0){
                        case "clear-day":
                            day0Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day0Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day0Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day0Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day0Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day0Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day0Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day0Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day0Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day0Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day0Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow0 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureLow");
                    day0TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow0))));
                    String tHigh0 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureHigh");
                    day0THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh0))));



                    String time1 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("time");
                    Date d1 = new Date(Long.parseLong(time1) * 1000);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(d1);
                    String month1 = Integer.toString(cal1.get(Calendar.MONTH) + 1);
                    String day1 = Integer.toString(cal1.get(Calendar.DAY_OF_MONTH));
                    String year1 = Integer.toString(cal1.get(Calendar.YEAR));
                    if (month1.length() < 2){
                        month1 = "0" + month1;
                    }
                    if (day1.length() < 2){
                        day1 = "0" + day1;
                    }
                    String mmddyyyy1 = month1 + "/" + day1 + "/" + year1;
                    day1Date.setText(mmddyyyy1);
                    String icon1 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("icon");
                    switch (icon1){
                        case "clear-day":
                            day1Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day1Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day1Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day1Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day1Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day1Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day1Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day1Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day1Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day1Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day1Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow1 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureLow");
                    day1TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow1))));
                    String tHigh1 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureHigh");
                    day1THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh1))));




                    String time2 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("time");
                    Date d2 = new Date(Long.parseLong(time2) * 1000);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(d2);
                    String month2 = Integer.toString(cal2.get(Calendar.MONTH) + 1);
                    String day2 = Integer.toString(cal2.get(Calendar.DAY_OF_MONTH));
                    String year2 = Integer.toString(cal2.get(Calendar.YEAR));
                    if (month2.length() < 2){
                        month2 = "0" + month2;
                    }
                    if (day2.length() < 2){
                        day2 = "0" + day2;
                    }
                    String mmddyyyy2 = month2 + "/" + day2 + "/" + year2;
                    day2Date.setText(mmddyyyy2);
                    String icon2 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("icon");
                    switch (icon2){
                        case "clear-day":
                            day2Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day2Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day2Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day2Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day2Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day2Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day2Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day2Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day2Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day2Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day2Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow2 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureLow");
                    day2TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow2))));
                    String tHigh2 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureHigh");
                    day2THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh2))));





                    String time3 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("time");
                    Date d3 = new Date(Long.parseLong(time3) * 1000);
                    Calendar cal3 = Calendar.getInstance();
                    cal3.setTime(d3);
                    String month3 = Integer.toString(cal3.get(Calendar.MONTH) + 1);
                    String day3 = Integer.toString(cal3.get(Calendar.DAY_OF_MONTH));
                    String year3 = Integer.toString(cal3.get(Calendar.YEAR));
                    if (month3.length() < 2){
                        month3 = "0" + month3;
                    }
                    if (day3.length() < 2){
                        day3 = "0" + day3;
                    }
                    String mmddyyyy3 = month3 + "/" + day3 + "/" + year3;
                    day3Date.setText(mmddyyyy3);
                    String icon3 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("icon");
                    switch (icon3){
                        case "clear-day":
                            day3Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day3Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day3Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day3Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day3Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day3Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day3Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day3Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day3Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day3Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day3Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow3 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureLow");
                    day3TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow3))));
                    String tHigh3 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureHigh");
                    day3THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh3))));




                    String time4 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("time");
                    Date d4 = new Date(Long.parseLong(time4) * 1000);
                    Calendar cal4 = Calendar.getInstance();
                    cal4.setTime(d4);
                    String month4 = Integer.toString(cal4.get(Calendar.MONTH) + 1);
                    String day4 = Integer.toString(cal4.get(Calendar.DAY_OF_MONTH));
                    String year4 = Integer.toString(cal4.get(Calendar.YEAR));
                    if (month4.length() < 2){
                        month4 = "0" + month4;
                    }
                    if (day4.length() < 2){
                        day4 = "0" + day4;
                    }
                    String mmddyyyy4 = month4 + "/" + day4 + "/" + year4;
                    day4Date.setText(mmddyyyy4);
                    String icon4 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("icon");
                    switch (icon4){
                        case "clear-day":
                            day4Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day4Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day4Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day4Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day4Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day4Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day4Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day4Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day4Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day4Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day4Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow4 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureLow");
                    day4TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow4))));
                    String tHigh4 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureHigh");
                    day4THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh4))));




                    String time5 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("time");
                    Date d5 = new Date(Long.parseLong(time5) * 1000);
                    Calendar cal5 = Calendar.getInstance();
                    cal5.setTime(d5);
                    String month5 = Integer.toString(cal5.get(Calendar.MONTH) + 1);
                    String day5 = Integer.toString(cal5.get(Calendar.DAY_OF_MONTH));
                    String year5 = Integer.toString(cal5.get(Calendar.YEAR));
                    if (month5.length() < 2){
                        month5 = "0" + month5;
                    }
                    if (day5.length() < 2){
                        day5 = "0" + day5;
                    }
                    String mmddyyyy5 = month5 + "/" + day5 + "/" + year5;
                    day5Date.setText(mmddyyyy5);
                    String icon5 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("icon");
                    switch (icon5){
                        case "clear-day":
                            day5Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day5Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day5Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day5Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day5Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day5Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day5Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day5Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day5Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day5Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day5Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow5 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureLow");
                    day5TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow5))));
                    String tHigh5 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureHigh");
                    day5THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh5))));





                    String time6 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("time");
                    Date d6 = new Date(Long.parseLong(time6) * 1000);
                    Calendar cal6 = Calendar.getInstance();
                    cal6.setTime(d6);
                    String month6 = Integer.toString(cal6.get(Calendar.MONTH) + 1);
                    String day6 = Integer.toString(cal6.get(Calendar.DAY_OF_MONTH));
                    String year6 = Integer.toString(cal6.get(Calendar.YEAR));
                    if (month6.length() < 2){
                        month6 = "0" + month6;
                    }
                    if (day6.length() < 2){
                        day6 = "0" + day6;
                    }
                    String mmddyyyy6 = month6 + "/" + day6 + "/" + year6;
                    day6Date.setText(mmddyyyy6);
                    String icon6 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("icon");
                    switch (icon6){
                        case "clear-day":
                            day6Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day6Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day6Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day6Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day6Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day6Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day6Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day6Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day6Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day6Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day6Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow6 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureLow");
                    day6TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow6))));
                    String tHigh6 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureHigh");
                    day6THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh6))));





                    String time7 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("time");
                    Date d7 = new Date(Long.parseLong(time7) * 1000);
                    Calendar cal7 = Calendar.getInstance();
                    cal7.setTime(d7);
                    String month7 = Integer.toString(cal7.get(Calendar.MONTH) + 1);
                    String day7 = Integer.toString(cal7.get(Calendar.DAY_OF_MONTH));
                    String year7 = Integer.toString(cal7.get(Calendar.YEAR));
                    if (month7.length() < 2){
                        month7 = "0" + month7;
                    }
                    if (day7.length() < 2){
                        day7 = "0" + day7;
                    }
                    String mmddyyyy7 = month7 + "/" + day7 + "/" + year7;
                    day7Date.setText(mmddyyyy7);
                    String icon7 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("icon");
                    switch (icon7){
                        case "clear-day":
                            day7Image.setImageResource(R.drawable.weather_sunny);
                            break;
                        case "clear-night":
                            day7Image.setImageResource(R.drawable.weather_night);
                            break;
                        case "rain":
                            day7Image.setImageResource(R.drawable.weather_rainy);
                            break;
                        case "snow":
                            day7Image.setImageResource(R.drawable.weather_snowy);
                            break;
                        case "sleet":
                            day7Image.setImageResource(R.drawable.weather_snowy_rainy);
                            break;
                        case "wind":
                            day7Image.setImageResource(R.drawable.weather_windy_variant);
                            break;
                        case "fog":
                            day7Image.setImageResource(R.drawable.weather_fog);
                            break;
                        case "cloudy":
                            day7Image.setImageResource(R.drawable.weather_cloudy);
                            break;
                        case "partly-cloudy-day":
                            day7Image.setImageResource(R.drawable.weather_partly_cloudy);
                            break;
                        case "partly-cloudy-night":
                            day7Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                            break;
                        default:
                            day7Image.setImageResource(R.drawable.weather_sunny);
                    }
                    String tLow7 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("temperatureLow");
                    day7TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow7))));
                    String tHigh7 = response.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("temperatureHigh");
                    day7THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh7))));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        myQueue.add(jsonObjectRequest);

    }

    public static void setJSON(){
        try {
            cityText.setText(cityString);
            jsonStr = jsonObj.toString();
            float temp = Float.parseFloat(jsonObj.getJSONObject("currently").getString("temperature"));
            String temperature = Integer.toString(Math.round(temp));
            currentTemperature.setText(temperature+"°F");
            String summary = jsonObj.getJSONObject("currently").getString("summary");
            currentSummary.setText(summary);
            String weatherIcon = jsonObj.getJSONObject("currently").getString("icon");
            switch (weatherIcon){
                case "clear-day":
                    currentIcon.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    currentIcon.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    currentIcon.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    currentIcon.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    currentIcon.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    currentIcon.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    currentIcon.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    currentIcon.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    currentIcon.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    currentIcon.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    currentIcon.setImageResource(R.drawable.weather_sunny);
            }
            float humidity = Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("humidity"))*10000);
            humidity /= 100;
            currentHumidity.setText(Float.toString(humidity) + "%");
            float windSpeed =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("windSpeed"))*100);
            windSpeed /= 100;
            currentWindSpeed.setText(Float.toString(windSpeed) + " mph");
            float visibility =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("visibility"))*100);
            visibility /= 100;
            currentVisibility.setText(Float.toString(visibility) + " km");
            float pressure =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("pressure"))*100);
            pressure /= 100;
            currentPressure.setText(Float.toString(pressure) + " mb");

            String time0 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("time");
            Date d0 = new Date(Long.parseLong(time0) * 1000);
            Calendar cal0 = Calendar.getInstance();
            cal0.setTime(d0);
            String month0 = Integer.toString(cal0.get(Calendar.MONTH) + 1);
            String day0 = Integer.toString(cal0.get(Calendar.DAY_OF_MONTH));
            String year0 = Integer.toString(cal0.get(Calendar.YEAR));
            if (month0.length() < 2){
                month0 = "0" + month0;
            }
            if (day0.length() < 2){
                day0 = "0" + day0;
            }
            String mmddyyyy0 = month0 + "/" + day0 + "/" + year0;
            day0Date.setText(mmddyyyy0);
            String icon0 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("icon");
            switch (icon0){
                case "clear-day":
                    day0Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day0Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day0Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day0Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day0Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day0Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day0Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day0Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day0Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day0Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day0Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow0 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureLow");
            day0TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow0))));
            String tHigh0 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureHigh");
            day0THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh0))));



            String time1 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("time");
            Date d1 = new Date(Long.parseLong(time1) * 1000);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(d1);
            String month1 = Integer.toString(cal1.get(Calendar.MONTH) + 1);
            String day1 = Integer.toString(cal1.get(Calendar.DAY_OF_MONTH));
            String year1 = Integer.toString(cal1.get(Calendar.YEAR));
            if (month1.length() < 2){
                month1 = "0" + month1;
            }
            if (day1.length() < 2){
                day1 = "0" + day1;
            }
            String mmddyyyy1 = month1 + "/" + day1 + "/" + year1;
            day1Date.setText(mmddyyyy1);
            String icon1 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("icon");
            switch (icon1){
                case "clear-day":
                    day1Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day1Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day1Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day1Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day1Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day1Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day1Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day1Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day1Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day1Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day1Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow1 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureLow");
            day1TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow1))));
            String tHigh1 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureHigh");
            day1THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh1))));




            String time2 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("time");
            Date d2 = new Date(Long.parseLong(time2) * 1000);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(d2);
            String month2 = Integer.toString(cal2.get(Calendar.MONTH) + 1);
            String day2 = Integer.toString(cal2.get(Calendar.DAY_OF_MONTH));
            String year2 = Integer.toString(cal2.get(Calendar.YEAR));
            if (month2.length() < 2){
                month2 = "0" + month2;
            }
            if (day2.length() < 2){
                day2 = "0" + day2;
            }
            String mmddyyyy2 = month2 + "/" + day2 + "/" + year2;
            day2Date.setText(mmddyyyy2);
            String icon2 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("icon");
            switch (icon2){
                case "clear-day":
                    day2Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day2Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day2Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day2Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day2Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day2Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day2Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day2Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day2Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day2Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day2Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow2 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureLow");
            day2TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow2))));
            String tHigh2 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureHigh");
            day2THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh2))));





            String time3 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("time");
            Date d3 = new Date(Long.parseLong(time3) * 1000);
            Calendar cal3 = Calendar.getInstance();
            cal3.setTime(d3);
            String month3 = Integer.toString(cal3.get(Calendar.MONTH) + 1);
            String day3 = Integer.toString(cal3.get(Calendar.DAY_OF_MONTH));
            String year3 = Integer.toString(cal3.get(Calendar.YEAR));
            if (month3.length() < 2){
                month3 = "0" + month3;
            }
            if (day3.length() < 2){
                day3 = "0" + day3;
            }
            String mmddyyyy3 = month3 + "/" + day3 + "/" + year3;
            day3Date.setText(mmddyyyy3);
            String icon3 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("icon");
            switch (icon3){
                case "clear-day":
                    day3Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day3Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day3Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day3Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day3Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day3Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day3Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day3Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day3Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day3Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day3Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow3 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureLow");
            day3TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow3))));
            String tHigh3 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureHigh");
            day3THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh3))));




            String time4 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("time");
            Date d4 = new Date(Long.parseLong(time4) * 1000);
            Calendar cal4 = Calendar.getInstance();
            cal4.setTime(d4);
            String month4 = Integer.toString(cal4.get(Calendar.MONTH) + 1);
            String day4 = Integer.toString(cal4.get(Calendar.DAY_OF_MONTH));
            String year4 = Integer.toString(cal4.get(Calendar.YEAR));
            if (month4.length() < 2){
                month4 = "0" + month4;
            }
            if (day4.length() < 2){
                day4 = "0" + day4;
            }
            String mmddyyyy4 = month4 + "/" + day4 + "/" + year4;
            day4Date.setText(mmddyyyy4);
            String icon4 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("icon");
            switch (icon4){
                case "clear-day":
                    day4Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day4Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day4Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day4Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day4Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day4Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day4Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day4Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day4Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day4Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day4Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow4 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureLow");
            day4TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow4))));
            String tHigh4 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(4).getString("temperatureHigh");
            day4THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh4))));




            String time5 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("time");
            Date d5 = new Date(Long.parseLong(time5) * 1000);
            Calendar cal5 = Calendar.getInstance();
            cal5.setTime(d5);
            String month5 = Integer.toString(cal5.get(Calendar.MONTH) + 1);
            String day5 = Integer.toString(cal5.get(Calendar.DAY_OF_MONTH));
            String year5 = Integer.toString(cal5.get(Calendar.YEAR));
            if (month5.length() < 2){
                month5 = "0" + month5;
            }
            if (day5.length() < 2){
                day5 = "0" + day5;
            }
            String mmddyyyy5 = month5 + "/" + day5 + "/" + year5;
            day5Date.setText(mmddyyyy5);
            String icon5 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("icon");
            switch (icon5){
                case "clear-day":
                    day5Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day5Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day5Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day5Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day5Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day5Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day5Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day5Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day5Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day5Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day5Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow5 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureLow");
            day5TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow5))));
            String tHigh5 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(5).getString("temperatureHigh");
            day5THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh5))));





            String time6 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("time");
            Date d6 = new Date(Long.parseLong(time6) * 1000);
            Calendar cal6 = Calendar.getInstance();
            cal6.setTime(d6);
            String month6 = Integer.toString(cal6.get(Calendar.MONTH) + 1);
            String day6 = Integer.toString(cal6.get(Calendar.DAY_OF_MONTH));
            String year6 = Integer.toString(cal6.get(Calendar.YEAR));
            if (month6.length() < 2){
                month6 = "0" + month6;
            }
            if (day6.length() < 2){
                day6 = "0" + day6;
            }
            String mmddyyyy6 = month6 + "/" + day6 + "/" + year6;
            day6Date.setText(mmddyyyy6);
            String icon6 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("icon");
            switch (icon6){
                case "clear-day":
                    day6Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day6Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day6Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day6Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day6Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day6Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day6Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day6Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day6Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day6Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day6Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow6 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureLow");
            day6TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow6))));
            String tHigh6 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(6).getString("temperatureHigh");
            day6THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh6))));





            String time7 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("time");
            Date d7 = new Date(Long.parseLong(time7) * 1000);
            Calendar cal7 = Calendar.getInstance();
            cal7.setTime(d7);
            String month7 = Integer.toString(cal7.get(Calendar.MONTH) + 1);
            String day7 = Integer.toString(cal7.get(Calendar.DAY_OF_MONTH));
            String year7 = Integer.toString(cal7.get(Calendar.YEAR));
            if (month7.length() < 2){
                month7 = "0" + month7;
            }
            if (day7.length() < 2){
                day7 = "0" + day7;
            }
            String mmddyyyy7 = month7 + "/" + day7 + "/" + year7;
            day7Date.setText(mmddyyyy7);
            String icon7 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("icon");
            switch (icon7){
                case "clear-day":
                    day7Image.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    day7Image.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    day7Image.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    day7Image.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    day7Image.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    day7Image.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    day7Image.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    day7Image.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    day7Image.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    day7Image.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    day7Image.setImageResource(R.drawable.weather_sunny);
            }
            String tLow7 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("temperatureLow");
            day7TLow.setText(Integer.toString(Math.round(Float.parseFloat(tLow7))));
            String tHigh7 = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(7).getString("temperatureHigh");
            day7THigh.setText(Integer.toString(Math.round(Float.parseFloat(tHigh7))));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
