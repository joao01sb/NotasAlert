package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.databinding.FragmentListaTarefasBinding
import com.joao01sb.tarefas.databinding.FragmentTarefaModificacoesBinding
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.ui.adapter.AdapterTarefas
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentListaTarefas : Fragment() {


    private lateinit var binding: FragmentListaTarefasBinding
    private val tarefasViewModel: TarefasViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaTarefasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO) {

            val adapterTarefas = tarefasViewModel.tarefaslista()?.let {
                AdapterTarefas(it) {
                    vaiParaTarefa(it)
                }
            }
            withContext(Main) {
                binding.listaTarefas.adapter = adapterTarefas
            }
        }
    }

    private fun vaiParaTarefa(tarefa: Tarefa) {
        val acao = FragmentTarefaModificacoesDirections.actionTarefaParaTarefas()
        findNavController().navigate(acao)
    }

}