package com.example.campsnatch

import android.os.Bundle
import com.example.campsnatch.databinding.ActivityMainBinding
import com.example.campsnatch.support.CampsnatchActivity

class MainActivity : CampsnatchActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) { // make sure to add the fragment only once
            replaceFragment(FragmentMain(), binding.fragContainer.id)
        }
    }
}