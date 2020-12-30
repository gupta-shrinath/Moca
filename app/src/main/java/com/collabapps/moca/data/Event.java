package com.collabapps.moca.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event implements Parcelable {

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

    protected Event(Parcel in) {
        id = in.readString();
        eventName = in.readString();
        eventOrganiserName = in.readString();
        eventDate = in.readString();
        eventLocation = in.readString();
        eventDesc = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(eventName);
        dest.writeString(eventOrganiserName);
        dest.writeString(eventDate);
        dest.writeString(eventLocation);
        dest.writeString(eventDesc);
    }
}
