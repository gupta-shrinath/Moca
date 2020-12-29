package com.collabapps.moca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.collabapps.moca.R;
import com.collabapps.moca.data.Event;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList = new ArrayList<>();
    private EventClickListener eventClickListener;

    public EventAdapter(List<Event> eventList, EventClickListener eventClickListener) {
        this.eventList = eventList;
        this.eventClickListener = eventClickListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(view, eventClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.setEventName(eventList.get(position).getEventName());
        holder.setEventOrganiserName(eventList.get(position).getEventOrganiserName());
        holder.setEventDate(eventList.get(position).getEventDate());
        holder.setEventLocation(eventList.get(position).getEventLocation());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements EventClickListener {

        private final TextView eventName, eventOrganiserName, eventDate, eventLocation;
        private final CardView event_item_cardView;

        public EventViewHolder(@NonNull View itemView, EventClickListener eventClickListener) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventOrganiserName = itemView.findViewById(R.id.event_organiser_name);
            eventDate = itemView.findViewById(R.id.event_date);
            eventLocation = itemView.findViewById(R.id.event_location);
            event_item_cardView = itemView.findViewById(R.id.event_item_cardView);

            event_item_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventClickListener.onEventClick(getAdapterPosition());
                }
            });
        }

        public void setEventName(String eventName) {
            this.eventName.setText(eventName);
        }

        public void setEventOrganiserName(String eventOrganiserName) {
            this.eventOrganiserName.setText(eventOrganiserName);
        }

        public void setEventDate(String eventDate) {
            this.eventDate.setText(eventDate);
        }

        public void setEventLocation(String eventLocation) {
            this.eventLocation.setText(eventLocation);
        }

        @Override
        public void onEventClick(int pos) {

        }
    }

    public interface EventClickListener {
        void onEventClick(int pos);
    }
}
