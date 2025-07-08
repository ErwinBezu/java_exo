package org.example.tp_billetterie.Service;

import org.example.tp_billetterie.Entity.Event;
import org.example.tp_billetterie.Entity.Ticket;
import org.example.tp_billetterie.Exception.NotFoundException;
import org.example.tp_billetterie.Repository.EventRepository;
import java.util.List;

public class EventService extends BaseCrudService<Event> {
    private final EventRepository eventRepository;

    public EventService() {
        super("Event");
        this.eventRepository = new EventRepository();
    }

    @Override
    public void create(Event event) {
        eventRepository.save(event);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(int id) throws NotFoundException {
        Event event = eventRepository.findById(id);
        if (event == null) {
            throwNotFound(id);
        }
        return event;
    }

    @Override
    public void update(int id, Event updatedEvent) throws NotFoundException {
        Event existingEvent = getById(id); // Vérifie existence
        updateEventFields(existingEvent, updatedEvent);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        Event event = getById(id);
        eventRepository.deleteById(id);
    }

    private void updateEventFields(Event existing, Event updated) {
        existing.setName(updated.getName());
        existing.setVenue(updated.getVenue());
        existing.setDate(updated.getDate());
        existing.setTime(updated.getTime());
        existing.setNumberOfSeats(updated.getNumberOfSeats());
    }

    public List<Ticket> getEventTickets(int eventId) throws NotFoundException {
        Event event = getById(eventId);
        return event.getTicketList();
    }
}