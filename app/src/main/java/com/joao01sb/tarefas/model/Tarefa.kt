package com.joao01sb.tarefas.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Tarefa (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val titulo: String,
    val conteudo: String,
    val data: String
    ) : java.io.Serializable