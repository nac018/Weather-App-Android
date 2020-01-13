package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class DetailActivity extends AppCompatActivity {


    private ImageView twitter;
    private TextView detailCity;
    private JSONObject jsonObj;
    private String cityText;
    private String cityOnly;

    private RequestQueue myQueue;
    public static ImageLoader mImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        myQueue = Volley.newRequestQueue(this);

        mImageLoader = new ImageLoader(myQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    tabs.getTabAt(0).getIcon().setColorFilter(getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(1).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(2).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                }
                else if(position == 1){
                    tabs.getTabAt(0).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(1).getIcon().setColorFilter(getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(2).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                }
                else{
                    tabs.getTabAt(0).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(1).getIcon().setColorFilter(getColor(R.color.colorGray), PorterDuff.Mode.SRC_IN);
                    tabs.getTabAt(2).getIcon().setColorFilter(getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabs.getTabAt(1).setIcon(R.drawable.trending_up);
        tabs.getTabAt(2).setIcon(R.drawable.google_photos);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twitter = findViewById(R.id.twitter);

        detailCity = findViewById(R.id.detailCity);

        Intent intent = getIntent();
        cityText = intent.getStringExtra("cityText");
        cityOnly = intent.getStringExtra("cityOnly");
        detailCity.setText(cityText);

        String jsonStr = intent.getStringExtra("jsonStr");
        sectionsPagerAdapter.jsonStr = jsonStr;
        try {
            jsonObj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temperature = "";
                try {
                    float temp = Float.parseFloat(jsonObj.getJSONObject("currently").getString("temperature"));
                    temperature = Integer.toString(Math.round(temp));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String text = "Check Out " + cityText + "'s Weather! It is " + temperature + "°F! %23CSCI571WeatherSearch";
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://twitter.com/intent/tweet?text=" + text)));
            }
        });

        String url = "https://weatherapp-csci571.appspot.com/seal?city=" + cityOnly;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    sectionsPagerAdapter.imageArr = new String[8];
                    for (int i = 0; i < sectionsPagerAdapter.imageArr.length; i++){
                        sectionsPagerAdapter.imageArr[i] = response.getJSONArray("items").getJSONObject(i).getString("link");
                    }

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
}