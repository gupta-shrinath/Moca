package com.collabapps.moca.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event {

    private String id;
    private String eventName;
    private String eventOrganiserName;
    private String eventDate;
    private String eventLocation;
    private String eventDesc;

    public Event() {

    }

    public Event(String eventName, String eventDate, String eventLocation, String eventDesc) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDesc = eventDesc;
    }

    public Event(String id, String eventName, String eventOrganiserName, String eventDate, String eventLocation, String eventDesc) {
        this.id = id;
        this.eventName = eventName;
        this.eventOrganiserName = eventOrganiserName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventDesc = eventDesc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventOrganiserName() {
        return eventOrganiserName;
    }

    public void setEventOrganiserName(String eventOrganiserName) {
        this.eventOrganiserName = eventOrganiserName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public boolean isEventLive() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, HH:mm");
        String date = sdf.format(new Date());
        return eventDate == date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDesc='" + eventDesc + '\'' +
                '}';
    }
}
