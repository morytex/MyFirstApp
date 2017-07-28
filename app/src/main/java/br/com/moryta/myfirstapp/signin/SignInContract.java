package br.com.moryta.myfirstapp.signin;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 16/07/2017.
 */

public interface SignInContract {
    interface View extends BaseView<Presenter> {
        void showErrorMessage();
    }

    interface Presenter extends BasePresenter {
        boolean isDefaultUser(String username, String password);
    }
}
