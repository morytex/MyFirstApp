package br.com.moryta.myfirstapp.events.register;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.Date;
import java.util.List;

import br.com.moryta.myfirstapp.model.Address;
import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;
import br.com.moryta.myfirstapp.model.Pet;
import br.com.moryta.myfirstapp.utils.DateUtil;
import br.com.moryta.myfirstapp.utils.TimeUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 24/08/2017.
 */

public class EventRegisterPresenter implements EventRegisterContract.Presenter {
    private static final String TAG = "EventRegisterPresenter";

    private EventRegisterContract.View view;
    private DaoSession daoSession;

    public EventRegisterPresenter(@NonNull EventRegisterContract.View view, @NonNull DaoSession daoSession) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    public void start() {
        // Do nothing
    }

    @Override
    public List<Pet> fetchAllPets() {
        return this.daoSession.getPetDao().loadAll();
    }

    @Override
    public Event getOrCreateEvent(Long eventId) {
        Event event = this.daoSession.getEventDao().loadDeep(eventId);
        if (event == null) {
            event = new Event();
            event.setAddress(new Address());
        }

        return event;
    }

    @Override
    public Long insertOrUpdateEvent(Event event) {
        Long eventId = this.daoSession.getEventDao().insertOrReplace(event);
        this.daoSession.clear();
        return eventId;
    }

    @Override
    public boolean isEventDataFilled(String title, String date, String time) {
        return !Strings.isNullOrEmpty(title)
                && !Strings.isNullOrEmpty(date)
                && DateUtil.isValidFormat(date)
                && !Strings.isNullOrEmpty(time)
                && TimeUtil.isValidFormat(time);
    }

    @Override
    public String formatDate(Date date) {
        return DateUtil.format(date);
    }

    @Override
    public String formatTime(int hourOfDay, int minute) {
        return TimeUtil.format(hourOfDay, minute);
    }
}
