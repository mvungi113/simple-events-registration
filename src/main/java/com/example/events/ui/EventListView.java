package com.example.events.ui;

import com.example.events.MainLayout;
import com.example.events.backend.Event;
import com.example.events.backend.EventRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Route(value = "events", layout = MainLayout.class)
@PageTitle("My Events")
@PermitAll
public class EventListView extends VerticalLayout {

    private final EventRepository eventRepository;

    public EventListView(EventRepository eventRepository) {
        this.eventRepository = eventRepository;

        Grid<Event> eventGrid = new Grid<>(Event.class);
        // Get the current user's username
        String username = getCurrentUsername();
        // Fetch only the events belonging to the current user
        List<Event> userEvents = eventRepository.findByUserUsername(username);

        eventGrid.setItems(userEvents);
        eventGrid.setColumns("name", "description", "date", "location");

        eventGrid.addComponentColumn(event -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> editEvent(event));
            return editButton;
        }).setHeader("Edit");

        eventGrid.addComponentColumn(event -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                eventRepository.delete(event);
                eventGrid.setItems(eventRepository.findByUserUsername(username)); // Update the grid
                Notification.show("Event deleted successfully", 3000, Notification.Position.MIDDLE);
            });
            return deleteButton;
        }).setHeader("Delete");

        add(eventGrid);
    }

    private void editEvent(Event event) {
        TextField eventName = new TextField("Event name");
        eventName.setValue(event.getName());
        eventName.setClearButtonVisible(true);

        TextArea eventDescription = new TextArea("Event Description");
        eventDescription.setValue(event.getDescription());
        eventDescription.setClearButtonVisible(true);

        DatePicker eventDate = new DatePicker("Event Date");
        eventDate.setValue(event.getDate());

        TextField eventLocation = new TextField("Location");
        eventLocation.setValue(event.getLocation());
        eventLocation.setClearButtonVisible(true);

        Button saveButton = new Button("Save Changes");
        saveButton.addClickListener(e -> {
            event.setName(eventName.getValue());
            event.setDescription(eventDescription.getValue());
            event.setDate(eventDate.getValue());
            event.setLocation(eventLocation.getValue());

            eventRepository.save(event);

            Notification.show("Event Updated Successfully!", 3000, Notification.Position.MIDDLE);
            this.removeAll();
            this.add(new EventListView(eventRepository));
        });

        this.removeAll();
        this.add(new VerticalLayout(eventName, eventDescription, eventDate, eventLocation, saveButton));
    }

    private String getCurrentUsername() {
        // Get the authenticated user from the security context
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            return ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        }
        return "";
    }
}
