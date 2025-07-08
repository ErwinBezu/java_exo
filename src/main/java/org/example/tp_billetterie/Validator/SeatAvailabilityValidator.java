package org.example.tp_billetterie.Validator;

import org.example.tp_billetterie.Entity.Event;
import org.example.tp_billetterie.Entity.Ticket;

import java.util.ArrayList;
import java.util.List;

public class SeatAvailabilityValidator {

    public static void validateAvailableSeats(Event event) {
        if (!event.hasAvailableSeats()) {
            throw new RuntimeException("No available seats for event: " + event.getName());
        }
    }

    public static void validateSeatNotTaken(Event event, int seatNumber) {
        List<Ticket> tickets = event.getTicketList();

        for (Ticket ticket : tickets) {
            if (ticket.getSeatNumber() == seatNumber) {
                throw new RuntimeException("Seat number " + seatNumber + " is already taken");
            }
        }
    }

    public static void validateSeatNumber(Event event, int seatNumber) {
        if (seatNumber < 1) {
            throw new IllegalArgumentException("Seat number must be at least 1");
        }

        if (seatNumber > event.getNumberOfSeats()) {
            throw new IllegalArgumentException("Seat number " + seatNumber +
                    " exceeds maximum seats (" + event.getNumberOfSeats() + ")");
        }
    }

    public static void validateSeat(Event event, int seatNumber) {
        validateSeatNumber(event, seatNumber);
        validateAvailableSeats(event);
        validateSeatNotTaken(event, seatNumber);
    }
}