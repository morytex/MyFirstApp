package br.com.moryta.myfirstapp.splash;

import android.os.Handler;
import android.support.annotation.NonNull;

import br.com.moryta.myfirstapp.api.MockyAPI;
import br.com.moryta.myfirstapp.model.DaoSession;
import br.com.moryta.myfirstapp.model.Login;
import br.com.moryta.myfirstapp.model.LoginDTO;
import br.com.moryta.myfirstapp.model.LoginDao;
import br.com.moryta.myfirstapp.model.mappers.LoginMapper;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by moryta on 07/07/2017.
 */

public class SplashPresenter implements SplashContract.Presenter {

    private final SplashContract.View mSplashView;
    private final MockyAPI mockyAPI;
    private DaoSession daoSession;

    public SplashPresenter(@NonNull SplashContract.View view
            , @NonNull MockyAPI mockyAPI
            , @NonNull DaoSession daoSession) {

        this.mSplashView = checkNotNull(view, "view cannot be null!");
        this.mockyAPI = checkNotNull(mockyAPI, "mockyAPI cannot be null!");
        this.daoSession = checkNotNull(daoSession, "daoSession cannot be null!");
    }

    @Override
    public void start() {
        // Do something
    }

    @Override
    public void fetchDefaultUser() {
        this.mockyAPI.getDefaultLogin()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginDTO>() {
                    @Override
                    public void onCompleted() {
                        mSplashView.onFetchDefaultUserCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mSplashView.onFetchDefaultUserError();
                    }

                    @Override
                    public void onNext(LoginDTO loginDTO) {
                        Login login = LoginMapper.toEntity(loginDTO);

                        if (login == null) {
                            return;
                        }

                        LoginDao loginDao = daoSession.getLoginDao();
                        loginDao.insertOrReplace(login);
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
                    mSplashView.startSignInActivity();
                }
            }
        }, mSplashView.SPLASH_DISPLAY_LENGTH);
    }
}
