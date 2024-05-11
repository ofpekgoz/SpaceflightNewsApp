package com.omerfpekgoz.spaceflightnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omerfpekgoz.spaceflightnewsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}