package com.joao01sb.tarefas.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.databinding.TaskItemBinding
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import java.text.SimpleDateFormat
import java.util.*

class AdapterTarefas(
    private val tarefas: List<Tarefa> = emptyList(),
    var onItemClickListener: (tarefa: Tarefa) -> Unit = {}
) : RecyclerView.Adapter<AdapterTarefas.ViewHolder>() {

    inner class ViewHolder(
        private val binding: TaskItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun vincularPersonagemComDados(tarefa: Tarefa) {

            binding.nomeTarefa.text = tarefa.titulo
            binding.descricaoDaTarefa.text = tarefa.conteudo
            if (binding.descricaoDaTarefa.text.isBlank())
                binding.descricaoDaTarefa.visibility = View.GONE

            verificaDataDaTarefa(tarefa.data)
            binding.dataVencimentoDaTarefa.text = tarefa.data.formatDate()

            binding.root.setOnClickListener {
                onItemClickListener(tarefa)
            }
        }

        private fun verificaDataDaTarefa(tarefaData: String) {
            val data =
                SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).parse(tarefaData)
            if (data.time - System.currentTimeMillis() > 0)
                binding.iconeVencimento.setImageResource(R.drawable.ic_time_valido)
            else
                binding.iconeVencimento.setImageResource(R.drawable.ic_time_vencido)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        tarefas[position].let { holder.vincularPersonagemComDados(it) }

    override fun getItemCount(): Int = tarefas.size
}
