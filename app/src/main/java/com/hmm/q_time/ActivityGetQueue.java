package com.hmm.q_time;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityGetQueue extends AppCompatActivity {
    private static final String TAG = "ActivityGetQueue";
    private static final String EXTRA_DOCTOR = "Doctor";

    private Button mQueueNowButton;
    private Button mQueueLaterButton;
    private TextView mDoctorName;
    private Doctor mDoctor;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LinearLayout mLocationBox;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_queue);

        Intent intent = getIntent();
        mDoctor = intent.getParcelableExtra(EXTRA_DOCTOR);
        Log.d(TAG, mDoctor.getId() + ", " + mDoctor.getName() + ", " + mDoctor.getType());

        mDoctorName = findViewById(R.id.doctor_detail_name);
        mDoctorName.setText(mDoctor.getName());

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, location.toString());
                            Log.d(TAG, location.getProvider());
                        }
                    }
                });


        mLocationBox = findViewById(R.id.location);
        mLocationBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:"+mDoctor.getLatitude()+","+mDoctor.getLongitude()
                        +"?z=20&q="+mDoctor.getLatitude()+","+mDoctor.getLongitude()+"(Your doctor is here!)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        mQueueNowButton = findViewById(R.id.queue_now);
        mQueueNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: called");
                Toast.makeText(ActivityGetQueue.this, "QUEUE NOW", Toast.LENGTH_SHORT).show();
            }
        });
        mQueueLaterButton = findViewById(R.id.queue_later);
        mQueueLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityGetQueue.this, "QUEUE LATER", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
