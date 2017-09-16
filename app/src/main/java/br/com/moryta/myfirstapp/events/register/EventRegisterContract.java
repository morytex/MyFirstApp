package br.com.moryta.myfirstapp.events.register;

import java.util.Date;
import java.util.List;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.events.EventsContract;
import br.com.moryta.myfirstapp.model.Pet;

/**
 * Created by moryta on 24/08/2017.
 */

public interface EventRegisterContract {
    Long NO_EVENT_ID = 0L;

    interface View extends BaseView<EventsContract.Presenter> {
        void onEventLoaded(Long petId, String title, String description, String date, String time);
    }

    interface Presenter extends BasePresenter {
        List<Pet> fetchAllPets();

        void loadEvent(Long eventId);

        Long insertOrUpdateEvent(Long petId, String title, String description
                , String date, String time, String contact, String state, String city
                , String street, String addressNumber, double latitude, double longitude);

        boolean isEventDataFilled(String title, String date, String time);

        String formatDate(Date date);

        String formatTime(int hourOfDay, int minute);
    }
}
