package com.lalit.devtrackr.ui.problem

import DsaAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.lalit.devtrackr.R
import com.lalit.devtrackr.data.local.Entity.DsaProblem
import com.lalit.devtrackr.databinding.FragmentProblemBinding

import com.lalit.devtrackr.viewmodel.DsaViewModel
import kotlinx.coroutines.launch

class ProblemFragment : Fragment(R.layout.fragment_problem) {

    private var _binding: FragmentProblemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DsaViewModel by viewModels()
    private lateinit var adapter: DsaAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProblemBinding.bind(view)




        binding.fabAdd.setOnClickListener {
            showAddProblemDialog()
        }

        // recycler view
        adapter = DsaAdapter()

        binding.recyclerViewProblem.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerViewProblem.adapter = adapter

        // search bar working
        binding.searchEditText.addTextChangedListener {
            adapter.filter(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allProblems.collect { problemList ->
                adapter.setData(problemList)
            }
        }

        // swipte to delete
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.adapterPosition
                val deletedProblem = adapter.getProblemAt(position)

                viewModel.delete(deletedProblem)

                Snackbar.make(
                    binding.root,
                    "Problem deleted",
                    Snackbar.LENGTH_LONG
                ).setAction("UNDO") {
                    viewModel.insert(deletedProblem)
                }.show()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerViewProblem)



    }

    private fun showAddProblemDialog() {

        val dialogBinding = com.lalit.devtrackr.databinding.DialogAddProblemBinding
            .inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        // Dropdown Options
        val platformOptions = listOf("LeetCode", "CodeStudio", "CodeChef", "HackerRank")
        val difficultyOptions = listOf("Easy", "Medium", "Hard")
        val complexityOptions = listOf("O(1)", "O(log n)", "O(n)", "O(nlog n)", "O(n²)", "O(2ⁿ)")

        dialogBinding.actPlatform.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, platformOptions)
        )

        dialogBinding.actDifficulty.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, difficultyOptions)
        )

        dialogBinding.actComplexity.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, complexityOptions)
        )

        dialogBinding.btnSave.setOnClickListener {

            val name = dialogBinding.etTitle.text.toString().trim()
            val platform = dialogBinding.actPlatform.text.toString().trim()
            val difficulty = dialogBinding.actDifficulty.text.toString().trim()
            val complexity = dialogBinding.actComplexity.text.toString().trim()

            if (name.isEmpty() || platform.isEmpty() || difficulty.isEmpty() || complexity.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val problem = DsaProblem(
                title = name,
                platform = platform,
                difficulty = difficulty,
                timecomplexity = complexity,
                datesolved = System.currentTimeMillis()
            )

            viewModel.insert(problem)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
