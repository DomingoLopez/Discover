package com.hdrescuer.supportyourdiscoveries.db.entity;

public class AddressShort {

    String address;
    double latitud;
    double longitud;

    public AddressShort(String address, double latitud, double longitud) {
        this.address = address;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
