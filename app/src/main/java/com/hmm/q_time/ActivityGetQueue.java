package com.hmm.q_time;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityGetQueue extends AppCompatActivity {
    private static final String TAG = "Activity Get Queue";
    private static final String EXTRA_DOCTOR = "Doctor";

    private Button mQueueNowButton;
    private Button mQueueLaterButton;
    private TextView mDoctorName;
    private Doctor mDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_queue);

        Intent intent = getIntent();
        mDoctor = intent.getParcelableExtra(EXTRA_DOCTOR);
        Log.d(TAG, mDoctor.getId() + ", " + mDoctor.getName() + ", " + mDoctor.getType());

        mDoctorName = findViewById(R.id.doctor_detail_name);
        mDoctorName.setText(mDoctor.getName());

        mQueueNowButton = findViewById(R.id.queue_now);
        mQueueNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityGetQueue.this, "QUEUE NOW", Toast.LENGTH_SHORT).show();
            }
        });
        mQueueLaterButton = findViewById(R.id.queue_later);
        mQueueLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityGetQueue.this, "QUEUEU LATER", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
