package com.ebt.newsapicleanarchitecture.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ebt.newsapicleanarchitecture.R
import com.ebt.newsapicleanarchitecture.databinding.FragmentCalendarDialogBinding
import com.ebt.newsapicleanarchitecture.presentation.viewmodel.SharedCalendarViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class CalendarDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCalendarDialogBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedCalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timeInMillis = Calendar.getInstance().timeInMillis
        binding.startCalendarView.maxDate = timeInMillis
        binding.endCalendarView.maxDate = timeInMillis
        binding.doneButton.setOnClickListener {
            if (sharedViewModel.isDateRangeSelectedCorrectly()
            ) {
                sharedViewModel.changeCalendar()
                dismiss()
            } else {
                Toast.makeText(requireContext(), R.string.choose_date_error, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.startCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            sharedViewModel.changeStartCalendar(day = dayOfMonth, month = month, year = year)
        }
        binding.endCalendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            sharedViewModel.changeEndCalendar(day = dayOfMonth, month = month, year = year)
        }

        binding.startCalendarView.date = sharedViewModel.sCalendar!!.timeInMillis
        binding.endCalendarView.date = sharedViewModel.eCalendar!!.timeInMillis
    }
}