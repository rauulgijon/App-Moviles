package com.example.appf1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appf1.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.tvAbout.text = getString(com.example.appf1.R.string.about_text)
    }
}
