package br.com.moryta.myfirstapp.splash;

/**
 * Created by moryta on 07/07/2017.
 */

public interface SplashContract {
    interface View {
        int SPLASH_DISPLAY_LENGTH = 3500;

        void startMainActivity(String username);
        void startLoginActivity();
    }

    interface Presenter {
        void resolveRedirect(final boolean stayConnected, final String username, final String password);
        void fetchDefaultLogin();
    }
}
