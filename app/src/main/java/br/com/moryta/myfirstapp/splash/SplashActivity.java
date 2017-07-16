package br.com.moryta.myfirstapp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.moryta.myfirstapp.LoginActivity;
import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.api.APIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    @BindView(R.id.splash_logo)
    ImageView ivSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        SplashPresenter presenter = new SplashPresenter(SplashActivity.this
                , APIUtils.getMockyAPI()
                , ((MyApplication) getApplication()).getDaoSession());

        startAnimation();

        presenter.fetchDefaultLogin();

        // Get user information if saved on sharedPreferences
        SharedPreferences sp = getSharedPreferences(
                getString(R.string.login_preference_file_key), MODE_PRIVATE);

        boolean stayConnected = sp.getBoolean(
                getString(R.string.login_preference_stay_connected_key)
                , false);

        String username = sp.getString(
                getString(R.string.login_preference_username_key)
                , "");

        String password = sp.getString(
                getString(R.string.login_preference_password_key)
                , "");

        presenter.resolveRedirect(stayConnected, username, password);
    }

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
    public void startMainActivity(String username) {
        Toast.makeText(SplashActivity.this, "Redirecting to Main Activity", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
