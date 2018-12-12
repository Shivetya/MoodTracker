package com.thuillier.guillaume.moodtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.thuillier.guillaume.moodtracker.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv1, mTv2, mTv3, mTv4, mTv5, mTv6, mTv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTv1 = findViewById(R.id.history_activity_first_TV);
        mTv2 = findViewById(R.id.history_activity_second_TV);
        mTv3 = findViewById(R.id.history_activity_third_TV);
        mTv4 = findViewById(R.id.history_activity_fourth_TV);
        mTv5 = findViewById(R.id.history_activity_fifth_TV);
        mTv6 = findViewById(R.id.history_activity_sixth_TV);
        mTv7 = findViewById(R.id.history_activity_seventh_TV);
    }

    private void verySad(TextView tv){

    }

    @Override
    public void onClick(View v) {

    }
}
