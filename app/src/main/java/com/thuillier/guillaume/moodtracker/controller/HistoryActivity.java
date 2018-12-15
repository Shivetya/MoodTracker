package com.thuillier.guillaume.moodtracker.controller;

import android.content.SharedPreferences;
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
import org.threeten.bp.LocalDate;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView[] mTv = new TextView[7];
    private SharedPreferences mHistory;
    private String[] mComments = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistory = getSharedPreferences("History.History",MODE_PRIVATE);

        mTv[6] = findViewById(R.id.history_activity_first_TV);
        mTv[5] = findViewById(R.id.history_activity_second_TV);
        mTv[4] = findViewById(R.id.history_activity_third_TV);
        mTv[3] = findViewById(R.id.history_activity_fourth_TV);
        mTv[2] = findViewById(R.id.history_activity_fifth_TV);
        mTv[1] = findViewById(R.id.history_activity_sixth_TV);
        mTv[0] = findViewById(R.id.history_activity_seventh_TV);

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

        LocalDate date = LocalDate.now();
        ConstraintLayout constrainLayout = findViewById(R.id.history_activity_constraint_layout);
        ConstraintSet set = new ConstraintSet();
        set.clone(constrainLayout);
        Mood mood;

        for (int i = 0; i < mTv.length; i++) {

            mood = Mood.fromValues(mHistory.getInt("mood : " + date.minusDays(i + 1).format(MainActivity.formatter), 3));

            set.constrainPercentWidth(mTv[i].getId(), mood.getPercent());
            set.applyTo(constrainLayout);
            mTv[i].setBackgroundColor(ContextCompat.getColor(this, mood.getNumberColor()));

            if (mHistory.getString("comment : " + date.minusDays(i + 1).format(MainActivity.formatter), null ) != null) {
                mTv[i].setOnClickListener(this);
                mTv[i].setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.ic_comment_black_48px,0);
                mComments[i] = mHistory.getString("comment : " + date.minusDays(i + 1).format(MainActivity.formatter), null );
            }
        }
    }

}
