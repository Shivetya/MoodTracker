package com.thuillier.guillaume.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.thuillier.guillaume.moodtracker.R;
import com.thuillier.guillaume.moodtracker.model.*;

public class MainActivity extends AppCompatActivity {

    private ImageView mSmileyImage;
    private ImageButton mNoteAddButton;
    private ImageButton mHistoryButton;
    private RelativeLayout mMainBackground;
    private GestureDetector mGestureListener;
    private OnFlingListener mOnFlingListener;
    private static final int MAX_MOOD = Mood.values().length;
    private int mLocationInt = (MAX_MOOD+1) /2;
    private HistorySharedPreferences mHistory;
    private String mCommentMood = null;
    private Mood mActualMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        mSmileyImage = findViewById(R.id.activity_main_image_smiley);
        mNoteAddButton = findViewById(R.id.activity_main_note_add_button);
        mHistoryButton = findViewById(R.id.activity_main_history_button);
        mMainBackground = findViewById(R.id.activity_main_background);
        TextView shareTv = findViewById(R.id.activity_main_share_textview);

        mOnFlingListener = new OnFlingListener();
        mGestureListener = new GestureDetector(this, mOnFlingListener);
        mHistory = new HistorySharedPreferences(this);

        mLocationInt = mHistory.todayMood();
        mCommentMood = mHistory.todayComment();


        mNoteAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogComment();
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivityIntent = new Intent (MainActivity.this, HistoryActivity.class);
                startActivity(historyActivityIntent);
            }
        });

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMood();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event){

        boolean consumedEvent = mGestureListener.onTouchEvent(event);
        float deltaY;

        if (consumedEvent){
            deltaY = mOnFlingListener.getDeltaY();

            if(deltaY > 0) swipeUp();
            else swipeDown();

            return true;
        }
        else return false;
    }

    public void onStop(){

        saveActualMood();
        super.onStop();
    }

    private void swipeUp(){

        if (mLocationInt + 1 < MAX_MOOD) {
            mLocationInt++;
            changeMoodVisual(mLocationInt);
        }
    }

    private void swipeDown(){

        if(mLocationInt - 1  >= 0) {
            mLocationInt--;
            changeMoodVisual(mLocationInt);
        }
    }

    /**
     * This method change everything that depends on the active mood : smiley, background color, background color of buttons.
     * @param location = mLocationInt, that allow program to know which mood to display.
     */
    private void changeMoodVisual(int location){

        mActualMood = Mood.fromValues(location);

        mSmileyImage.setImageResource(mActualMood.getSmileyImage());
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, mActualMood.getNumberColor()));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, mActualMood.getNumberColor()));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, mActualMood.getNumberColor()));
        saveActualMood();
    }

    /**
     * Method that create and display a dialog box. The user can write a comment, it will be saved and display when wanted.
     */
    private void alertDialogComment(){

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        adBuilder.setView(input);
        adBuilder.setTitle("Commentaire");

        if (mHistory.todayComment() != null){
            input.setText(mHistory.todayComment());
        }

        adBuilder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCommentMood = input.getText().toString();
                saveActualMood();
            }
        });

        adBuilder.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        adBuilder.show();
    }


    /**
     * Method that allow to share the description of the mood with the comment (if it exists).
     */
    private void shareMood(){

        String stringToShare = "Hello ! Je suis aujourd'hui d'humeur " + getString(mActualMood.getDescription()) + " !";

        if (mCommentMood != null){
            stringToShare += "\n Voilà pourquoi : " + mCommentMood;
        }

        Intent shareMood = new Intent(Intent.ACTION_SEND);
        shareMood.setType("text/plain");
        shareMood.putExtra(Intent.EXTRA_TEXT, stringToShare);
        startActivity(shareMood);
    }

    private void saveActualMood(){
        mHistory.saveActualMood(mLocationInt, mCommentMood);
    }
}
