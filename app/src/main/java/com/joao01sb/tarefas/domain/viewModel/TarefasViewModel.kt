package com.joao01sb.tarefas.domain.viewModel

import androidx.lifecycle.ViewModel
import com.joao01sb.tarefas.domain.repository.TarefaRepository

class TarefasViewModel(
    val repository: TarefaRepository
) : ViewModel() {



}