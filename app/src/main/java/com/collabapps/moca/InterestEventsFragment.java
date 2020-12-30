package com.collabapps.moca;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collabapps.moca.adapters.EventAdapter;
import com.collabapps.moca.data.Event;

import java.util.ArrayList;
import java.util.List;

public class InterestEventsFragment extends Fragment implements EventAdapter.EventClickListener {

    private View view;
    private List<Event> eventList;

    public InterestEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_interest_events, container, false);

        //setupRecyclerView();

        return view;
    }

    private void populateDummyData() {
        eventList = new ArrayList<>();

        //fill some dummy data
    }

    private void setupRecyclerView() {
        RecyclerView interestEventsRv = view.findViewById(R.id.interest_events_recyclerView);
        EventAdapter eventAdapter = new EventAdapter(eventList, this);
        interestEventsRv.setAdapter(eventAdapter);
        interestEventsRv.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onEventClick(int pos) {
        //TODO: navigate to EventDetailsActivity with the Event at pos
    }
}