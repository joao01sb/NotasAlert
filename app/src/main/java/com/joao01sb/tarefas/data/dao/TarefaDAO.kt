package com.joao01sb.tarefas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.joao01sb.tarefas.model.Tarefa

@Dao
interface TarefaDAO {

    @Query("SELECT * FROM Tarefa")
     fun buscarTodas(): List<Tarefa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun salva(tarefa: Tarefa)

    @Delete
     fun remove(tarefa: Tarefa)

}