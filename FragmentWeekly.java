package com.example.weatherapp;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentWeekly.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentWeekly#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWeekly extends Fragment{

    private String jsonStr;

    private ImageView weeklyIcon;
    private  TextView weeklySummary;

    private LineChart lineChart;

    private OnFragmentInteractionListener mListener;

    public FragmentWeekly() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentWeekly.
     */
    public static FragmentWeekly newInstance(String jsonStr) {
        FragmentWeekly fragment = new FragmentWeekly();
        Bundle args = new Bundle();
        args.putString("jsonStr",jsonStr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jsonStr = getArguments().getString("jsonStr");
            Log.d("abc",jsonStr);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weekly, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        try {
            weeklyIcon = (ImageView) getView().findViewById(R.id.weeklyIcon);
            weeklySummary = (TextView) getView().findViewById(R.id.weeklySummary);
            JSONObject jsonObj = new JSONObject(jsonStr);
            String summary = jsonObj.getJSONObject("daily").getString("summary");
            String weatherIcon = jsonObj.getJSONObject("daily").getString("icon");
            weeklySummary.setText(summary);
            switch (weatherIcon){
                case "clear-day":
                    weeklyIcon.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    weeklyIcon.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    weeklyIcon.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    weeklyIcon.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    weeklyIcon.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    weeklyIcon.setImageResource(R.drawable.weather_windy_variant);
                    break;
                case "fog":
                    weeklyIcon.setImageResource(R.drawable.weather_fog);
                    break;
                case "cloudy":
                    weeklyIcon.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    weeklyIcon.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    weeklyIcon.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    weeklyIcon.setImageResource(R.drawable.weather_sunny);
            }

            lineChart = (LineChart) getView().findViewById(R.id.lineChart);

            lineChart.setDragEnabled(true);
            lineChart.setScaleEnabled(false);

           // ArrayList<Entry> yValues = new ArrayList<>();
            ArrayList<Entry> tHighs = new ArrayList<>();
            ArrayList<Entry> tLows = new ArrayList<>();

            for(int i = 0; i <= 7; i++){
                float tHighStr = Float.parseFloat(jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("temperatureHigh"));
                tHighs.add(new Entry(i,tHighStr));
                float tLowStr = Float.parseFloat(jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(i).getString("temperatureLow"));
                tLows.add(new Entry(i,tLowStr));
            }


            LineDataSet temperatureHigh = new LineDataSet(tHighs,"Maximum Temperature");
            LineDataSet temperatureLow = new LineDataSet(tLows,"Minimum Temperature");
            temperatureHigh.setColor(Color.parseColor("#faab1a"));
            temperatureLow.setColor(Color.parseColor("#bb86fc"));

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(temperatureLow);
            dataSets.add(temperatureHigh);
            LineData data = new LineData(dataSets);

            lineChart.setData(data);
            lineChart.getAxisLeft().setTextColor(Color.parseColor("#ffffff"));
            lineChart.getAxisRight().setTextColor(Color.parseColor("#ffffff"));
            lineChart.getXAxis().setTextColor(Color.parseColor("#ffffff"));
            lineChart.getLegend().setTextColor(Color.parseColor("#ffffff"));
            lineChart.getLegend().setTextSize(15f);
            lineChart.getLegend().setFormSize(13f);
            lineChart.getLegend().setXEntrySpace(25f);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
