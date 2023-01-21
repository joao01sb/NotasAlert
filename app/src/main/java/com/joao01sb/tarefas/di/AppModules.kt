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
//
//    // com isso eu não presciso mais de fazer um factory e para poder passar parametros para esse viewmodel no provedor
    viewModel { TarefasViewModel(get()) }
    viewModel { (t: Tarefa) -> TarefaViewModel(t, get()) }

}

/*tudo bem aqui sabemos que e uma injeçã de dependencia para usar e somente chamar
* private val database: AppDataBase by inject()
* e chamar database.tarefaDao
* e usar isso como instancia de banco de dados em qualquwe activity viewmodel etc pq ele e um single então e uma unica instancia para todos que chamarem
* ja esta iniciado o koin no AppAplication*/