package com.example.events;


import com.example.events.ui.EventRegistrationView;
import com.example.events.ui.EventListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;


public class MainLayout extends AppLayout {

    public MainLayout(@Autowired AuthenticationContext authenticationContext) {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Events RS");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);
        layout.add(toggle);
        layout.add(title);
        layout.add(new Button("Logout", click -> authenticationContext.logout()) );




        addToNavbar(layout);


        // Create the side drawer
        createDrawer();

        // Set the drawer as the primary section
        setPrimarySection(Section.DRAWER);
    }


    private void createDrawer() {
        SideNav nav = new SideNav();

        // Add navigation items

        nav.addItem(new SideNavItem("Register Event", EventRegistrationView.class));
        nav.addItem(new SideNavItem("Manage Event", EventListView.class));

        nav.getStyle()
                .set("margin-top", "20px");




        // Add navigation to drawer inside a scrollable container
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        addToDrawer(scroller);
    }
}