package com.example.events.ui;

import com.example.events.backend.User;
import com.example.events.backend.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;


@Route("signup")
@AnonymousAllowed
@PageTitle("Events RS - Sign Up")
public class SignupView extends VerticalLayout {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupView(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        setAlignItems(Alignment.CENTER);  // Horizontal alignment
        setJustifyContentMode(JustifyContentMode.CENTER);  // Vertical alignment
        setWidth("100%");  // Full width of the layout
        setPadding(true);
        setSpacing(true);  // Add spacing between components



        // Create input fields for the form
        TextField username = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        // Add fields to the form layout
//        formLayout.add(username, passwordField);

//        formLayout.setResponsiveSteps(
//            new FormLayout.ResponsiveStep("250px",1 )
//        );
//        formLayout.setWidth("350px");

        // Create the signup button
        Button signupButton = new Button("Sign Up", event -> {
            User user = new User();
            user.setUsername(username.getValue());
            user.setPassword(passwordEncoder.encode(passwordField.getValue()));
            user.setRole("USER");

            userRepository.save(user);  // Save user to the database

            // Clear the fields after successful signup
            username.clear();
            passwordField.clear();
        });

//        getStyle().set("max-width", "400px");

//        formLayout.setMaxWidth("500px");
        // Style the signup button
        signupButton.getStyle()
                .set("background-color", "#007bff")
                .set("color", "white")
                .set("border", "none")
                .set("padding", "10px 20px")
                .set("font-size", "16px")
                .set("cursor", "pointer")
                .set("margin-top", "20px");

        // Create SignIn link
        Anchor signIn = new Anchor("login", "Already have an account? Login here.");
        signIn.getStyle().set("margin-top", "10px").set("text-decoration", "none");

        // Add components to the layout
        addClassName("signup-view");

        getStyle().setWidth("100%");
        getStyle().setHeight("100%");
        getStyle().set("padding", "20px");
        getStyle().set("border", "1px solid #ddd");
        getStyle().set("border-radius", "5px");
        getStyle().set("item-align", "center");


        username.setWidth("400px");
        passwordField.setWidth("400px");



        add(new H1("Sign Up"),username,passwordField, signupButton, signIn);
    }
}