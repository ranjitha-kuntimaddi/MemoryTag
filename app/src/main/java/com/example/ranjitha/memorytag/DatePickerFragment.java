package com.example.ranjitha.memorytag;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment{

    public static final String EXTRA_DATE = "Extra to pass new Date";
    public static final String EXTRA_TIME = "Extra to pass new Time";
    private Date memoryDate;
    //private Time memoryTime;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode){

        if(getTargetFragment()==null){
            return;
        }
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE,memoryDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        memoryDate = (Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(memoryDate);

        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_picker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                memoryDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                getArguments().putSerializable(EXTRA_DATE, memoryDate);
            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.id.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }).create();
        /*.setNegativeButton(R.string.time_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onNegativeClick(Activity.RESULT_OK);
                    }
                }).create();*/

    }

   /* public void onNegativeClick(int resultCode){

        if (resultCode!=Activity.RESULT_OK){
            return;
        }

        Intent i = new Intent();
        i.putExtra(EXTRA_TIME,memoryTime);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,i);

    }*/

}
