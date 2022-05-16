package com.example.minimental;


public class informationQuestion {

    private String day;
    private String month;
    private String date;
    private String year;
    private String season;
    private String country;
    private String city;
    private String address;
    private String floor;
    private String area;

    public informationQuestion() {
    }

    public informationQuestion(String day, String month, String date, String year, String season, String country, String city, String address, String floor, String area) {
        this.day = day;
        this.month = month;
        this.date = date;
        this.year = year;
        this.season = season;
        this.country = country;
        this.city = city;
        this.address = address;
        this.floor = floor;
        this.area = area;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
