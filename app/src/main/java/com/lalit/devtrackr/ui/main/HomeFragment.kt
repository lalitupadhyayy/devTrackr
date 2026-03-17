package com.lalit.devtrackr.ui.main

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.databinding.FragmentHomeBinding
import com.lalit.devtrackr.viewmodel.DsaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DsaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        lifecycleScope.launch {
            viewModel.allProblems.collect { problems ->

                binding.tvTotalSolved.text = "Total Problems: ${problems.size}"

                val streak = calculateStreak(problems)

                binding.tvStreak.text = "$streak"

                // to find problems hard medium and easy-
                val easy = problems.count { it.difficulty == "Easy" }
                val medium = problems.count { it.difficulty == "Medium" }
                val hard = problems.count { it.difficulty == "Hard" }

                binding.tvEasy.text = "Easy: $easy"
                binding.tvMedium.text = "Medium: $medium"
                binding.tvHard.text = "Hard: $hard"

                // To create the heatmap for tracking like leetcode
                generateHeatmap(problems)

                // to show the recent added problem on home screen
                if (problems.isNotEmpty()) {

                    val latestProblem = problems.maxByOrNull { it.datesolved }

                    binding.tvLatestProblem.text =
                        "${latestProblem?.title}"

                } else {
                    binding.tvLatestProblem.text = "No problems solved yet"
                }

            }
        }
    }

    private fun calculateStreak(problems: List<DsaProblem>): Int {

        if (problems.isEmpty()) return 0

        val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

        val uniqueDates = problems
            .map { formatter.format(Date(it.datesolved)) }
            .distinct()
            .sortedDescending()

        var streak = 0
        val calendar = Calendar.getInstance()

        for (date in uniqueDates) {

            val today = formatter.format(calendar.time)

            if (date == today) {
                streak++
                calendar.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }

        return streak
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateHeatmap(problems: List<DsaProblem>) {

        val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val displayFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

        val dateMap = mutableMapOf<String, Int>()

        // Count problems per day
        for (problem in problems) {
            val date = formatter.format(Date(problem.datesolved))
            dateMap[date] = dateMap.getOrDefault(date, 0) + 1
        }

        binding.heatmapGrid.removeAllViews()

        val calendar = Calendar.getInstance()

        val totalDays = 21

// screen width
        val screenWidth = resources.displayMetrics.widthPixels

// calculate cell size dynamically
        val size = (screenWidth / totalDays) - 12  // adjust margin

        // Reverse list so oldest → newest (like real heatmap)
        val datesList = mutableListOf<Date>()
        for (i in 0 until totalDays) {
            datesList.add(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }
        datesList.reverse()

        // 🔥 IMPORTANT: 7 rows (days of week)
        binding.heatmapGrid.columnCount = totalDays / 7

        for (dateObj in datesList) {

            val dateKey = formatter.format(dateObj)
            val count = dateMap[dateKey] ?: 0

            val view = View(requireContext())


            val params = GridLayout.LayoutParams().apply {
                width = size
                height = size
                setMargins(6, 6, 6, 6)
            }

            view.layoutParams = params

            // 🎨 Better color levels
            val color = when (count) {
                0 -> "#E0E0E0"
                1 -> "#A5D6A7"
                2 -> "#66BB6A"
                3 -> "#43A047"
                else -> "#1B5E20"
            }

            view.setBackgroundColor(android.graphics.Color.parseColor(color))

            val displayDate = displayFormat.format(dateObj)

            // Tooltip
            view.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "📅 $displayDate\nSolved: $count",
                    Toast.LENGTH_SHORT
                ).show()
            }

            binding.heatmapGrid.addView(view)
        }
    }


}