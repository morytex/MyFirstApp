package br.com.moryta.myfirstapp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.api.APIUtils;
import br.com.moryta.myfirstapp.home.HomeActivity;
import br.com.moryta.myfirstapp.signin.SignInActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    private SplashContract.Presenter mSplashPresenter;

    @BindView(R.id.splash_logo)
    ImageView ivSplashLogo;

    private void startAnimation() {
        Animation animation = AnimationUtils.loadAnimation(
                SplashActivity.this, R.anim.splash_animation);
        animation.reset();

        if (ivSplashLogo != null) {
            ivSplashLogo.clearAnimation();
            ivSplashLogo.startAnimation(animation);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        mSplashPresenter = new SplashPresenter(SplashActivity.this
                , APIUtils.getMockyAPI()
                , ((MyApplication) getApplication()).getDaoSession());

        startAnimation();

        mSplashPresenter.fetchDefaultLogin();
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public void onFetchDefaultProposalCompleted() {
        // Get user information if saved on sharedPreferences
        SharedPreferences sp = getSharedPreferences(
                getString(R.string.login_preference_file_key), MODE_PRIVATE);

        boolean stayConnected = sp.getBoolean(
                getString(R.string.login_preference_stay_connected_key)
                , false);

        mSplashPresenter.resolveRedirect(stayConnected);
    }

    @Override
    public void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void startSignInActivity() {
        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}
