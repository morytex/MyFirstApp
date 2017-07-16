package br.com.moryta.myfirstapp.login;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 16/07/2017.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void startMainActivity(String username);
        void showErrorMessage();
        void storeStayConnectedPreference(boolean stayConnected);
        void storeLoginPreference(String username, String password);
        void clearLoginPreference();
    }

    interface Presenter extends BasePresenter {
        void validateUser(String username, String password, boolean stayConnected);
    }
}
