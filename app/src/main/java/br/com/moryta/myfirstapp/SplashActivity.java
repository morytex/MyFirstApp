package br.com.moryta.myfirstapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    private final static int SPLASH_DISPLAY_LENGTH = 3500;

    @BindView(R.id.splash_logo)
    ImageView ivSplashLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        startAnimation();
        // initApp();
        redirect();
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

    private void redirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences(
                        getString(R.string.login_preference_file_key), MODE_PRIVATE);

                boolean stayConnected = sp.getBoolean(
                        getString(R.string.login_preference_stay_connected_key)
                        , false);

                String username = sp.getString(
                        getString(R.string.login_preference_username_key)
                        , "User");

                if (stayConnected) {
                    redirectToMainActivity(username);
                } else {
                    redirectToLoginActivity();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }

    private void redirectToMainActivity(String username) {
        Toast.makeText(SplashActivity.this, "Redirecting to Main Activity", Toast.LENGTH_SHORT)
                .show();
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
