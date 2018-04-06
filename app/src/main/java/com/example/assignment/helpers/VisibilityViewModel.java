package com.example.assignment.helpers;

import android.view.View;

public class VisibilityViewModel {
    public static int getHiddenIfTrue(boolean state){
        return state ? View.GONE: View.VISIBLE;
    }
    public static int getVisibleIfTrue(boolean state){
        return state ? View.VISIBLE: View.GONE;
    }
}
