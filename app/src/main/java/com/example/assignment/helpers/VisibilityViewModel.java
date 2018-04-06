package com.example.assignment.helpers;

import android.view.View;

public class VisibilityViewModel {
    public static int hiddenIfTrue(boolean state){
        return state ? View.GONE: View.VISIBLE;
    }
    public static int visibleIfTrue(boolean state){
        return state ? View.VISIBLE: View.GONE;
    }
}
