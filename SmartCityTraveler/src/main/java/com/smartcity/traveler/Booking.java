package com.smartcity.traveler;

public class Booking {
    private final String reference, source, destination, date;
    private final TravelOption option;
    private final int passengers;
    private final double amount;

    public Booking(String reference, TravelOption option, String source, String destination,
                   String date, int passengers, double amount) {
        this.reference = reference; this.option = option; this.source = source;
        this.destination = destination; this.date = date; this.passengers = passengers; this.amount = amount;
    }
    public String getReference() { return reference; }
    public TravelOption getOption() { return option; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getDate() { return date; }
    public int getPassengers() { return passengers; }
    public double getAmount() { return amount; }
}
