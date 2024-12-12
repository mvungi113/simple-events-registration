package com.example.events.ui;

import com.example.events.backend.Event;
import com.example.events.backend.EventService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("")
@PageTitle("Event Registration System")
@AnonymousAllowed
public class HomePage extends VerticalLayout {

    private final EventService eventService;

    @Autowired
    public HomePage(EventService eventService) {
        this.eventService = eventService;

        addClassName("dashboard-view");
        setSpacing(true);
        setPadding(true);

        // Add title
        add(new H1("Welcome to the Dashboard!"));
        Anchor signupLink = new Anchor("signup", "Register");
        Anchor loginLink = new Anchor("login", "Login");
        add( signupLink, loginLink);
        // Fetch and display events as cards
        List<Event> events = eventService.getAllEvents();
        add(createEventCards(events));
    }

    private Div createEventCards(List<Event> events) {
        Div cardContainer = new Div();
        cardContainer.addClassName("card-container");
        cardContainer.getStyle().set("display", "flex");
        cardContainer.getStyle().set("flex-wrap", "wrap");
        cardContainer.getStyle().set("gap", "20px");

        for (Event event : events) {
            Div card = new Div();
            card.addClassName("event-card");
            card.getStyle().set("border", "1px solid #ccc");
            card.getStyle().set("border-radius", "8px");
            card.getStyle().set("padding", "16px");
            card.getStyle().set("box-shadow", "0 2px 4px rgba(0, 0, 0, 0.1)");
            card.getStyle().set("width", "300px");

            // Event details
            card.add(new H1(event.getName()));
            card.add(new Paragraph("Date: " + event.getDate()));
            card.add(new Paragraph("Location: " + event.getLocation()));

            // "Read More" button
            Button readMoreButton = new Button("Read More");
            readMoreButton.addClickListener(e -> showEventDetails(event));
            card.add(readMoreButton);

            cardContainer.add(card);
        }
        return cardContainer;
    }

    private void showEventDetails(Event event) {
        // Create a modal dialog to display event details
        Dialog dialog = new Dialog();

        // Event details
        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setPadding(true);
        content.add(new H1(event.getName()));
        content.add(new Paragraph("Description: " + event.getDescription()));
        content.add(new Paragraph("Date: " + event.getDate()));
        content.add(new Paragraph("Location: " + event.getLocation()));

        // Close button
        Button closeButton = new Button("Close", e -> dialog.close());
        content.add(closeButton);

        dialog.add(content);
        dialog.open();
    }
}
