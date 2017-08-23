package br.com.moryta.myfirstapp.events;

import java.util.List;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.model.Event;

/**
 * Created by moryta on 23/08/2017.
 */

public interface EventsContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        List<Event> fetchAllEvents();
    }
}
