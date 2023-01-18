package com.joao01sb.tarefas.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.joao01sb.tarefas.databinding.FragmentListTaskBinding
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.ui.adapter.AdapterTarefas
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentListaTarefas : Fragment() {


    private lateinit var binding: FragmentListTaskBinding
    private val tarefasViewModel: TarefasViewModel by viewModel()
    lateinit var adapter: AdapterTarefas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(IO) {
            val adapterTarefas = tarefasViewModel.tarefaslista()
            adapterTarefas?.let {
                adapter = AdapterTarefas(adapterTarefas) {
                    vaiParaTarefa(it)
                }
            }
            withContext(Main) {
                if (tarefasViewModel.tarefaslista()!!.isEmpty()) {
                    binding.listaTarefas.visibility = View.GONE
                    binding.infoTarefasVazia.visibility = View.VISIBLE
                }
                binding.listaTarefas.adapter = adapter
            }
            binding.infoTarefasVazia.visibility = View.GONE
        }
    }

    private fun vaiParaTarefa(tarefa: Tarefa) {
        val acao = FragmentListaTarefasDirections.deTarefasParaTarefa(tarefa)
        findNavController().navigate(acao)
    }

}