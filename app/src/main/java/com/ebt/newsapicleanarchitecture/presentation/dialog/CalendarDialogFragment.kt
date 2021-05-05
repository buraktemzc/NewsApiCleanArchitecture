package com.ebt.newsapicleanarchitecture.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
        binding.calendarView.maxDate = Calendar.getInstance().timeInMillis
        binding.doneButton.setOnClickListener {
            sharedViewModel.changeCalendar()
            dismiss()
        }
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val currentMonth = month + 1
            sharedViewModel.date = "$year-$currentMonth-$dayOfMonth"
        }
    }
}