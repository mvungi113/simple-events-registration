package com.example.events.ui;

import com.example.events.MainLayout;
import com.example.events.backend.Event;
import com.example.events.backend.EventRepository;
import com.example.events.backend.User;
import com.example.events.backend.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Route(value = "register-event", layout = MainLayout.class)
@PageTitle("Register Event")
@PermitAll
public class EventRegistrationView extends VerticalLayout {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventRegistrationView(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;

        setAlignItems(Alignment.CENTER);  // Horizontal alignment
        setJustifyContentMode(JustifyContentMode.CENTER);  // Vertical alignment
        setWidth("100%");  // Full width of the layout
        setPadding(true);
        setSpacing(true);  // Add spacing between components


        TextField eventName = new TextField("Event Name");


        TextArea eventDescription = new TextArea("Event Description");
        DatePicker eventDate = new DatePicker("Event Date");
        TextField eventLocation = new TextField("Location");

        Button saveButton = new Button("Save Event");
        saveButton.addClickListener(e -> {
            // Retrieve the logged-in user
            String username = getCurrentUsername();
            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                User currentUser = userOptional.get();
                Event event = new Event();
                event.setName(eventName.getValue());
                event.setDescription(eventDescription.getValue());
                event.setDate(eventDate.getValue());
                event.setLocation(eventLocation.getValue());
                event.setUser(currentUser); // Associate the event with the logged-in user

                eventRepository.save(event);
                Notification.show("Event saved successfully!", 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show("User not found!", 3000, Notification.Position.MIDDLE);
            }
        });
        saveButton.getStyle()
                .set("background-color", "#007bff")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("font-size", "16px")
                .set("cursor", "pointer")
                .set("margin-top", "20px");

        add(eventName, eventDescription, eventDate, eventLocation, saveButton);

        getStyle().setWidth("100%");
        getStyle().setHeight("100%");
        getStyle().set("padding", "20px");
        getStyle().set("border", "1px solid #ddd");
        getStyle().set("border-radius", "5px");
        getStyle().set("item-align", "center");


        eventName.setWidth("500px");
        eventDescription.setWidth("500px");
        eventLocation.setWidth("500px");
        eventDate.setWidth("500px");


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
