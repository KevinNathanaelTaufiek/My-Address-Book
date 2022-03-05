package com.kevinnt.myaddressbook.models.employee_attribute;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("city")
    private String city;

    @SerializedName("country")
    private String country;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public class Coordinates{

        @SerializedName("latitude")
        private double latitude;

        @SerializedName("longitude")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", coordinates=" + coordinates.toString() +
                '}';
    }
}
