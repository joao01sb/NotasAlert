package com.joao01sb.tarefas.ui.fragment

import android.opengl.Visibility
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerView()
        binding.fab.setOnClickListener {
            goEditeNote(null)
        }
    }

    private fun configurarRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listNote = tarefasViewModel.tarefaslista()
            if (listNote != null) {
                this@FragmentListNotes.adapter = AdapterTarefas(listNote) {
                    goEditeNote(it ?: null)
                }
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.listaTarefas.adapter = adapter
                }
            } else {
                binding.listaTarefas.visibility = View.GONE
                binding.infoTarefasVazia.visibility = View.VISIBLE
            }
        }
    }

    private fun goEditeNote(tarefa: Tarefa?) {
        val direction = FragmentListNotesDirections.goListNotesForEditeNote(tarefa)
        findNavController().navigate(direction)
    }

}