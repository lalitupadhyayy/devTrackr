package com.lalit.devtrackr.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.databinding.ActivityMainBinding
import com.lalit.devtrackr.ui.problem.ProblemFragment
import com.lalit.devtrackr.viewmodel.DsaViewModel
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: DsaViewModel
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //applying view binding to access all the ids of layout manager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[DsaViewModel::class.java]
//
//        val testProblem = DsaProblem(
//            title = "Two Sum",
//            platform = "LeetCode",
//            difficulty = "Easy",
//            topic = "Array",
//            datesolved = System.currentTimeMillis(),
//            notes = "Classic hashmap problem"
//        )
//
//        viewModel.insert(testProblem)

//        val adapter = DsaAdapter()
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        lifecycleScope.launch {
//            viewModel.allProblems.collect { list ->
//                adapter.setData(list)
//            }
//        }

//        private fun replaceFragment(fragment: HomeFragment){
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.)
//        }

        // Load default fragment (Home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Bottom Navigation Click Listener
        binding.bottomMenu.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .commit()
                    true
                }

                R.id.dsaProblemFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProblemFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }


    }
}