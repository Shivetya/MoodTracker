package com.thuillier.guillaume.moodtracker.model;

public enum Mood {
    VERY_SAD (0),
    SAD (1),
    NORMAL (2),
    HAPPY (3),
    VERY_HAPPY (4);

    int mNumber;

    Mood(int number) {
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    public static Mood fromValues(Integer number){

        Mood moodTested = null;

        for (Mood m : Mood.values()){
            if (number == m.getNumber()){
                moodTested = m;
            }
        }

        return moodTested;
    }
}
