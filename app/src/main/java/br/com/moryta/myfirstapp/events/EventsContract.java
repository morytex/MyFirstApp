package br.com.moryta.myfirstapp.events;

import java.util.List;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.model.Event;

/**
 * Created by moryta on 23/08/2017.
 */

public interface EventsContract {
    int RC_DETAIL_EVENT = 1000;
    int RC_REGISTER_EVENT = 1001;
    int RC_UPDATE_EVENT = 1002;
    int RC_ACTION_CALL = 5000;
    int RC_ACTION_SHARE = 5001;

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        List<Event> fetchAllEvents();
    }
}
