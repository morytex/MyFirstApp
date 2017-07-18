package br.com.moryta.myfirstapp.splash;

import br.com.moryta.myfirstapp.BasePresenter;
import br.com.moryta.myfirstapp.BaseView;

/**
 * Created by moryta on 07/07/2017.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter> {
        int SPLASH_DISPLAY_LENGTH = 3500;

        void onFetchDefaultProposalCompleted();
        void startHomeActivity();
        void startLoginActivity();
    }

    interface Presenter extends BasePresenter {
        void fetchDefaultLogin();
        void resolveRedirect(final boolean stayConnected);
    }
}
