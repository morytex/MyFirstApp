package br.com.moryta.myfirstapp.events.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.base.Strings;

import br.com.moryta.myfirstapp.Extras;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.events.register.EventRegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.moryta.myfirstapp.events.EventsContract.RC_ACTION_CALL;
import static br.com.moryta.myfirstapp.events.EventsContract.RC_UPDATE_EVENT;
import static br.com.moryta.myfirstapp.events.register.EventRegisterContract.NO_EVENT_ID;
import static com.google.common.base.Preconditions.checkNotNull;

public class EventDetailActivity extends AppCompatActivity
        implements EventDetailContract.View
        , OnMapReadyCallback
        , View.OnClickListener {

    private static final String TAG = "EventDetailActivity";
    private static final int DEFAULT_ZOOM = 13;

    private GoogleMap mMap;
    private LatLng mEventPosition;
    private Long eventId;

    @BindView(R.id.tvEventTitle)
    TextView tvEventTitle;

    @BindView(R.id.tvEventDescription)
    TextView tvEventDescription;

    @BindView(R.id.tvEventDate)
    TextView tvEventDate;

    @BindView(R.id.tvEventTime)
    TextView tvEventTime;

    @BindView(R.id.tvEventStreet)
    TextView tvEventStreet;

    @BindView(R.id.tvEventCity)
    TextView tvEventCity;

    @BindView(R.id.ivEventContact)
    ImageView ivEventContact;

    @BindView(R.id.tvEventContact)
    TextView tvEventContact;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private EventDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        this.fab.setOnClickListener(this);

        // Instance of presenter
        this.mPresenter = new EventDetailPresenter(EventDetailActivity.this
                , ((MyApplication) getApplication()).getDaoSession());

        this.eventId = getIntent().getLongExtra(Extras.EVENT_ID, NO_EVENT_ID);
        this.mPresenter.loadEvent(eventId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                // Start register event activity passing event id
                // TODO: After greendao is removed, pass object through Intent
                Intent intent = new Intent(EventDetailActivity.this, EventRegisterActivity.class);
                intent.putExtra(Extras.EVENT_ID, this.eventId);
                startActivityForResult(intent, RC_UPDATE_EVENT);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onEventLoaded(String title, String description
            , String date, String time, String contact
            , String street, String addressNumber, String city
            , String state, double latitude, double longitude) {
        this.tvEventTitle.setText(title);
        this.tvEventDescription.setText(description);
        this.tvEventDate.setText(date);
        this.tvEventTime.setText(time);
        this.tvEventStreet.setText(this.mPresenter.buildEventStreetInfo(street, addressNumber));
        this.tvEventCity.setText(this.mPresenter.buildEventCityInfo(city, state));
        this.tvEventContact.setText(contact);

        if (Strings.isNullOrEmpty(contact)) {
            this.ivEventContact.setVisibility(View.INVISIBLE);
            this.tvEventContact.setVisibility(View.INVISIBLE);
            this.fab.setEnabled(false);
            this.fab.setVisibility(View.INVISIBLE);
        }

        // Setting map
        this.mEventPosition = new LatLng(latitude, longitude);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(this.mEventPosition);
        this.mMap.addMarker(markerOptions);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.mEventPosition, DEFAULT_ZOOM));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_UPDATE_EVENT:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(
                            EventDetailActivity.this
                            , getString(R.string.warning_message_operation_canceled)
                            , Toast.LENGTH_SHORT).show();

                    return;
                }
                Long eventId = data.getLongExtra(Extras.EVENT_ID, 0);
                this.mPresenter.loadEvent(eventId);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (this.hasPermissions()) {
                    makeCall(this.tvEventContact.getText().toString());
                }
                break;
            default:
        }
    }

    @Override
    public void setPresenter(EventDetailContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public boolean hasPermissions() {
        if (ContextCompat.checkSelfPermission(this
                , Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this
                    , new String[]{Manifest.permission.CALL_PHONE}
                    , RC_ACTION_CALL);

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_ACTION_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    this.makeCall(this.tvEventContact.getText().toString());
                }
                return;
            }
        }
    }

    /**
     * Make a phone call to contact
     *
     * @param contact
     */
    private void makeCall(@NonNull String contact) {
        checkNotNull(contact, "contact cannot be null!");

        String uri = String.format(getString(R.string.action_call_template), contact.trim());

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));

        try {
            startActivity(intent);
        } catch (SecurityException ex) {
            Toast.makeText(this
                    , getString(R.string.warning_message_operation_canceled)
                    , Toast.LENGTH_SHORT);
            Log.e(TAG, getString(R.string.error_message_action_call_canceled), ex);
        }
    }
}
