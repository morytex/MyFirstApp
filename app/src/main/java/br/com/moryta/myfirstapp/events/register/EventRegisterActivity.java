package br.com.moryta.myfirstapp.events.register;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import br.com.moryta.myfirstapp.DatePickerFragment;
import br.com.moryta.myfirstapp.Extras;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.TimePickerFragment;
import br.com.moryta.myfirstapp.events.EventsContract;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventRegisterActivity extends AppCompatActivity
        implements EventRegisterContract.View
        , DatePickerFragment.DatePickerFragmentListener
        , TimePickerFragment.TimePickerFragmentListener
        , View.OnClickListener
        , TextWatcher {

    private static final String TAG = "EventRegisterActivity";
    private static final int RC_REGISTER_EVENT = 1001;

    private Long petId;
    private String petName;

    @BindView(R.id.etEventTitle)
    EditText etEventTitle;

    @BindView(R.id.tvEventDate)
    TextView tvEventDate;

    @BindView(R.id.tvEventTime)
    TextView tvEventTime;

    @BindView(R.id.btnRegisterEvent)
    Button btnRegisterEvent;

    private EventRegisterContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        this.petId = savedInstanceState.getLong(Extras.PET_ID);
        this.petName = savedInstanceState.getString(Extras.PET_NAME);

        // Instance of presenter
        this.mPresenter = new EventRegisterPresenter(EventRegisterActivity.this
                , ((MyApplication) getApplication()).getDaoSession());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Adding watcher on fields to disable/enable register button
        this.btnRegisterEvent.setEnabled(false);
        this.etEventTitle.addTextChangedListener(this);
        this.tvEventDate.addTextChangedListener(this);
        this.tvEventTime.addTextChangedListener(this);
    }

    @Override
    protected void onStop() {
        this.etEventTitle.removeTextChangedListener(this);
        this.tvEventDate.removeTextChangedListener(this);
        this.tvEventTime.removeTextChangedListener(this);
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void setPresenter(EventsContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEventDate:
                DialogFragment datePickerFragment = DatePickerFragment.newInstance(this
                        , DatePickerFragment.FUTURE_OR_PRESENT_DATES);
                datePickerFragment.show(getSupportFragmentManager(), "eventDate");
                break;
            case R.id.ivEventTime:
                DialogFragment timePickerFragment = TimePickerFragment.newInstance(this);
                timePickerFragment.show(getSupportFragmentManager(), "eventTime");
                break;
            case R.id.btnRegisterEvent:
                String title = this.etEventTitle.getText().toString();
                String date = this.tvEventDate.getText().toString();
                String time = this.tvEventTime.getText().toString();

                this.mPresenter.addEvent(title, this.petId, date, time);
                break;
            default:
        }
    }

    @Override
    public void onDateSet(Date date) {
        if (date == null) {
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        this.tvEventDate.setText(this.mPresenter.formatDate(date));
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        this.tvEventTime.setText(this.mPresenter.formatTime(hourOfDay, minute));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Do nothing
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Do nothing
    }

    @Override
    public void afterTextChanged(Editable s) {
        String title = this.etEventTitle.getText().toString();
        String eventDate = this.tvEventDate.getText().toString();
        String eventTime = this.tvEventTime.getText().toString();

        btnRegisterEvent.setEnabled(this.mPresenter.isEventDataFilled(title, eventDate, eventTime));
    }
}
