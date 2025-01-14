package com.joao01sb.tarefas.ui.fragment

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.joao01sb.tarefas.databinding.FragmentCriarTarefaBinding
import com.joao01sb.tarefas.domain.viewModel.TarefasViewModel
import com.joao01sb.tarefas.extra.Util.formatDate
import com.joao01sb.tarefas.model.Tarefa
import com.joao01sb.tarefas.notification.Notification
import com.joao01sb.tarefas.notification.Notification.Companion.notificationID
import com.joao01sb.tarefas.notification.channelID
import com.joao01sb.tarefas.notification.messageExtra
import com.joao01sb.tarefas.notification.titleExtra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class CriarTarefaFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentCriarTarefaBinding
    private val tarefaViewModel: TarefasViewModel by viewModel()
    private val calendar = Calendar.getInstance()
    private lateinit var dataSelecionada: String

    private var diaSelecionado = 0
    private var mesSelecionado = 0
    private var anoSelecionado = 0
    private var horaSelecionada = 0
    private var minutoSelecionado = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCriarTarefaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            dataTarefa.setOnClickListener { configurarData() }
            salvaTarefaButtom.setOnClickListener { saveNote() }
            iconeVoltarEditar.setOnClickListener { back() }
        }
        createNotificationChannel()
    }

    private fun saveNote() {
        if (this::dataSelecionada.isInitialized) {
            if (binding.nomeDaTarefaValor.text.isNullOrBlank()) {
                Toast.makeText(
                    requireContext(),
                    "Informe um titulo para a tarefa",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newNote = Tarefa(
                    id = null,
                    titulo = binding.nomeDaTarefaValor.text.toString(),
                    conteudo = binding.descricaoDaTarefaValor.text.toString(),
                    data = dataSelecionada
                )
                try {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        tarefaViewModel.salvarTarefa(newNote)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao salvar note verifique todos os campos e tente novamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
                if ((SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).parse(dataSelecionada)?.time ?: 0) - System.currentTimeMillis() > 0) {
                    scheduleNotification()
                }
                findNavController().popBackStack()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Informe uma data para a tarefa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun configurarData() {
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        DatePickerDialog(requireActivity(), this, year, month, day).show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, p3)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.YEAR, p1)
        diaSelecionado = p3
        mesSelecionado = p2
        anoSelecionado = p1

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), this, hour, minutes, true).show()
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, p1)
        calendar.set(Calendar.MINUTE, p2)
        horaSelecionada = p1
        minutoSelecionado = p2

        dataSelecionada = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(calendar.time)
        if((SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).parse(dataSelecionada)?.time ?: 0) -  System.currentTimeMillis() > 0)
            binding.dataTarefa.text = dataSelecionada.formatDate()
        else
            Toast.makeText(requireContext(), "Somente possivel selecionar datas futuras", Toast.LENGTH_SHORT).show()

    }

    private fun scheduleNotification()
    {
        val intent = Intent(requireActivity().applicationContext, Notification::class.java)
        val title = binding.nomeDaTarefaValor.text.toString()
        val message = binding.descricaoDaTarefaValor.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity().applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(requireActivity().applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(requireActivity().applicationContext)

        AlertDialog.Builder(requireContext())
            .setTitle("Notificação")
            .setMessage(
                "Titulo: " + title +
                        "\nMensagem: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    private fun back() {
        findNavController().popBackStack()
    }

    private fun getTime(): Long {
        calendar.set(anoSelecionado, mesSelecionado, diaSelecionado, horaSelecionada, minutoSelecionado)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = requireActivity().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}