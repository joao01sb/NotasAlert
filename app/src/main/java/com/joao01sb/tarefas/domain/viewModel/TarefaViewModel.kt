package com.joao01sb.tarefas.domain.viewModel

import androidx.lifecycle.ViewModel
import com.joao01sb.tarefas.domain.repository.TarefaRepository
import com.joao01sb.tarefas.model.Tarefa

class TarefaViewModel(
    val tarefa: Tarefa? = null,
    val repository: TarefaRepository
) : ViewModel() {

    suspend fun salvarTarefa() {
        if (tarefa != null) {
            repository.salvarTarefa(tarefa)
            return
        }
        throw java.lang.Exception("Tarefa não selecionada")
    }
}