package th.in.droid.liveat500px.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import th.in.droid.liveat500px.R;
import th.in.droid.liveat500px.dao.PhotoItemDao;
import th.in.droid.liveat500px.fragment.MainFragment;
import th.in.droid.liveat500px.fragment.MoreInfoFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_container, MainFragment.newInstance())
                    .commit();
        }
    }

    private void initInstances() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_menu,
                R.string.close_menu
        );
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPhotoItemClicked(PhotoItemDao dao) {
        FrameLayout moreInfoContainer = findViewById(R.id.more_info_container);

        if (moreInfoContainer == null) {
            Intent intent = new Intent(MainActivity.this, MoreInfoActivity.class);
            intent.putExtra("dao", dao);
            startActivity(intent);
        } else {
            // Tablet
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.more_info_container, MoreInfoFragment.newInstance(dao))
                    .commit();
        }
    }
}
