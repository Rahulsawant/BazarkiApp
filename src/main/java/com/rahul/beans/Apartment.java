package com.rahul.beans;

public class Apartment {

    String apartmentName;
    String costOfRent;
    String detailsUrl;

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getCostOfRent() {
        return costOfRent;
    }

    public void setCostOfRent(String costOfRent) {
        this.costOfRent = costOfRent;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    @Override
    public String toString() {
        return "apartmentName- "+apartmentName+" \n "+"costOfRent- "+costOfRent+" \n "+"detailsUrl- "+detailsUrl;
    }
}
