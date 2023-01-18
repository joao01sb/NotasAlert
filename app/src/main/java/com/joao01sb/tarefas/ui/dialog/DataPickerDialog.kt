package com.joao01sb.tarefas.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

class DataPickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // default dates
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        //return DatePickerDialog instance
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }


    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(Calendar.DAY_OF_MONTH, p3)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.YEAR, p1)

        val dataSelecionada = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(calendar.time)
        val dataSelecionadaEnviar = Bundle()
        dataSelecionadaEnviar.putString("SELECTED_DATE", dataSelecionada)
        setFragmentResult("REQUEST_CODE", dataSelecionadaEnviar)
    }
}