package com.senr.ichra.dom011;

public class EmergencyNo {
    private String service;
    private int number;

    public EmergencyNo(String service, int number) {
        this.service = service;
        this.number = number;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
