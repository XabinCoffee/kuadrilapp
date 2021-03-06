package com.kapp.rxabin.kuadrilapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapp.rxabin.kuadrilapp.adapter.UserAdapter;
import com.kapp.rxabin.kuadrilapp.obj.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment implements UserAdapter.OnUserRemovalSelectedListener {

    private static String date;
    private static String time;
    private static boolean datetimeSet;
    private UserAdapter uAdapter;
    private RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View iv = inflater.inflate(R.layout.fragment_create_event, container, false);
        rv = (RecyclerView) iv.findViewById(R.id.rvUser);

        mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        uAdapter = new UserAdapter(getContext(), this);
        rv.setAdapter(uAdapter);

        //eventType.setText(getResources().getStringArray(R.array.eventType)[0].toString());
        datetimeSet = false;

        date = "";
        time = "";

        return iv;

    }

    public static String getDate(){
        return date;
    }

    public void setDate(String dt){
        date = dt;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        CreateEventFragment.time = time;
    }

    public static boolean isDatetimeSet() {
        return datetimeSet;
    }

    public static void setDatetimeSet(boolean datetimeSet) {
        CreateEventFragment.datetimeSet = datetimeSet;
    }

    public UserAdapter getUserAdapter(){
        return this.uAdapter;
    }

    @Override
    public void onUserRemovalSelected(User userData) {
        getUserAdapter().getUsers().remove(userData);
        getUserAdapter().notifyDataSetChanged();
    }

    public RecyclerView getRv() {
        return rv;
    }



}
