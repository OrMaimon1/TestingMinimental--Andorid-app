package com.example.minimental;

public class MissingDetail {

    private String country;
    private String city;
    private String address;
    private String floor;
    private String area;

    public MissingDetail() {}

    public MissingDetail(String country, String city, String address, String floor, String area) {
        this.country = country;
        this.city = city;
        this.address = address;
        this.floor = floor;
        this.area = area;
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
