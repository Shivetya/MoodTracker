package com.thuillier.guillaume.moodtracker.controller;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.thuillier.guillaume.moodtracker.R;
import com.thuillier.guillaume.moodtracker.model.*;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private ImageView mSmileyImage;
    private ImageButton mNoteAddButton;
    private ImageButton mHistoryButton;
    private RelativeLayout mMainBackground;
    private GestureDetector mGestureListener;
    private static final int MAX_MOOD = 5;
    private int mLocationInt = 3;
    private Mood mActualMood;
    private LocalDate mDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
    private String mStringDate;
    private SharedPreferences mHistory;
    private String mCommentMood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);

        mSmileyImage = findViewById(R.id.activity_main_image_smiley);
        mNoteAddButton = findViewById(R.id.activity_main_note_add_button);
        mHistoryButton = findViewById(R.id.activity_main_history_button);
        mMainBackground = findViewById(R.id.activity_main_background);

        mGestureListener = new GestureDetector(this, new MySimpleGestureListener());
        mHistory = getSharedPreferences("History.History",MODE_PRIVATE);

        mDate = LocalDate.now();
        mStringDate = mDate.format(formatter);

        if (mHistory.getInt("mood : " + mStringDate, -1) != -1){
            mLocationInt = mHistory.getInt("mood : " + mStringDate, -1);
            changeMoodVisual(mLocationInt);
            mCommentMood = mHistory.getString("comment : " + mStringDate, null);
        }

        mNoteAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogComment();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event){

        boolean consumedEvent = mGestureListener.onTouchEvent(event);
        float deltaY;

        if (consumedEvent){
            deltaY = MySimpleGestureListener.sDeltaY;

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

    public void onResume() {

        super.onResume();
        mDate = LocalDate.now();
        mStringDate = mDate.format(formatter);
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

    private void changeMoodVisual(int location){

        mActualMood = Mood.fromValues(location);

        switch (mActualMood){

            case VERY_SAD:
                verySad();
                break;

            case SAD:
                sad();
                break;

            case NORMAL:
                normal();
                break;

            case HAPPY:
                happy();
                break;

            case VERY_HAPPY:
                veryHappy();
                break;

        }
    }

    private void saveActualMood(){

        mHistory.edit().putInt("mood : " + mStringDate, mLocationInt).apply();
        mHistory.edit().putString("comment : " + mStringDate, mCommentMood).apply();
    }

    private void alertDialogComment(){

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        adBuilder.setView(input);
        adBuilder.setTitle("Commentaire");

        if (mHistory.getString("comment : " + mStringDate, null) != null){
            input.setText(mHistory.getString("comment : " + mStringDate, null));
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

    private void verySad(){
        mSmileyImage.setImageResource(R.mipmap.smiley_sad);
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.faded_red));
    }

    private void sad(){
        mSmileyImage.setImageResource(R.mipmap.smiley_disappointed);
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.warm_grey));
    }

    private void normal(){
        mSmileyImage.setImageResource(R.mipmap.smiley_normal);
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.cornflower_blue_65));
    }

    private void happy(){
        mSmileyImage.setImageResource(R.mipmap.smiley_happy);
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sage));
    }

    private void veryHappy(){
        mSmileyImage.setImageResource(R.mipmap.smiley_super_happy);
        mMainBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
        mNoteAddButton.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
        mHistoryButton.setBackgroundColor(ContextCompat.getColor(this, R.color.banana_yellow));
    }
}
