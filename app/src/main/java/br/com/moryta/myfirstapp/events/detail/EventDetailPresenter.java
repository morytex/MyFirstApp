package br.com.moryta.myfirstapp.events.detail;

import android.support.annotation.NonNull;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 25/08/2017.
 */

public class EventDetailPresenter implements EventDetailContract.Presenter {
    private EventDetailContract.View view;
    private DaoSession daoSession;

    public EventDetailPresenter(@NonNull EventDetailContract.View view, @NonNull DaoSession daoSession) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    public void start() {
        // Do nothing
    }

    @Override
    public void loadEvent(Long id) {
        Event event = daoSession.getEventDao().loadDeep(id);
        this.view.onEventLoaded(event.getTitle(), event.getDescription()
                , event.getDate(), event.getTime()
                , event.getAddress().getStreet(), event.getAddress().getNumber()
                , event.getAddress().getCity(), event.getAddress().getState()
                , event.getAddress().getLatitude(), event.getAddress().getLongitude());
    }

    @Override
    public String buildEventStreetInfo(String street, String addressNumber) {
        return String.format("%s, %s"
                , street
                , addressNumber);
    }

    @Override
    public String buildEventCityInfo(String city, String state) {
        return String.format("%s - %s"
                , city
                , state);
    }
}
