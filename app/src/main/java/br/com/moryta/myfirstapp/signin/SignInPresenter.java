package br.com.moryta.myfirstapp.signin;

import android.support.annotation.NonNull;

import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Login;
import br.com.moryta.myfirstapp.model.LoginDao;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 16/07/2017.
 */

public class SignInPresenter implements SignInContract.Presenter {

    private SignInContract.View mLoginView;
    private DaoSession mDaoSession;

    public SignInPresenter(@NonNull SignInContract.View loginView
            , @NonNull DaoSession daoSession) {

        this.mLoginView = checkNotNull(loginView, "loginView cannot be null!");
        this.mDaoSession = checkNotNull(daoSession, "mDaoSession cannot be null!");
    }

    @Override
    public void start() {
        // Not necessary, we don't start anything on start/resume lifecycle
    }

    @Override
    public void signIn(String email, String password, boolean stayConnected) {
        LoginDao loginDao = this.mDaoSession.getLoginDao();
        Login savedLogin = loginDao.queryBuilder()
                .where(LoginDao.Properties.Email.eq(email)
                       , LoginDao.Properties.Password.eq(password))
                .build()
                .unique();

        if (savedLogin == null) {
            // TODO: try firebase sign in with email and password
            mLoginView.showErrorMessage();
        } else {
            mLoginView.storeLoginPreference(email, password, stayConnected);
            mLoginView.startHomeActivity();
        }
    }
}
