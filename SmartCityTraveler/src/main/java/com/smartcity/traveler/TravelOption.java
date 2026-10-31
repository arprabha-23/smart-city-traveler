package com.smartcity.traveler;

public class TravelOption {
    private final String name, source, destination, time, type;
    private final double price;

    public TravelOption(String name, String source, String destination, double price, String time, String type) {
        this.name = name; this.source = source; this.destination = destination;
        this.price = price; this.time = time; this.type = type;
    }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getPrice() { return price; }
    public String getTime() { return time; }
    public String getType() { return type; }
}
