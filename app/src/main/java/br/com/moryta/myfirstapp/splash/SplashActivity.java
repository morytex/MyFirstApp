package br.com.moryta.myfirstapp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.moryta.myfirstapp.login.LoginActivity;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.api.APIUtils;
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

        String username = sp.getString(
                getString(R.string.login_preference_username_key)
                , null);

        mSplashPresenter.resolveRedirect(stayConnected, username);
    }

    @Override
    public void startMainActivity(String username) {
        // TODO: apply redirect to main activity
        Toast.makeText(SplashActivity.this, "Redirecting to Main Activity", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
