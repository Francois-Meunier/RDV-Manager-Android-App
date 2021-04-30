package com.example.rdv_app_meunier_nicolas;

import android.app.TimePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    int minute,hour;
    TimePickerDialog.OnTimeSetListener timeSet;

    public TimePickerFragment(){

    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime){
        timeSet=onTime;
    }
    //On attribut les valeur au parametre
    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        hour=args.getInt("hour");
        minute=args.getInt("minute");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new TimePickerDialog(getActivity(),timeSet,minute,hour,true);
    }
}
