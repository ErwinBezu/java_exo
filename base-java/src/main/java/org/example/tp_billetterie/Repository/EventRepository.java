package org.example.tp_billetterie.Repository;

import org.example.tp_billetterie.Entity.Event;
import java.util.ArrayList;
import java.util.List;

public class EventRepository implements Repository<Event> {
    private final List<Event> events;

    public EventRepository() {
        this.events = new ArrayList<>();
    }

    @Override
    public void save(Event event) {
        events.add(event);
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public Event findById(int id) {
        for (Event event : events) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId() == id) {
                events.remove(i);
                break;
            }
        }
    }
}