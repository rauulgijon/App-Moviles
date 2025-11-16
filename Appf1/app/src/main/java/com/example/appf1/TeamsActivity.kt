package com.example.appf1.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appf1.BaseActivity
import com.example.appf1.adapter.TeamAdapter
import com.example.appf1.databinding.ActivityTeamsBinding
import viewmodel.RaceViewModel

class TeamsActivity : BaseActivity() {

    private lateinit var binding: ActivityTeamsBinding
    private val vm: RaceViewModel by viewModels()
    private val adapter = TeamAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.recyclerViewTeams.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTeams.adapter = adapter

        // Observamos los equipos de la BBDD
        vm.allTeams.observe(this) { teams ->
            adapter.submitList(teams)
        }
    }
}