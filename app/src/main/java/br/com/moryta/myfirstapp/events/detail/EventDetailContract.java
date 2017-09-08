package br.com.moryta.myfirstapp.events.detail;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 25/08/2017.
 */

public class EventDetailContract {


    interface View extends BaseView<Presenter> {
        void onEventLoaded(String title, String description
                , String date, String time, String contact
                , String street, String addressNumber, String state
                , String city, double latitude, double longitude);

        boolean hasPermissions();
    }

    interface Presenter extends BasePresenter {
        void loadEvent(Long id);
        String buildEventStreetInfo(String street, String addressNumber);
        String buildEventCityInfo(String city, String state);
    }
}
