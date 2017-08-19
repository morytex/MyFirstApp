package br.com.moryta.myfirstapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import br.com.moryta.myfirstapp.MyApplication;
import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.aboutus.AboutUsFragment;
import br.com.moryta.myfirstapp.aboutus.AboutUsPresenter;
import br.com.moryta.myfirstapp.pets.PetsFragment;
import br.com.moryta.myfirstapp.pets.PetsPresenter;
import br.com.moryta.myfirstapp.signin.SignInActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , HomeFragment.OnFragmentInteractionListener
        , PetsFragment.OnFragmentInteractionListener
        , AboutUsFragment.OnFragmentInteractionListener {

    private static final String TAG = "HomeActivity";

    private HomeFragment mHomeFragment;
    private PetsFragment mPetsFragment;
    private PetsPresenter mPetsPresenter;
    private AboutUsFragment mAboutUsFragment;
    private AboutUsPresenter mAboutUsPresenter;

    private Boolean isDefaultUser;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView tvEmail;
    TextView tvPetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        View headerView = navigationView.getHeaderView(0);
        this.tvEmail = ButterKnife.findById(headerView, R.id.tvEmail);
        this.tvPetName = ButterKnife.findById(headerView, R.id.tvPetName);

        // HomeFragment
        this.mHomeFragment = HomeFragment.newInstance();

        // PetsFragment
        this.mPetsFragment = PetsFragment.newInstance();
        this.mPetsPresenter = new PetsPresenter(this.mPetsFragment
                , ((MyApplication) getApplication()).getDaoSession());
        this.mPetsPresenter.start();

        // AboutUsFragment
        this.mAboutUsFragment = AboutUsFragment.newInstance();
        this.mAboutUsPresenter = new AboutUsPresenter(this.mAboutUsFragment);
        this.mAboutUsPresenter.start();

        configureNavigationDrawer();
        setNavigationDisplay();

        // Always start with home fragment on create activity
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_home, this.mHomeFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                this.replaceContentWith(this.mHomeFragment);
                break;
            case R.id.nav_pets:
                this.replaceContentWith(this.mPetsFragment);
                break;
            case R.id.nav_about_us:
                this.replaceContentWith(this.mAboutUsFragment);
                break;
            case R.id.nav_sign_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onHomeFragmentAttach() {
        setTitle(R.string.nav_home);
    }

    @Override
    public void onPetsFragmentAttach() {
        setTitle(R.string.nav_pets);
    }

    @Override
    public void onAboutUsFragmentAttach() {
        setTitle(R.string.nav_about_us);
    }

    private void configureNavigationDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setNavigationDisplay() {
        String email = getSharedPreferences(getString(R.string.login_preference_file_key), MODE_PRIVATE)
                .getString(getString(R.string.login_preference_email_key), null);

        this.tvEmail.setText(email);
        this.tvPetName.setText(R.string.my_pet);
    }

    private void replaceContentWith(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.content_home, fragment)
                .addToBackStack(TAG)
                .commit();
    }

}
