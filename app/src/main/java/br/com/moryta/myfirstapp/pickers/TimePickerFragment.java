package br.com.moryta.myfirstapp.pickers;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by moryta on 24/08/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TimePickerFragmentListener timePickerListener;

    public static TimePickerFragment newInstance(@NonNull TimePickerFragmentListener listener) {
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setTimePickerListener(listener);
        return fragment;
    }

    public TimePickerFragmentListener getTimePickerListener() {
        return this.timePickerListener;
    }

    public void setTimePickerListener(TimePickerFragmentListener listener) {
        this.timePickerListener = listener;
    }

    protected void notifyTimePickerListener(int hourOfDay, int minute) {
        if (this.timePickerListener != null) {
            this.timePickerListener.onTimeSet(hourOfDay, minute);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.notifyTimePickerListener(hourOfDay, minute);
    }

    public interface TimePickerFragmentListener {
        public void onTimeSet(int hourOfDay, int minute);
    }


}
