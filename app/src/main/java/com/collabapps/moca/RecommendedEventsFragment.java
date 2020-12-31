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

        /*
        RecyclerView recommEventsRv = view.findViewById(R.id.recomm_events_rv);
        recommEventsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventAdapter eventAdapter = new EventAdapter(eventList, this);
        recommEventsRv.setAdapter(eventAdapter); */

        return view;
    }

    private void populateDummyData() {
        eventList = new ArrayList<>();

        eventList.add(new Event("Mastering Kotlin", "GDG MAD", "Sat, Dec 19, 11:00", "us04web.zoom.us/j/74212717789"));
        eventList.add(new Event("Understanding Colors", "The UI/UX People", "Sat, Feb 13, 17:00", "meet.google.com/sqn-osgw-tyf"));
        eventList.add(new Event("The Future of Jetpack", "Kotlin Mumbai", "Sat, Dec 26, 16:00", "Online, Microsoft Teams"));
        eventList.add(new Event("Start Reacting", "Javascript Boston", "Sat, Feb 21, 15:00", "Idea Space, Boston, MA"));
        eventList.add(new Event("Why Moshi is Better", "The Kotliners", "Sat, Mar 16, 12:00", "New York, NY"));
        eventList.add(new Event("Swift all the way", "Swifters of Nashville", "Sat, Jan 5, 16:00", "Nashville, TN"));
        eventList.add(new Event("The Era of Blockchain", "The Blockchain Club", "Sat, Apr 22, 13:00", "Charlotte, NC"));
    }

    private void setupRecyclerView() {

    }

    @Override
    public void onEventClick(int pos) {
        //navigate to EventDetailsActivity and send the Event at pos
    }
}