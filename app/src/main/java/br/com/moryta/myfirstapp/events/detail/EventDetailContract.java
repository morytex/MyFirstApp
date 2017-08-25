package br.com.moryta.myfirstapp.events.detail;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;
import br.com.moryta.myfirstapp.model.Address;

/**
 * Created by moryta on 25/08/2017.
 */

public class EventDetailContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        Address getAddress(Long id);
    }
}
