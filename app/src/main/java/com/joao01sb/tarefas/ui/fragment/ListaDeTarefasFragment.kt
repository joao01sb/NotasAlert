package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joao01sb.tarefas.databinding.FragmentListaTarefasBinding
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.ui.adapter.AdapterTarefas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaDeTarefasFragment : Fragment() {

    private lateinit var binding: FragmentListaTarefasBinding
    private val tarefasViewModel: TarefasViewModel by viewModel()
    private var adapter: AdapterTarefas? = null
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaTarefasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configRecyclerView()
        binding.fab.setOnClickListener {
            criarNovaTarefa()
        }
    }

    private fun configRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listNote = tarefasViewModel.tarefaslista()
            if (!listNote.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    binding.infoTarefasVazia.visibility = View.GONE
                    this@ListaDeTarefasFragment.adapter = AdapterTarefas(listNote) { note ->
                        vaiParaTarefaDetalhes(note)
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

    private fun vaiParaTarefaDetalhes(tarefa: Tarefa) {
        val direction = ListaDeTarefasFragmentDirections.goNotesForNoteDetails(tarefa)
        navController.navigate(direction)
    }

    private fun criarNovaTarefa() {
        val direction = ListaDeTarefasFragmentDirections.goNotesForEditeNote()
        navController.navigate(direction)
    }

}