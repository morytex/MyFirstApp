package br.com.moryta.myfirstapp.events.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import br.com.moryta.myfirstapp.DatePickerFragment;
import br.com.moryta.myfirstapp.Extras;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.TimePickerFragment;
import br.com.moryta.myfirstapp.events.EventsContract;
import br.com.moryta.myfirstapp.model.Pet;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.moryta.myfirstapp.events.register.EventRegisterContract.NO_EVENT_ID;

public class EventRegisterActivity extends AppCompatActivity
        implements EventRegisterContract.View
        , DatePickerFragment.DatePickerFragmentListener
        , TimePickerFragment.TimePickerFragmentListener
        , View.OnClickListener
        , AdapterView.OnItemSelectedListener
        , TextWatcher {

    private static final String TAG = "EventRegisterActivity";

    private Long petId;
    private Boolean hasSelectedPet;

    @BindView(R.id.sPets)
    Spinner sPets;

    @BindView(R.id.etEventTitle)
    EditText etEventTitle;

    @BindView(R.id.etEventDescription)
    EditText etEventDescription;

    @BindView(R.id.tvEventDate)
    TextView tvEventDate;

    @BindView(R.id.ivEventDate)
    ImageView ivEventDate;

    @BindView(R.id.tvEventTime)
    TextView tvEventTime;

    @BindView(R.id.ivEventTime)
    ImageView ivEventTime;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private EventRegisterContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        // Setting click listener
        this.ivEventDate.setOnClickListener(this);
        this.ivEventTime.setOnClickListener(this);

        // Instance of presenter
        this.mPresenter = new EventRegisterPresenter(EventRegisterActivity.this
                , ((MyApplication) getApplication()).getDaoSession());

        Long eventId = getIntent().getLongExtra(Extras.EVENT_ID, NO_EVENT_ID);
        this.mPresenter.loadEvent(eventId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Adding watcher on fields to disable/enable register button
        this.etEventTitle.addTextChangedListener(this);
        this.etEventDescription.addTextChangedListener(this);
        this.tvEventDate.addTextChangedListener(this);
        this.tvEventTime.addTextChangedListener(this);
        this.fab.setOnClickListener(this);
        this.sPets.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        this.etEventTitle.removeTextChangedListener(this);
        this.etEventDescription.removeTextChangedListener(this);
        this.tvEventDate.removeTextChangedListener(this);
        this.tvEventTime.removeTextChangedListener(this);
        this.fab.setOnClickListener(null);
        this.sPets.setOnItemSelectedListener(null);
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
            case R.id.fab:
                Long savedEventId = this.mPresenter.insertOrUpdateEvent(this.petId
                        , this.etEventTitle.getText().toString()
                        , this.etEventDescription.getText().toString()
                        , this.tvEventDate.getText().toString()
                        , this.tvEventTime.getText().toString()
                        , "SP", "São Paulo", "Av. Paulista"
                        , "1100", -23.564149, -46.652484
                );

                // TODO: Trigger this on callback after async task in presenter finishes insert/update
                Intent intent = new Intent();
                intent.putExtra(Extras.EVENT_ID, savedEventId);
                setResult(RESULT_OK, intent);
                finish();

                break;
            default:
        }
    }

    @Override
    public void onEventLoaded(Long petId, String title, String description, String date, String time) {
        this.petId = petId;

        this.etEventTitle.setText(title);
        this.etEventDescription.setText(description);
        this.tvEventDate.setText(date);
        this.tvEventTime.setText(time);

        fab.setEnabled(this.mPresenter.isEventDataFilled(title, date, time));

        // TODO: Remove spinner after refactoring to exclude insert of more than one pet
        ArrayAdapter<Pet> spinnerArrayAdapter = new ArrayAdapter<>(EventRegisterActivity.this
                , R.layout.support_simple_spinner_dropdown_item
                , new ArrayList<>(this.mPresenter.fetchAllPets()));
        this.sPets.setAdapter(spinnerArrayAdapter);
        this.sPets.setSelection(0);
    }

    @Override
    public void onDateSet(Date date) {
        if (date == null) {
            Toast.makeText(this
                    , getString(R.string.error_message_date_required)
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        this.tvEventDate.setText(this.mPresenter.formatDate(date));
    }

    @Override
    public void onTimeSet(int hourOfDay, int minute) {
        this.tvEventTime.setText(this.mPresenter.formatTime(hourOfDay, minute));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Pet pet = (Pet) this.sPets.getSelectedItem();
        this.petId = pet.getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

        fab.setEnabled(this.mPresenter.isEventDataFilled(title, eventDate, eventTime));
    }
}
