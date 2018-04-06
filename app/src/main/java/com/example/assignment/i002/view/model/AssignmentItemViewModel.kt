package com.example.assignment.i002.view.model

import android.content.Context

class AssignmentItemViewModel(val text:String, private val function: (context:Context, text:String) -> Unit){
    fun onClick(context: Context){
        function.invoke(context,text)
    }
}