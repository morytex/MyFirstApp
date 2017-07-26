package br.com.moryta.myfirstapp.signin;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 16/07/2017.
 */

public interface SignInContract {
    interface View extends BaseView<Presenter> {
        void startHomeActivity();
        void showErrorMessage();
        void storeLoginPreference(String email, String password, boolean stayConnected);
    }

    interface Presenter extends BasePresenter {
        void signIn(String email, String password, boolean stayConnected);
    }
}
