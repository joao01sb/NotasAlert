package com.joao01sb.tarefas.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickDialogFragment : DialogFragment() {

    private lateinit var timeListener: TimePickerDialog.OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        return TimePickerDialog(
            activity, timeListener, hour, minute,
            DateFormat.is24HourFormat(activity)
        )
    }


    fun setTimeSetListener(listener: TimePickerDialog.OnTimeSetListener) {
        timeListener = listener
    }

    companion object {
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickDialogFragment {
            val instance = TimePickDialogFragment()
            instance.setTimeSetListener(listener)
            return instance
        }
    }
}