package br.com.moryta.myfirstapp.splash;

import android.os.Handler;
import android.support.annotation.NonNull;
import br.com.moryta.myfirstapp.api.MockyAPI;
import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Login;
import br.com.moryta.myfirstapp.model.LoginDTO;
import br.com.moryta.myfirstapp.model.LoginDao;
import br.com.moryta.myfirstapp.model.mapping.LoginMapper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 07/07/2017.
 */

public class SplashPresenter implements SplashContract.Presenter{
    private final SplashContract.View mSplashView;
    private final MockyAPI mockyAPI;
    private DaoSession daoSession;

    public SplashPresenter(@NonNull SplashContract.View splashView
            , @NonNull MockyAPI mockyAPI
            , @NonNull DaoSession daoSession) {

        this.mSplashView = checkNotNull(splashView, "splashView cannot be null!");
        this.mockyAPI = checkNotNull(mockyAPI, "mockyAPI cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    @Override
    public void start() {
        // Not necessary, we don't start anything on start/resume lifecycle
    }

    @Override
    public void fetchDefaultLogin() {
        this.mockyAPI.getDefaultLogin()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginDTO>() {
                    @Override
                    public void onCompleted() {
                        mSplashView.onFetchDefaultProposalCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(LoginDTO loginDTO) {
                        Login login = LoginMapper.toEntity(loginDTO);

                        if (login == null) {
                            return;
                        }

                        LoginDao loginDao = daoSession.getLoginDao();
                        Login savedLogin = loginDao.queryBuilder()
                                .where(LoginDao.Properties.Username.eq(login.getUsername()))
                                .build()
                                .unique();

                        if (savedLogin == null) {
                            loginDao.save(login);
                        } else {
                            login.setId(savedLogin.getId());
                            loginDao.update(login);
                        }
                    }
                });
    }

    @Override
    public void resolveRedirect(final boolean stayConnected) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (stayConnected) {
                    mSplashView.startHomeActivity();
                } else {
                    mSplashView.startLoginActivity();
                }
            }
        }, mSplashView.SPLASH_DISPLAY_LENGTH);
    }
}
