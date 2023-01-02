package com.joao01sb.tarefas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.databinding.FragmentTarefaModificacoesBinding
import com.joao01sb.tarefas.model.Tarefa
import kotlinx.coroutines.launch

class FragmentTarefaModificacoes : Fragment() {

    private lateinit var binding: FragmentTarefaModificacoesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTarefaModificacoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            binding.salvaTarefaButtom.setOnClickListener {
                Tarefa(
                    titulo = binding.nomeDaTarefaValor.text.toString(),
                    conteudo = binding.descricaoDaTarefaValor.text.toString(),
                    data = binding.dataDaTarefaValor.text.toString()
                )
                lifecycleScope.launch {

                }
            }
        }
    }

}