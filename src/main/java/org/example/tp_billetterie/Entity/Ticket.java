package org.example.tp_billetterie.Entity;

import org.example.tp_billetterie.Enum.SeatType;

public class Ticket {
    private static int nextId = 1;
    private int id;
    private int seatNumber;
    private Customer customer;
    private Event event;
    private SeatType seatType;

    public Ticket(int seatNumber, Customer customer, Event event, SeatType seatType) {
        this.id = nextId++;
        this.seatNumber = seatNumber;
        this.customer = customer;
        this.event = event;
        this.seatType = seatType;
    }

    public int getId() {
        return id;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + id + " | Seat #" + seatNumber + " (" + seatType + ") - " +
                event.getName() + " - Customer: " + customer.getFirstName() + " " + customer.getLastName();
    }
}