package com.thuillier.guillaume.moodtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.thuillier.guillaume.moodtracker.R;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[] mTv = new TextView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTv[0] = findViewById(R.id.history_activity_first_TV);
        mTv[1] = findViewById(R.id.history_activity_second_TV);
        mTv[2] = findViewById(R.id.history_activity_third_TV);
        mTv[3] = findViewById(R.id.history_activity_fourth_TV);
        mTv[4] = findViewById(R.id.history_activity_fifth_TV);
        mTv[5] = findViewById(R.id.history_activity_sixth_TV);
        mTv[6] = findViewById(R.id.history_activity_seventh_TV);

        for (int i = 0; i < mTv.length; i++) {
            mTv[i].setOnClickListener(this);

        }
    }

    private void verySad(TextView tv){

    }

    @Override
    public void onClick(View v) {

    }
}
