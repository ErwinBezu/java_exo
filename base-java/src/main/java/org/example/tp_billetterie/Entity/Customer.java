package org.example.tp_billetterie.Entity;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private static int nextId = 1;
    private int id;
    private String lastName;
    private String firstName;
    private Address address;
    private int age;
    private String phoneNumber;
    private List<Ticket> ticketList;

    public Customer(String lastName, String firstName, Address address, int age, String phoneNumber) {
        this.id = nextId++;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.ticketList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Ticket> getTicketList() {
        return new ArrayList<>(ticketList);
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public void addTicket(Ticket ticket) {
        ticketList.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        ticketList.remove(ticket);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + firstName + " " + lastName + " (" + age + " years old) - " + address + " - Phone: " + phoneNumber;
    }
}
