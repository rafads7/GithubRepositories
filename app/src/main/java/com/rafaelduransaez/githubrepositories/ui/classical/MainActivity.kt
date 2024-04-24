package com.rafaelduransaez.githubrepositories.ui.classical

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaelduransaez.githubrepositories.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)
    }
}