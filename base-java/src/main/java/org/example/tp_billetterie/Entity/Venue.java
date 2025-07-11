package org.example.tp_billetterie.Entity;

public class Venue extends Address {
    private int capacity;

    public Venue(String street, String city, String postalCode, String country, int capacity) {
        super(street, city, postalCode, country);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return super.toString() + " (Capacity: " + capacity + ")";
    }
}
