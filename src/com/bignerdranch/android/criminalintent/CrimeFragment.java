package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	
	public static final String EXTRA_CRIME_ID = 
			"com.bignerdranch.android.criminalintent.crime_id";
	
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final String DIALOG_DATETIME = "date_time_choose";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 1;
	private static final int REQUEST_DATETIME = 2;
	
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateTimeButton;
	private CheckBox mSolvedCheckBox;
	
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		mTitleField = (EditText)v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence c, int start, int before, int count) {
				mCrime.setTitle(c.toString());
			}
			
			public void beforeTextChanged(CharSequence c, int start, int count, int after) {
				return;
			}
			
			public void afterTextChanged(Editable c) {
				return;
			}
		});
		
		mDateTimeButton = (Button)v.findViewById(R.id.crime_datetime_choose);
		mDateTimeButton.setText(R.string.set_timedate_button_label);
		mDateTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity()
						.getSupportFragmentManager();
				DateTimeSelectFragment dialog = DateTimeSelectFragment.newInstance();
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATETIME);
				dialog.show(fm, DIALOG_DATETIME);
			}
		});
		
		mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mCrime.setSolved(isChecked);
			}
		});
		
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return ;
		}
		
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
		} else if (requestCode == REQUEST_TIME) {
			Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			mCrime.setDate(date);
		} else if (requestCode == REQUEST_DATETIME) {
			int checkedId = data.getIntExtra(
					DateTimeSelectFragment.EXTRA_CHECKID, R.id.crimeDateRadioButton);
			FragmentManager fm = getActivity()
					.getSupportFragmentManager();
			if (checkedId == R.id.crimeDateRadioButton) {
				DatePickerFragment datePickerDialog = 
						DatePickerFragment.newInstance(mCrime.getDate());
				datePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				datePickerDialog.show(fm, DIALOG_DATE);
			} else if (checkedId == R.id.crimeTimeRadioButton) {
				TimePickerFragment timePickerDialog = 
						TimePickerFragment.newInstance(mCrime.getDate());
				timePickerDialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
				timePickerDialog.show(fm, DIALOG_TIME);
			}
		}
	}

}
