package com.example.myapplication.Model;

import com.example.myapplication.Model.Object.Humidity;
import com.example.myapplication.Model.Object.Manufacturer;
import com.example.myapplication.Model.Object.Place;
import com.example.myapplication.Model.Object.Rainfall;
import com.example.myapplication.Model.Object.Temperature;
import com.example.myapplication.Model.Object.WindDirection;
import com.example.myapplication.Model.Object.WindSpeed;

public class Attribute {
    private Rainfall rainfall;
    private Manufacturer manufacturer;
    private Humidity humidity;
    private Place place;
    private WindDirection windDirection;
    private WindSpeed windSpeed;
    private Temperature temperature;

    public Rainfall getRainfall() {
        return rainfall;
    }

    public void setRainfall(Rainfall rainfall) {
        this.rainfall = rainfall;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }
}
