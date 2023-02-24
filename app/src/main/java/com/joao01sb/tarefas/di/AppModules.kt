package com.joao01sb.tarefas.di

import androidx.room.Room
import com.joao01sb.tarefas.data.AppDatabase
import com.joao01sb.tarefas.data.dao.TarefaDAO
import com.joao01sb.tarefas.domain.repository.TarefaRepository
import com.joao01sb.tarefas.domain.viewModel.TarefaViewModel
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.model.Tarefa
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDatabase = module {
    // instancia do banco
    single<AppDatabase>{ Room.databaseBuilder(
        get(),
        AppDatabase::class.java,
        "tarefas.db"
    ).build() }

    single<TarefaDAO> { get<AppDatabase>().tarefaDAO }

    single<TarefaRepository> { TarefaRepository(get()) }

    viewModel { TarefasViewModel(get()) }
    viewModel { (t: Tarefa) -> TarefaViewModel(t, get()) }

}
