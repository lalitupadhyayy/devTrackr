package com.lalit.devtrackr.ui.problem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.lalit.devtrackr.ui.main.DsaAdapter
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

        // ViewModel
//        viewModel = ViewModelProvider(this)[DsaViewModel::class.java]

        binding.fabAdd.setOnClickListener {
            showAddProblemDialog()
        }

        // recycler view
        adapter = DsaAdapter()

        binding.recyclerViewProblem.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerViewProblem.adapter = adapter

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

        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_add_problem, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add Problem")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                // Later we will extract data
                val name =
                    dialogView.findViewById<EditText>(R.id.etProblemName).text.toString()
                val platform =
                    dialogView.findViewById<EditText>(R.id.etPlatform).text.toString()
                val difficulty =
                    dialogView.findViewById<EditText>(R.id.etDifficulty).text.toString()
                val complexity =
                    dialogView.findViewById<EditText>(R.id.etComplexity).text.toString()

                val problem = DsaProblem(
                    title = name,
                    platform = platform,
                    difficulty = difficulty,
                    timecomplexity = complexity,
                    datesolved = System.currentTimeMillis()
                )

                viewModel.insert(problem)

//                Toast.makeText(, "saved", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
