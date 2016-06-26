package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	private static final String EXTRA_TIME = 
			"com.bignerdranch.android.time";
	
	public static TimePickerFragment newInstance() {
		Bundle args = new Bundle();
		//args.putSerializable(EXTRA_TIME, null);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private TimePickerFragment() {
		return ;
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_time, null);
		
		TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setIs24HourView(true);
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.time_picker_title)
			.setPositiveButton(android.R.string.ok, null)
			.create();
	}

}
