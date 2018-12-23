package com.thuillier.guillaume.moodtracker.controller;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.thuillier.guillaume.moodtracker.R;
import com.thuillier.guillaume.moodtracker.model.Mood;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[] mTv = new TextView[7];
    private String[] mComments = new String[7];
    private HistorySharedPreferences mHistory;

    /**
     * Here, mTv[0] is the lower textview in the screen, not the higher.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistory = new HistorySharedPreferences(this);

        mTv[0] = findViewById(R.id.history_activity_yesterday_TV);
        mTv[1] = findViewById(R.id.history_activity_two_days_ago_TV);
        mTv[2] = findViewById(R.id.history_activity_three_days_ago_TV);
        mTv[3] = findViewById(R.id.history_activity_four_days_ago_TV);
        mTv[4] = findViewById(R.id.history_activity_five_days_ago_TV);
        mTv[5] = findViewById(R.id.history_activity_six_days_ago_TV);
        mTv[6] = findViewById(R.id.history_activity_week_ago_TV);

        setVisualAndComments();
    }


    @Override
    public void onClick(View v) {

        for (int i = 0; i < 7; i++){
            if (v == mTv[i]){
                Toast.makeText(this, mComments[i], Toast.LENGTH_LONG ).show();
            }
        }

    }

    /**
     * This method check if mood and comment exist for the past week. if there are, each bars will have width associated
     * to the mood and an icon. If there is only mood saved and not comment, there will be no icon.
     * If there is no mood saved, the mood "HAPPY" and no comment is by default.
     */

    private void setVisualAndComments(){

        ConstraintLayout constraintLayout = findViewById(R.id.history_activity_constraint_layout);
        ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        Mood mood;
        int locationIntAgo;

        for (int i = 0; i < mTv.length; i++) {

            locationIntAgo = mHistory.getMoodXDaysAgo(i + 1);
            mood = Mood.values()[locationIntAgo];

            set.constrainPercentWidth(mTv[i].getId(), mood.getPercent());
            set.applyTo(constraintLayout);
            mTv[i].setBackgroundColor(ContextCompat.getColor(this, mood.getNumberColor()));

            if (mHistory.getCommentXDaysAgo(i + 1) != null) {
                mTv[i].setOnClickListener(this);
                mTv[i].setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_comment_black_48px,0);
                mComments[i] = mHistory.getCommentXDaysAgo(i + 1);
            }
        }
    }

}
