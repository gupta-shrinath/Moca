package com.collabapps.moca.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Event implements Parcelable {

    @SerializedName("topic_id")
    private String topicId;

    @SerializedName("name")
    private String eventName;

    @SerializedName("organization")
    private String eventOrganiserName;

    @SerializedName("organization_image ")
    private String eventOrganiserImageUrl;

    @SerializedName("date")
    private String eventDate;

    @SerializedName("time")
    private String eventTime;

    @SerializedName("platform_link")
    private String eventLocation;

    @SerializedName("is_event_online")
    private boolean isEventOnline;

    @SerializedName("description")
    private String eventDesc;

    @SerializedName("venue")
    private Venue eventVenue;

    public Event() {

    }

    public Event(String eventName, String eventOrganiserName, String eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventOrganiserName = eventOrganiserName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }


    protected Event(Parcel in) {
        topicId = in.readString();
        eventName = in.readString();
        eventOrganiserName = in.readString();
        eventOrganiserImageUrl = in.readString();
        eventDate = in.readString();
        eventTime = in.readString();
        eventLocation = in.readString();
        isEventOnline = in.readByte() != 0;
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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
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

    public String getEventOrganiserImageUrl() {
        return eventOrganiserImageUrl;
    }

    public void setEventOrganiserImageUrl(String eventOrganiserImageUrl) {
        this.eventOrganiserImageUrl = eventOrganiserImageUrl;
    }

    public String getEventDate() {
        String formEventDate = "";

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
            SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd");
            formEventDate = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formEventDate + ", " + eventTime;
    }

    public String getEventTime() {
        return eventTime.substring(0, eventTime.lastIndexOf(":"));
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Venue getEventVenue() {
        return eventVenue;
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
                ", eventOrganiserName='" + eventOrganiserName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topicId);
        dest.writeString(eventName);
        dest.writeString(eventOrganiserName);
        dest.writeString(eventOrganiserImageUrl);
        dest.writeString(eventDate);
        dest.writeString(eventTime);
        dest.writeString(eventLocation);
        dest.writeByte((byte) (isEventOnline ? 1 : 0));
        dest.writeString(eventDesc);
    }

    public class Venue {

        @SerializedName("address")
        private String address;
        @SerializedName("latitude")
        private double lat;
        @SerializedName("longitude")
        private double lng;

        public Venue() {

        }

        public Venue(String address, double lat, double lng) {
            this.address = address;
            this.lat = lat;
            this.lng = lng;
        }

        public String getAddress() {
            return address.substring(0, address.indexOf(" ")) + ","
                    + address.substring(address.indexOf(" "), address.lastIndexOf(" ")) + ","
                    + address.substring(address.lastIndexOf(" "), address.length() - 1);
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }
}
