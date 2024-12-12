package com.example.events.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
@PageTitle("Events RS - Login")
public class LoginView extends VerticalLayout {
    private final LoginForm login = new LoginForm();

    public LoginView() {
        addClassName("login-rich-content");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        Anchor signupLink = new Anchor("signup", "Don't have an account? Sign up here.");

        add(new H1("Events RS"), login, signupLink);
    }
}
