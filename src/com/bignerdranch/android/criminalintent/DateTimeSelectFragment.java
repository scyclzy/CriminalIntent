package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

public class DateTimeSelectFragment extends DialogFragment {

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.date_time_select_fragment, null);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(R.string.date_time_select_title)
			.setView(v)
			.setPositiveButton(android.R.string.ok, null)
			.create();
	}

}
