package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joao01sb.tarefas.databinding.FragmentListNotesBinding
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.ui.adapter.AdapterTarefas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentListNotes : Fragment() {

    private lateinit var binding: FragmentListNotesBinding
    private val tarefasViewModel: TarefasViewModel by viewModel()
    private var adapter: AdapterTarefas? = null
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
        binding.fab.setOnClickListener {
            goEditeNote()
        }
    }

    private fun configRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listNote = tarefasViewModel.tarefaslista()
            if (!listNote.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    binding.infoTarefasVazia.visibility = View.GONE
                    this@FragmentListNotes.adapter = AdapterTarefas(listNote) { note ->
                        goDetailsNote(note)
                    }
                    binding.listaTarefas.adapter = adapter
                }
            } else {
                withContext(Dispatchers.Main) {
                    binding.listaTarefas.visibility = View.GONE
                    binding.infoTarefasVazia.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun goDetailsNote(tarefa: Tarefa) {
        val direction = FragmentListNotesDirections.goNotesForNoteDetails(tarefa)
        navController.navigate(direction)
    }

    private fun goEditeNote() {
        val direction = FragmentListNotesDirections.goNotesForEditeNote()
        navController.navigate(direction)
    }

}