package com.hmm.q_time;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private static final String TAG = "Recycler Doctor Adapter";

    private ArrayList<String> mDoctorNames = new ArrayList<>();
    private ArrayList<Integer> mDoctorImages = new ArrayList<>();
    Context mContext;

    public DoctorAdapter(Context context, ArrayList<String> doctorNames, ArrayList<Integer> doctorImages) {
        mDoctorNames = doctorNames;
        mDoctorImages = doctorImages;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_details, viewGroup, false);
        ViewHolder holder = new ViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext).asBitmap().load(mDoctorImages.get(i)).into(viewHolder.mImage);

        viewHolder.mImageName.setText(mDoctorNames.get(i));

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mDoctorNames.get(i));

                Toast.makeText(mContext, mDoctorNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctorNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView mImage;
        TextView mImageName;
        RelativeLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.doctor_image);
            mImageName = itemView.findViewById(R.id.doctor_name);
            mParentLayout = itemView.findViewById(R.id.doctor_detail_parent);
        }
    }
}
