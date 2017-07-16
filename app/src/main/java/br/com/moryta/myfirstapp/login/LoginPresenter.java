package br.com.moryta.myfirstapp.login;

import android.support.annotation.NonNull;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Login;
import br.com.moryta.myfirstapp.model.LoginDao;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 16/07/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private DaoSession daoSession;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull DaoSession daoSession) {
        this.mLoginView = checkNotNull(loginView, "loginView cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    @Override
    public void start() {
        // Not necessary, we don't start anything on start/resume lifecycle
    }

    @Override
    public void validateUser(String username, String password, boolean stayConnected) {
        LoginDao loginDao = this.daoSession.getLoginDao();
        Login savedLogin = loginDao.queryBuilder()
                .where(LoginDao.Properties.Username.eq(username)
                       , LoginDao.Properties.Password.eq(password))
                .build()
                .unique();

        if (savedLogin == null) {
            mLoginView.showErrorMessage();
        } else {
            mLoginView.storeStayConnectedPreference(stayConnected);
            if (stayConnected) {
                mLoginView.storeLoginPreference(username, password);
            } else {
                mLoginView.clearLoginPreference();
            }

            mLoginView.startMainActivity(username);
        }
    }
}
