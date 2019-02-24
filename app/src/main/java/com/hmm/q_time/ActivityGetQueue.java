package com.hmm.q_time;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private TextView mCurrentQueueTextView;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    private int currentQueue;
    private Boolean isInQueue;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "OnCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_queue);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        mDoctor = intent.getParcelableExtra(EXTRA_DOCTOR);
        Log.d(TAG, mDoctor.getId() + ", " + mDoctor.getName() + ", " + mDoctor.getType());


        mDoctorName = findViewById(R.id.doctor_detail_name);
        mDoctorName.setText(mDoctor.getName());

        getQueueNumber();

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
                //POST REQUEST
                sendPostRequest();
            }
        });
        mQueueLaterButton = findViewById(R.id.queue_later);
        mQueueLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityGetQueue.this, "QUEUE LATER", Toast.LENGTH_SHORT).show();
            }
        });
        isOnQueueRequest();


    }

    private void sendPostRequest() {
        try {
            String URL = "https://qtime-android.herokuapp.com/api/queue";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("user", auth.getCurrentUser().getEmail());
            jsonBody.put("doctor", mDoctor.getId());
            jsonBody.put("done", "false");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "request sent");
                    Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            Volley.newRequestQueue(this).add(request);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void isOnQueueRequest(){
        String URL = "https://qtime-android.herokuapp.com/api/queue/user/" + auth.getCurrentUser().getEmail();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject queue = response.getJSONObject("queue");
                    if(queue != null){
                        isInQueue = true;
                        mQueueNowButton.setEnabled(false);
                        mQueueNowButton.setText("Already in queue");
                    }else{
                        isInQueue = false;
                    }
                    if(isInQueue){
                        mCurrentQueueTextView = findViewById(R.id.current_queue);
                        mCurrentQueueTextView.setText("Current queue: "+currentQueue);
                        mCurrentQueueTextView.setTextColor(getResources().getColor(R.color.black));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void getQueueNumber(){
        String URL = "https://qtime-android.herokuapp.com/api/queue/doctor/"+mDoctor.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray queues = response.getJSONArray("queues");
                    for (int i = 0; i < queues.length(); i++){
                        JSONObject queue = queues.getJSONObject(i);
                        String user = queue.getString("user");
                        if(user.equals(auth.getCurrentUser().getEmail())){
                            currentQueue = i + 1;
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

}
