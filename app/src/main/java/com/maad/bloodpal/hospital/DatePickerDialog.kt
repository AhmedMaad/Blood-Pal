package com.maad.bloodpal.hospital

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

class DatePickerDialog:DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(), activity as DatePickerDialog.OnDateSetListener?, year, month, day
        )
    }

}