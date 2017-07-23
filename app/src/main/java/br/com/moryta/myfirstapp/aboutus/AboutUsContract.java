package br.com.moryta.myfirstapp.aboutus;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 22/07/2017.
 */

public interface AboutUsContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        String buildVersionText(String template);
    }
}
