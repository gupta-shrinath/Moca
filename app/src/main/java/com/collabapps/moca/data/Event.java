package com.collabapps.moca.data;

public class Event {

    private String id;
    private String eventName;
    private String eventOrganiserName;
    private String eventDate;
    private String eventLocation;

    public Event() {

    }

    public Event(String eventName, String eventOrganiserName, String eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventOrganiserName = eventOrganiserName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
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
}
