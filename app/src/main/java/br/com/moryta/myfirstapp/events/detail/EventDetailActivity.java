package br.com.moryta.myfirstapp.events.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.moryta.myfirstapp.Extras;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.events.register.EventRegisterActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.moryta.myfirstapp.events.EventsContract.RC_UPDATE_EVENT;
import static br.com.moryta.myfirstapp.events.register.EventRegisterContract.NO_EVENT_ID;

public class EventDetailActivity extends AppCompatActivity
        implements EventDetailContract.View
        , OnMapReadyCallback {

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

    private EventDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

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
            , String date, String time, String street
            , String addressNumber, String city, String state
            , double latitude, double longitude) {
        this.tvEventTitle.setText(title);
        this.tvEventDescription.setText(description);
        this.tvEventDate.setText(date);
        this.tvEventTime.setText(time);
        this.tvEventStreet.setText(this.mPresenter.buildEventStreetInfo(street, addressNumber));
        this.tvEventCity.setText(this.mPresenter.buildEventCityInfo(city, state));

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
    public void setPresenter(EventDetailContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }
}
