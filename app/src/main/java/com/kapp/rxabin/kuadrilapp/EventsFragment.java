package com.kapp.rxabin.kuadrilapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapp.rxabin.kuadrilapp.database.DbManager;
import com.kapp.rxabin.kuadrilapp.obj.Event;

import java.util.ArrayList;
import java.util.Vector;


public class EventsFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private EventAdapter eAdapter;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private ProgressBar mLoading;
    private TextView mEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mEmpty = (TextView) view.findViewById(R.id.empty);


        mAuth = FirebaseAuth.getInstance();

        fillRecyclerView();

        return view;
    }

    public void fillRecyclerView(){

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        getUserEvents();
        recyclerView.setAdapter(eAdapter);
    }

    public void getUserEvents(){

        final EventAdapter eAdapter = new EventAdapter(getContext());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("events");
        final ArrayList<Event> el = new ArrayList<>();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot eventDataSnapshot : dataSnapshot.getChildren()){
                    Event e = eventDataSnapshot.getValue(Event.class);
                    if (e.hasMember(mAuth.getCurrentUser().getUid())){
                        el.add(e);
                    }

                }
                if (el.size()==0) {
                    mLoading.setVisibility(View.GONE);
                    mEmpty.setVisibility(View.VISIBLE);
                }
                else{
                eAdapter.setEvents(el);
                recyclerView.setAdapter(eAdapter);
                mLoading.setVisibility(View.GONE);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("onCancelled","DataBase error");
            }
        });
    }

}
