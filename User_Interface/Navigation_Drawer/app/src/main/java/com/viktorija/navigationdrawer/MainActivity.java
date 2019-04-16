package com.viktorija.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        ;
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_camera:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Import selected");
                return true;
            case R.id.nav_gallery:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Gallery selected");
                return true;
            case R.id.nav_slideshow:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Slideshow selected");
                return true;
            case R.id.nav_tools:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Tools selected");
                return true;
            case R.id.nav_share:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Share selected");
                return true;
            case R.id.nav_send:
                mDrawer.closeDrawer(GravityCompat.START);
                displayToast("Send selected");
                return true;
            default:
                return false;
        }
    }

    /**
     * Displays a toast message.
     * @param message   Message to display in toast
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
