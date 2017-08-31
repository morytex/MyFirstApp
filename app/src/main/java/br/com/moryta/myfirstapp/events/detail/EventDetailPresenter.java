package br.com.moryta.myfirstapp.events.detail;

import android.support.annotation.NonNull;

import br.com.moryta.myfirstapp.model.Address;
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
    public Event getEvent(Long id) {
        return daoSession.getEventDao().loadDeep(id);
    }

    @Override
    public String buildEventStreetInfo(Address address) {
        return String.format("%s, %s"
                , address.getStreet()
                , address.getNumber());
    }

    @Override
    public String buildEventCityInfo(Address address) {
        return String.format("%s - %s"
                , address.getCity()
                , address.getState());
    }
}
