package com.bignerdranch.android.criminalintent;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment {
	public static final String EXTRA_TIME = 
			"com.bignerdranch.android.time";
	
	private Date mDate;
	
	public static TimePickerFragment newInstance(Date crimeTime) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_TIME, crimeTime);
		TimePickerFragment fragment = new TimePickerFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	private TimePickerFragment() {
		return ;
	}
	
	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return ;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_TIME, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_time, null);
		
		mDate = (Date)getArguments().getSerializable(EXTRA_TIME);
		
		TimePicker timePicker = (TimePicker)v.findViewById(R.id.dialog_time_timePicker);
		timePicker.setIs24HourView(true);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mDate);
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		timePicker.setCurrentHour(hourOfDay);
		timePicker.setCurrentMinute(minute);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(mDate);
					calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
					calendar.set(Calendar.MINUTE, minute);
					mDate = calendar.getTime();
					
					getArguments().putSerializable(EXTRA_TIME, mDate);
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.time_picker_title)
			.setPositiveButton(android.R.string.ok, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							sendResult(Activity.RESULT_OK);
						}
					})
			.create();
	}

}
