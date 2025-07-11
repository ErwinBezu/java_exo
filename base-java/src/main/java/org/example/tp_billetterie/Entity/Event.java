package org.example.tp_billetterie.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private static int nextId = 1;
    private int id;
    private String name;
    private Venue venue;
    private LocalDate date;
    private LocalTime time;
    private int numberOfSeats;
    private List<Ticket> ticketList;

    public Event(String name, Venue venue, LocalDate date, LocalTime time, int numberOfSeats) {
        this.id = nextId++;
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.numberOfSeats = numberOfSeats;
        this.ticketList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<Ticket> getTicketList() {
        return new ArrayList<>(ticketList);
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void addTicket(Ticket ticket) throws IllegalStateException {
        if (ticketList.size() < numberOfSeats) {
            ticketList.add(ticket);
        } else {
            throw new IllegalStateException("Event is full - cannot add more tickets");
        }
    }

    public void removeTicket(Ticket ticket) {
        ticketList.remove(ticket);
    }

    public int getAvailableSeats() {
        return numberOfSeats - ticketList.size();
    }

    public boolean hasAvailableSeats() {
        return getAvailableSeats() > 0;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " - " + date + " at " + time + " (" + venue + ") - Available: " + getAvailableSeats();
    }
}
