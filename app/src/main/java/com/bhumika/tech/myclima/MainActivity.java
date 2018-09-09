package com.bhumika.tech.myclima;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/*
    Please add " implementation 'com.loopj.android:android-async-http:1.4.9' " to app level build.gradle file
    Please add " <uses-permission android:name="android.permission.INTERNET"/> " to AndroidManifest.xml
    TO DO:
        (1) Get references to all your layout views for this activity in initLayout() function
        (2) Connect this activity with "CitySelectionActivity" via "changeCityButton" click
            (a) set an onClickListener on the button
            (b) implement the methods of onClickListener interface
            (c) use the intent
        (3) Get result from activity via onActivityResult()
        (4) Check request code & result code
        (5) Get city from this result
        (6) Set request parameters (city, AppID) for the http request
        (7) Fetch data from the api via AsyncHttpClient & JsonHttpClientHandler, using above params
        (8) If success, update the UI, else display error toast
 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // declaration of references
    TextView cityTextView, temperatureTextView;
    ImageButton changeCityButton;
    ImageView weatherImageView;

    private int NEW_CITY_CODE = 123;
    private String LOGCAT_TAG = "MainActivity --> ";
    // Base URL for the OpenWeatherMap API. More secure https is a premium feature =(
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "e72ca729af228beabd5d20e3b7749713";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();

        changeCityButton.setOnClickListener(this);
    }

    protected void initLayout() {
        cityTextView = findViewById(R.id.cityTextView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        changeCityButton = findViewById(R.id.changeCityButton);
        weatherImageView = findViewById(R.id.weatherImageView);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, CitySelectionActivity.class);
        startActivityForResult(intent, NEW_CITY_CODE);
    }

    // Callback received when a new city name is entered on the second screen.
    // Checking request code and if result is OK before making the API call
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK && requestCode==NEW_CITY_CODE) {
            String city = data.getStringExtra("City Name");
            setParameters(city);
        }

    }

    // Configuring the parameters when a new city has been entered:
    // This part of the code is API-specific. Wr do this in accordance with OpenWeatherMap API
    protected void setParameters(String city) {
        RequestParams params = new RequestParams();
        params.put("q", city); // For query string. Do not change this line.
        params.put("appid", APP_ID);

        getWeatherData(params);
    }

    // This is the actual networking code. Parameters are already configured.
    protected void getWeatherData(RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                WeatherDataModel weatherData = WeatherDataModel.fromJson(response);
                updateUI(weatherData);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Updates the information shown on screen.
    protected void updateUI(WeatherDataModel weatherDataModel) {
        temperatureTextView.setText(weatherDataModel.getTemperature());
        cityTextView.setText(weatherDataModel.getCity());
        int resourceID = getResources().getIdentifier(weatherDataModel.getIconName(), "drawable", getPackageName());
        weatherImageView.setImageResource(resourceID);
    }

}
