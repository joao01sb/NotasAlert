package com.joao01sb.tarefas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joao01sb.tarefas.data.dao.TarefaDAO
import com.joao01sb.tarefas.model.Tarefa

@Database(entities = [Tarefa::class], version = 1, exportSchema = true )
abstract class AppDatabase : RoomDatabase() {
    abstract val tarefaDAO : TarefaDAO

//    companion object {
//        private lateinit var databaseIntancia: AppDatabase
//        fun getInstance(context: Context): AppDatabase {
//            if(::databaseIntancia.isInitialized) return databaseIntancia
//            databaseIntancia = Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                "tarefas.db"
//            ).build()
//            return databaseIntancia
//        }
//
//    }

}