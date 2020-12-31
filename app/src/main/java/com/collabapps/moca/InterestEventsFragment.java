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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        populateDummyData();

        return view;
    }


    private void populateDummyData() {
        eventList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request;

        request = new Request.Builder()
                .url("https://moca-api.herokuapp.com/api/v1/resources/events/all/")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    Gson gson = new Gson();

                    Type eventType = new TypeToken<ArrayList<Event>>(){}.getType();
                    ArrayList<Event> events = gson.fromJson(json, eventType);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupRecyclerView(events);
                        }
                    });
                }
            }
        });
    }

    private void setupRecyclerView(List<Event> eventList) {
        RecyclerView interestEventsRv = view.findViewById(R.id.interest_events_rv);
        EventAdapter eventAdapter = new EventAdapter(eventList, this);
        interestEventsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        interestEventsRv.setAdapter(eventAdapter);
    }

    @Override
    public void onEventClick(int pos) {
        //TODO: navigate to EventDetailsActivity with the Event at pos
    }
}