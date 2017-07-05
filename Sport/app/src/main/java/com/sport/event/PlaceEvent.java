package com.sport.event;


public class PlaceEvent {
    private String type;
    private String city;

    public PlaceEvent(String type,String city){
        this.type=type;
        this.city=city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
