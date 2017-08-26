package br.com.moryta.myfirstapp.events.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.model.Event;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity
        implements EventDetailContract.View
        , OnMapReadyCallback {

    private static final int DEFAULT_ZOOM = 13;

    private GoogleMap mMap;
    private LatLng mEventPosition;
    private Event mEvent;

    @BindView(R.id.tvEventTitle)
    TextView tvEventTitle;

    @BindView(R.id.tvEventDescription)
    TextView tvEventDescription;

    @BindView(R.id.tvEventDate)
    TextView tvEventDate;

    @BindView(R.id.tvEventTime)
    TextView tvEventTime;

    @BindView(R.id.tvEventAddress)
    TextView tvEventAddress;

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

        Long eventId = getIntent().getLongExtra(Event.class.getName(), 0);
        this.mEvent = this.mPresenter.getEvent(eventId);
        this.mEventPosition = new LatLng(this.mEvent.getAddress().getLatitude()
                , this.mEvent.getAddress().getLongitude());

        this.tvEventTitle.setText(this.mEvent.getTitle());
        this.tvEventDescription.setText(this.mEvent.getDescription());
        this.tvEventDate.setText(this.mEvent.getDate());
        this.tvEventTime.setText(this.mEvent.getTime());
        this.tvEventAddress.setText(this.mPresenter.buildAddressInfo(this.mEvent.getAddress()));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
    public void setPresenter(EventDetailContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }
}
