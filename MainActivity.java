package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private RequestQueue myQueue;
    public static ArrayList<String> acCities;
    public static Context context;
    private Map<String,?> map;
    private static JSONObject jsonObj;
    public static TabLayout tabs;
    public ArrayList<String> favCities;
    public ArrayList<String> favJSONs;
    public static ConstraintLayout progress;
    public static ConstraintLayout currentView;
    public static MainPagerAdapter mainPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        myQueue = Volley.newRequestQueue(this);


        SharedPreferences sharedPreferences = getSharedPreferences("favoriteCities",MODE_PRIVATE);
        map = sharedPreferences.getAll();

        favCities = new ArrayList<>();
        favJSONs = new ArrayList<>();
        progress = findViewById(R.id.progress);
        currentView = findViewById(R.id.currentView);

        jsonParse();

    }

    private void jsonParse(){
        String url = "http://ip-api.com/json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("city");
                    FragmentCurrent.cityOnly = city;
                    String state = response.getString("region");
                    String country = response.getString("countryCode");
                    String lat = response.getString("lat");
                    String lon = response.getString("lon");
                    FragmentCurrent.cityString = city + ", " + state + ", " + country;
                    //cityText.setText(cityString);
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
                FragmentCurrent.jsonObj = response;
                //FragmentCurrent.setJSON();
                progress.setVisibility(View.VISIBLE);
                currentView.setVisibility(View.GONE);
                if(map.size() == 0){
                    mainPagerAdapter = new MainPagerAdapter(context,getSupportFragmentManager(),map.size()+1);
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(mainPagerAdapter);
                    tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager,true);
                    progress.setVisibility(View.GONE);
                    currentView.setVisibility(View.VISIBLE);
                }
                else {
                    for (Map.Entry<String, ?> entry : map.entrySet()) {
                        Log.d("mapvalues", entry.getKey() + ": " + entry.getValue().toString());
                        favJsonParse(entry.getKey());
                    }
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

    public void favJsonParse(final String city){
        String url = "https://weatherapp-csci571.appspot.com/search?street=&city=" + city + "&state=";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                favCities.add(city);
                favJSONs.add(response.toString());
                if(favCities.size() == map.size()){
                    mainPagerAdapter = new MainPagerAdapter(context,getSupportFragmentManager(),map.size()+1);
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(mainPagerAdapter);
                    tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager,true);
                    progress.setVisibility(View.GONE);
                    currentView.setVisibility(View.VISIBLE);
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



    private void openSearchActivity(String query){
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("fullCity",query);
        Log.d("fullCity",query);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_GO);

        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(getColor(R.color.colorCard));
        searchAutoComplete.setTextColor(getColor(R.color.colorWhite));
        searchAutoComplete.setDropDownBackgroundResource(R.color.colorWhite);
        searchAutoComplete.setThreshold(1);
        final AutoCompleteAdapter arrayAdapter = new AutoCompleteAdapter(context,android.R.layout.simple_dropdown_item_1line);
        searchAutoComplete.setAdapter(arrayAdapter);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSearchActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String url = "http://weatherapp-csci571.appspot.com/autocomplete?cityInput=" + newText;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            acCities = new ArrayList<>();

                            for(int i = 0; i < response.getJSONArray("predictions").length(); i++){
                                acCities.add(response.getJSONArray("predictions").getJSONObject(i).getString("description"));
                            }

                            arrayAdapter.setData(acCities);
                            arrayAdapter.notifyDataSetChanged();

                            searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String queryString = (String) adapterView.getItemAtPosition(i);
                                    searchAutoComplete.setText("" + queryString);
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },  new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                myQueue.add(jsonObjectRequest);

                return false;
            }
        });
        return true;
    }

    class MainPagerAdapter extends FragmentStatePagerAdapter {
        int numOfTabs;
        Context context;
        public MainPagerAdapter (Context context, FragmentManager fm, int numOfTabs){
            super(fm);
            this.numOfTabs = numOfTabs;
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Log.d("ab",favCities.toString());
            switch (position) {
                case 0:
                    fragment = FragmentCurrent.newInstance();
                    break;
                default:
                    fragment = FragmentDynamic.newInstance(position,favCities,favJSONs);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }

        public void removeTabPage(int position) {
            numOfTabs--;
            favCities.remove(position - 1);
            favJSONs.remove(position - 1);
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }
}
