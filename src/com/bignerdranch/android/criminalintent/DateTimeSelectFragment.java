package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;

public class DateTimeSelectFragment extends DialogFragment {
	public static final String EXTRA_CHECKID = 
			"com.bignerdranch.android.checkid";
	
	private int mRadioButtonId;
	private RadioGroup mRadioGroup;
	
	private void sendResult(int resultCode, int radioButtonId) {
		if (getTargetFragment() == null) {
			return ;
		}
		
		Intent i = new Intent();
		i.putExtra(EXTRA_CHECKID, radioButtonId);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}
	
	public static DateTimeSelectFragment newInstance() {
		Bundle args = new Bundle();
		args.putInt(EXTRA_CHECKID, R.id.crimeDateRadioButton);
		DateTimeSelectFragment dialog = new DateTimeSelectFragment();
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.date_time_select_fragment, null);
		
		mRadioButtonId = getArguments().getInt(EXTRA_CHECKID);	
		
		mRadioGroup = (RadioGroup)v.findViewById(R.id.dateTimeChooseRadioGroup);
		mRadioGroup.check(mRadioButtonId);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mRadioButtonId = checkedId;
			}
		});
		
		return new AlertDialog.Builder(getActivity())
			.setView(v)
			.setTitle(R.string.date_time_select_title)
			.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							sendResult(Activity.RESULT_OK, mRadioButtonId);
						}
					})
			.create();
	}

}
