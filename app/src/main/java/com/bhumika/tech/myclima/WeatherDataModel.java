package com.bhumika.tech.myclima;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataModel {

    // Member variables that hold our relevant weather inforomation.
    private String mTemperature;
    private String mCity;
    private String mIconName;
    private int mCondition;


    // Create a WeatherDataModel from a JSON.
    // We will call this instead of the standard constructor.
    public static WeatherDataModel fromJson(JSONObject jsonObject) {

        // JSON parsing is risky business. Need to surround the parsing code with a try-catch block.
        try {
            // instantiate an object
            WeatherDataModel weatherDataModel = new WeatherDataModel();
            // set weatherData.mCity from JSON
            weatherDataModel.mCity = jsonObject.get("name").toString();
            // set weatherData.mCondition from JSON
            weatherDataModel.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            // set mIconName using updateWeatherIcon() & mCondition
            weatherDataModel.mIconName = updateWeatherIcon(weatherDataModel.mCondition);
            // get temp from JSON
            // convert to degree celsius and round temp to int
            // set mTemperature
            double temp = jsonObject.getJSONObject("main").getDouble("temp") - 273;
            weatherDataModel.mTemperature = ""+(int) temp;

            // return the object
            return weatherDataModel;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    // Get the weather image name from OpenWeatherMap's condition (marked by a number code)
    private static String updateWeatherIcon(int condition) {

        if (condition >= 0 && condition < 300) {
            return "tstorm1";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return "shower3";
        } else if (condition >= 600 && condition <= 700) {
            return "snow4";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition < 800) {
            return "tstorm3";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy2";
        } else if (condition >= 900 && condition <= 902) {
            return "tstorm3";
        } else if (condition == 903) {
            return "snow5";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "tstorm3";
        }

        return "dunno";
    }

    // Getter methods for temperature, city, and icon name:
    public String getTemperature() {
        return mTemperature + "°";
    }
    public String getCity() {
        return mCity;
    }
    public String getIconName() {
        return mIconName;
    }

}
