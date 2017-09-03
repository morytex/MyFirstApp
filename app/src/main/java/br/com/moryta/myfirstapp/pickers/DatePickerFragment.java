package br.com.moryta.myfirstapp.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by moryta on 21/08/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final int ANY_DATES = 0;
    public static final int PAST_OR_PRESENT_DATES = 1;
    public static final int FUTURE_OR_PRESENT_DATES = 2;

    private DatePickerFragmentListener datePickerListener;
    private int restrictionFlag;

    /**
     * Build new instance of DatePickerFragment with no restriction of shown dates.
     * @param listener
     * @return
     */
    public static DatePickerFragment newInstance(@NonNull DatePickerFragmentListener listener) {
        return DatePickerFragment.newInstance(listener, ANY_DATES);
    }

    /**
     * Build new instance of DatePickerFragment with restriction options.
     * @param listener
     * @param restrictionFlag 1 = past or present dates only
     *                        2 = future or present dates only
     *                        Otherwise, no restriction
     * @return
     */
    public static DatePickerFragment newInstance(@NonNull DatePickerFragmentListener listener, @NonNull int restrictionFlag) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDatePickerListener(listener);
        fragment.setRestrictionFlag(restrictionFlag);
        return fragment;
    }

    public DatePickerFragmentListener getDatePickerListener() {
        return this.datePickerListener;
    }

    public void setDatePickerListener(DatePickerFragmentListener listener) {
        this.datePickerListener = listener;
    }

    protected void notifyDatePickerListener(Date date) {
        if(this.datePickerListener != null) {
            this.datePickerListener.onDateSet(date);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

        // Set optional restrictions of shown dates
        switch (this.restrictionFlag) {
            case PAST_OR_PRESENT_DATES:
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                break;
            case FUTURE_OR_PRESENT_DATES:
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                break;
            default:
        }

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        this.notifyDatePickerListener(calendar.getTime());
    }

    private int getRestrictionFlag() {
        return restrictionFlag;
    }

    private void setRestrictionFlag(int restrictionFlag) {
        this.restrictionFlag = restrictionFlag;
    }

    public interface DatePickerFragmentListener {
        public void onDateSet(Date date);
    }
}
