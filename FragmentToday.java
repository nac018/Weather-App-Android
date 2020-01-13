package com.example.weatherapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentToday.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentToday#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentToday extends Fragment {

    private String jsonStr;

    private TextView currentWindSpeed;
    private TextView currentPressure;
    private TextView currentPrecipitation;
    private TextView currentTemperature;
    private ImageView currentIcon;
    private TextView currentIconText;
    private TextView currentHumidity;
    private TextView currentVisibility;
    private TextView currentCloudCover;
    private TextView currentOzone;

    private OnFragmentInteractionListener mListener;

    public FragmentToday() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentToday.
     */
    public static FragmentToday newInstance(String jsonStr) {
        FragmentToday fragment = new FragmentToday();
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
            Log.d("ff",jsonStr);
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            float windSpeed =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("windSpeed"))*100);
            windSpeed /= 100;
            currentWindSpeed = (TextView) getView().findViewById(R.id.windSpeedValue);
            currentWindSpeed.setText(Float.toString(windSpeed) + " mph");

            float pressure =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("pressure"))*100);
            pressure /= 100;
            currentPressure = (TextView) getView().findViewById(R.id.pressureValue);
            currentPressure.setText(Float.toString(pressure) + " mb");

            float precipitation =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("precipIntensity"))*100);
            precipitation /= 100;
            currentPrecipitation = (TextView) getView().findViewById(R.id.precipitationValue);
            currentPrecipitation.setText(Float.toString(precipitation) + " mmph");

            float temp = Float.parseFloat(jsonObj.getJSONObject("currently").getString("temperature"));
            String temperature = Integer.toString(Math.round(temp));
            currentTemperature = (TextView) getView().findViewById(R.id.temperatureValue);
            currentTemperature.setText(temperature + " °F");

            String weatherIcon = jsonObj.getJSONObject("currently").getString("icon");
            currentIcon = (ImageView) getView().findViewById(R.id.iconImage);
            currentIconText = (TextView) getView().findViewById(R.id.iconValue);

            switch (weatherIcon){
                case "clear-day":
                    currentIcon.setImageResource(R.drawable.weather_sunny);
                    currentIconText.setText("clear day");
                    break;
                case "clear-night":
                    currentIcon.setImageResource(R.drawable.weather_night);
                    currentIconText.setText("clear night");
                    break;
                case "rain":
                    currentIcon.setImageResource(R.drawable.weather_rainy);
                    currentIconText.setText("rain");
                    break;
                case "snow":
                    currentIcon.setImageResource(R.drawable.weather_snowy);
                    currentIconText.setText("snow");
                    break;
                case "sleet":
                    currentIcon.setImageResource(R.drawable.weather_snowy_rainy);
                    currentIconText.setText("sleet");
                    break;
                case "wind":
                    currentIcon.setImageResource(R.drawable.weather_windy_variant);
                    currentIconText.setText("wind");
                    break;
                case "fog":
                    currentIcon.setImageResource(R.drawable.weather_fog);
                    currentIconText.setText("fog");
                    break;
                case "cloudy":
                    currentIcon.setImageResource(R.drawable.weather_cloudy);
                    currentIconText.setText("cloudy");
                    break;
                case "partly-cloudy-day":
                    currentIcon.setImageResource(R.drawable.weather_partly_cloudy);
                    currentIconText.setText("cloudy day");
                    break;
                case "partly-cloudy-night":
                    currentIcon.setImageResource(R.drawable.weather_night_partly_cloudy);
                    currentIconText.setText("cloudy night");
                    break;
                default:
                    currentIcon.setImageResource(R.drawable.weather_sunny);
                    currentIconText.setText("clear day");
            }

            float humidity = Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("humidity"))*10000);
            humidity /= 100;
            currentHumidity = (TextView) getView().findViewById(R.id.humidityValue);
            currentHumidity.setText(Float.toString(humidity) + "%");

            float visibility =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("visibility"))*100);
            visibility /= 100;
            currentVisibility = (TextView) getView().findViewById(R.id.visibilityValue);
            currentVisibility.setText(Float.toString(visibility) + " km");

            float cloudCover = Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("cloudCover"))*10000);
            cloudCover /= 100;
            currentCloudCover = (TextView) getView().findViewById(R.id.cloudCoverValue);
            currentCloudCover.setText(Float.toString(cloudCover) + "%");

            float ozone =  Math.round(Float.parseFloat(jsonObj.getJSONObject("currently").getString("ozone"))*100);
            ozone /= 100;
            currentOzone = (TextView) getView().findViewById(R.id.ozoneValue);
            currentOzone.setText(Float.toString(ozone) + " DU");

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
