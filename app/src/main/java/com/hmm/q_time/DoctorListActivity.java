package com.hmm.q_time;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity {
    private static final String TAG = "ListDoctorActivity";

    //vars
    private ArrayList<String> mDoctorNames = new ArrayList<>();
    private ArrayList<Integer> mDoctorImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Log.d(TAG, "onCreate: started.");
        initSeedDoctor();
        initRecyclerView();
    }

    private void initSeedDoctor(){
        Log.d(TAG, "initSeedDoctorCalled");

        mDoctorNames.add("Raka Hadhyana");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Timothy Sihombing");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Nira Rizki Ramadhani");
        mDoctorImages.add(R.drawable.ic_doctor_f);

        mDoctorNames.add("Raka Hadhyana");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Timothy Sihombing");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Nira Rizki Ramadhani");
        mDoctorImages.add(R.drawable.ic_doctor_f);

        mDoctorNames.add("Raka Hadhyana");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Timothy Sihombing");
        mDoctorImages.add(R.drawable.ic_doctor);

        mDoctorNames.add("Nira Rizki Ramadhani");
        mDoctorImages.add(R.drawable.ic_doctor_f);
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.doctor_recycler_view);
        DoctorAdapter adapter = new DoctorAdapter(this, mDoctorNames, mDoctorImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
