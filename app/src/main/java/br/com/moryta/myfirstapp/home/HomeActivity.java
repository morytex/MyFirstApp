package br.com.moryta.myfirstapp.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.moryta.myfirstapp.R;
import br.com.moryta.myfirstapp.aboutus.AboutUsContract;
import br.com.moryta.myfirstapp.aboutus.AboutUsFragment;
import br.com.moryta.myfirstapp.aboutus.AboutUsPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AboutUsContract.Presenter mAboutUsPresenter;
    private AboutUsFragment mAboutUsFragment;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView tvUsername;
    TextView tvPetName;

    private void configureAboutUs() {
        this.mAboutUsFragment = new AboutUsFragment();
        mAboutUsPresenter = new AboutUsPresenter(getApplicationContext(), this.mAboutUsFragment);
        this.mAboutUsFragment.setPresenter(mAboutUsPresenter);
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
        String username = getSharedPreferences(getString(R.string.login_preference_file_key), MODE_PRIVATE)
                                .getString(getString(R.string.login_preference_username_key), null);

        this.tvUsername.setText(username);
        this.tvPetName.setText("My pet");
    }

    private void replaceContentWith(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_home, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        View headerView = navigationView.getHeaderView(0);
        this.tvUsername = ButterKnife.findById(headerView, R.id.tvUsername);
        this.tvPetName = ButterKnife.findById(headerView, R.id.tvPetName);

        configureAboutUs();
        configureNavigationDrawer();
        setNavigationDisplay();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
        } else if (id == R.id.nav_pets) {
            // Handle the add pet action
        } else if (id == R.id.nav_about_us) {
            this.replaceContentWith(this.mAboutUsFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
