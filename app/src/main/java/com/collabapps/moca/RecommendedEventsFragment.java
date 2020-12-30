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

public class RecommendedEventsFragment extends Fragment implements EventAdapter.EventClickListener{

    private View view;
    private List<Event> eventList;

    public RecommendedEventsFragment() {
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

        View view = inflater.inflate(R.layout.fragment_recommended_events, container, false);

        populateDummyData();
        //setupRecyclerView();

        return view;
    }

    private void populateDummyData() {
        eventList = new ArrayList<>();

        //fill some dummy data
    }

    private void setupRecyclerView() {
        RecyclerView recommendedEventsRv = view.findViewById(R.id.recommended_events_recyclerView);
        EventAdapter eventAdapter = new EventAdapter(eventList, this);
        recommendedEventsRv.setAdapter(eventAdapter);
        recommendedEventsRv.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onEventClick(int pos) {
        //navigate to EventDetailsActivity and send the Event at pos
    }
}