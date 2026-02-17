package com.lalit.devtrackr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.viewmodel.DsaViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: DsaViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[DsaViewModel::class.java]

        val testProblem = DsaProblem(
            title = "Two Sum",
            platform = "LeetCode",
            difficulty = "Easy",
            topic = "Array",
            datesolved = System.currentTimeMillis(),
            notes = "Classic hashmap problem"
        )

        viewModel.insert(testProblem)
    }
}