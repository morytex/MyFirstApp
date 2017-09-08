package br.com.moryta.myfirstapp.events;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 23/08/2017.
 */

public class EventsPresenter implements EventsContract.Presenter {
    private EventsContract.View mEventsView;
    private DaoSession daoSession;

    public EventsPresenter(@NonNull EventsContract.View view , @NonNull DaoSession daoSession) {
        this.mEventsView = checkNotNull(view, "view cannot be null!");
        this.mEventsView.setPresenter(this);
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    public void start() {
        // Do nothing
    }

    @Override
    public List<Event> fetchAllEvents() {
        return this.daoSession.getEventDao().loadAll();
    }
}
