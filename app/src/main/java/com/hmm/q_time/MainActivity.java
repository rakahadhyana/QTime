package com.hmm.q_time;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        QueueFragment.OnFragmentInteractionListener, NearbyFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout mTablayout = findViewById(R.id.tab_layout);
        mTablayout.addTab(mTablayout.newTab().setIcon(R.drawable.ic_home_tab));
        mTablayout.addTab(mTablayout.newTab().setIcon(R.drawable.ic_queue_tab));
        mTablayout.addTab(mTablayout.newTab().setIcon(R.drawable.ic_nearby_tab));
        mTablayout.addTab(mTablayout.newTab().setIcon(R.drawable.ic_message_tab));
        mTablayout.addTab(mTablayout.newTab().setIcon(R.drawable.ic_profile_tab));
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL );

        final ViewPager mViewPager = findViewById(R.id.view_pager);
        final PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTablayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
