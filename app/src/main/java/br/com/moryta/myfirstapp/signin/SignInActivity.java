package br.com.moryta.myfirstapp.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.Strings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.home.HomeActivity;
import br.com.moryta.myfirstapp.signup.SignUpActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.SharedPreferences.Editor;
import static br.com.moryta.myfirstapp.signin.SignInContract.RC_EMAIL_PASSWORD_SIGN_UP;
import static br.com.moryta.myfirstapp.signin.SignInContract.RC_GOOGLE_SIGN_IN;

public class SignInActivity extends AppCompatActivity
        implements SignInContract.View
        , View.OnClickListener
        , FirebaseAuth.AuthStateListener
        , GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "SignInActivity";

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    private SignInContract.Presenter mLoginPresenter;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.cbStayConnected)
    CheckBox cbStayConnected;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.btnSignInGoogle)
    Button btnSignInGoogle;

    @BindView(R.id.tvSignUp)
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Bind views
        ButterKnife.bind(SignInActivity.this);
        btnSignIn.setOnClickListener(this);
        btnSignInGoogle.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

        // Authentication
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        this.mAuth = FirebaseAuth.getInstance();

        // Set presenter
        mLoginPresenter = new SignInPresenter(SignInActivity.this
                , ((MyApplication) getApplication()).getDaoSession());
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mAuth.removeAuthStateListener(this);
        this.mGoogleApiClient.unregisterConnectionCallbacks(this);
    }

    private void storeLoginPreference(String email, String password, boolean stayConnected) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.login_preference_file_key)
                , MODE_PRIVATE);
        Editor editor = sp.edit();

        editor.putString(getString(R.string.login_preference_email_key)
                , email);

        editor.putString(getString(R.string.login_preference_password_key)
                , password);

        editor.putBoolean(getString(R.string.login_preference_stay_connected_key)
                , stayConnected);

        editor.commit();
    }

    private void clearLoginPreference() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.login_preference_file_key)
                , MODE_PRIVATE);

        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithEmailAndPassword(String email, String password, boolean stayConnected) {
        // Verify if user is the one returned by mocky
        // If it is, save preferences and start home activity
        if (mLoginPresenter.isDefaultUser(email, password)) {
            this.storeLoginPreference(email, password, stayConnected);
            this.startHomeActivity();
            return;
        }

        this.firebaseAuthWithEmailAndPassword(email, password);
    }

    private void firebaseAuthWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.error_message_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithGoogleCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, R.string.error_message_auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    // Google sign out
    private void googleSignOut() {
        Auth.GoogleSignInApi.signOut(this.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        // Do nothing
                    }
                }
        );
    }

    private void signUpWithEmailAndPassword() {
        Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivityForResult(signUpIntent, RC_EMAIL_PASSWORD_SIGN_UP);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                this.firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else if (requestCode == RC_EMAIL_PASSWORD_SIGN_UP) {
            // TODO: Verify if result code needs treatment
        }
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {
        // Not necessary, because we instantiate presenter in this activity
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(SignInActivity.this
                , getString(R.string.error_message_invalid_sign_in)
                , Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // TODO: Do something
    }

    @Override
    public void onClick(View v) {
        String email = this.etEmail.getText().toString();
        String password = this.etPassword.getText().toString();
        boolean stayConnected = this.cbStayConnected.isChecked();

        switch (v.getId()) {
            case R.id.btnSignIn:
                if (Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(password)) {
                    Toast.makeText(this, R.string.error_message_email_password_required, Toast.LENGTH_SHORT).show();
                    return;
                }
                this.signInWithEmailAndPassword(email, password, stayConnected);
                break;
            case R.id.btnSignInGoogle:
                this.signInWithGoogle();
                break;
            case R.id.tvSignUp:
                this.signUpWithEmailAndPassword();
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            this.storeLoginPreference(
                    user.getEmail(),
                    null,
                    false
            );
            this.startHomeActivity();
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
            this.clearLoginPreference();
            if (this.mGoogleApiClient.isConnected()) {
                this.googleSignOut();
            } else {
                this.mGoogleApiClient.registerConnectionCallbacks(this);
            }
        }
        // ...
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        this.googleSignOut();
        this.mGoogleApiClient.unregisterConnectionCallbacks(this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        this.mGoogleApiClient.unregisterConnectionCallbacks(this);
    }
}
