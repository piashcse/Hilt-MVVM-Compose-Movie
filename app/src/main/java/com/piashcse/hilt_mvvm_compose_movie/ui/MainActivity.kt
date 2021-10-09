package com.piashcse.hilt_mvvm_compose_movie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.piashcse.hilt_mvvm_compose_movie.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}