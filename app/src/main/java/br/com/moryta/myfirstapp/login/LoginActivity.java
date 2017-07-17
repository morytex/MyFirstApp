package br.com.moryta.myfirstapp.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.SharedPreferences.*;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private LoginContract.Presenter mLoginPresenter;

    @BindView(R.id.etUsername)
    EditText etUsername;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.cbStayConnected)
    CheckBox cbStayConnected;

    private SharedPreferences getLoginPreference() {
        return getSharedPreferences(getString(R.string.login_preference_file_key)
                                    , MODE_PRIVATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(LoginActivity.this);

        mLoginPresenter = new LoginPresenter(LoginActivity.this
                , ((MyApplication) getApplication()).getDaoSession());
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public void startMainActivity(String username) {
        Toast.makeText(LoginActivity.this, "Redirecting to Main Activity", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(LoginActivity.this
                , getString(R.string.invalid_login_message)
                , Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void storeStayConnectedPreference(boolean stayConnected) {
        SharedPreferences sp = getLoginPreference();
        Editor editor = sp.edit();

        editor.putBoolean(getString(R.string.login_preference_stay_connected_key)
                        , stayConnected);

        editor.commit();
    }

    @Override
    public void storeLoginPreference(String username, String password) {
        SharedPreferences sp = getLoginPreference();
        Editor editor = sp.edit();

        editor.putString(getString(R.string.login_preference_username_key)
                        , username);

        editor.putString(getString(R.string.login_preference_password_key)
                        , password);

        editor.commit();
    }

    @Override
    public void clearLoginPreference() {
        SharedPreferences sp = getLoginPreference();
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    @OnClick(R.id.cbStayConnected)
    public void toggleStayConnected(View view) {
        // TODO: what to do here?
    }

    @OnClick(R.id.btSignIn)
    public void signIn() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean stayConnected = cbStayConnected.isChecked();

        mLoginPresenter.validateUser(username, password, stayConnected);
    }
}
