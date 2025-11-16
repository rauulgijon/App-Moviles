package com.example.appf1.activities

import android.os.Bundle
import com.example.appf1.BaseActivity // Heredamos de BaseActivity
import com.example.appf1.databinding.ActivityAboutBinding

// Heredamos de BaseActivity para que el selector de idioma funcione
class AboutActivity : BaseActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura la Toolbar
        setSupportActionBar(binding.topAppBar)
        // Pone la flecha de "volver" y le da la acción de cerrar la Activity
        binding.topAppBar.setNavigationOnClickListener { finish() }

        // Coge el texto desde el archivo strings.xml
        binding.tvAbout.text = getString(com.example.appf1.R.string.about_text)
    }
}