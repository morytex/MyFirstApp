package br.com.moryta.myfirstapp.events.register;

import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.Date;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Event;
import br.com.moryta.myfirstapp.utils.DateUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 24/08/2017.
 */

public class EventRegisterPresenter implements EventRegisterContract.Presenter {
    private EventRegisterContract.View view;
    private DaoSession daoSession;

    public EventRegisterPresenter(@NonNull EventRegisterContract.View view,@NonNull  DaoSession daoSession) {
        this.view = checkNotNull(view, "view cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    public void start() {
        // Do nothing
    }

    @Override
    public void addEvent(String title, Long petId, String date, String time) {
        Event event = new Event(null, petId, title, date, time);
        this.daoSession.getEventDao().insert(event);
    }

    @Override
    public boolean isEventDataFilled(String title, String date, String time) {
        // TODO: Apply validation on time
        return !Strings.isNullOrEmpty(title)
                && !Strings.isNullOrEmpty(date)
                && DateUtil.isValidFormat(date)
                && !Strings.isNullOrEmpty(time);
    }

    @Override
    public String formatDate(Date date) {
        return DateUtil.format(date);
    }

    @Override
    public String formatTime(int hourOfDay, int minute) {
        return String.format("%s:%s"
                , Strings.padStart(String.valueOf(hourOfDay), 2, '0')
                , Strings.padStart(String.valueOf(minute), 2, '0'));
    }
}
