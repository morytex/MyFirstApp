package br.com.moryta.myfirstapp.events.register;

import java.util.Date;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.events.EventsContract;

/**
 * Created by moryta on 24/08/2017.
 */

public interface EventRegisterContract {
    interface View extends BaseView<EventsContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addEvent(String title, Long petId, String date, String time);

        boolean isEventDataFilled(String title, String date, String time);

        String formatDate(Date date);

        String formatTime(int hourOfDay, int minute);
    }
}
