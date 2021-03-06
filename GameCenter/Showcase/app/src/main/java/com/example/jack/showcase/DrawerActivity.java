package com.example.jack.showcase;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.myFlappybird.flappybird.fbActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.jack.showcase.R.id.content_frame;

/**
 * Created by jack on 2014/10/4.
 */
public class DrawerActivity extends ActionBarActivity implements OnFragmentInteractionListener{
    private SharedPreferences prefs;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavigationTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        if (prefs.getBoolean("firstLaunch", true)) {
            prefs.edit().putBoolean("firstLaunch", false).commit();
            startActivity(new Intent(getApplicationContext(), HelpActivity.class));
        }

        //dummy content
        populate();

        mTitle = mDrawerTitle = getTitle();
        mNavigationTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavigationTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  // host Activity
                mDrawerLayout,         // DrawerLayout object
                R.drawable.ic_drawer,  // nav drawer image to replace 'Up' caret
                R.string.drawer_open,  // "open drawer" description for accessibility
                R.string.drawer_close  // "close drawer" description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    // Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return !drawerOpen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            AddItemDialog dialog = new AddItemDialog();
            dialog.show(getSupportFragmentManager(), "AddItemDialog");
            return true;
        }

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // The click listner for ListView in the navigation drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch(position) {
            case 0:
                fragment = new ItemListFragment();
                break;

            case 1:
                fragment = new ItemGridFragment();
                break;

            case 2:
                fragment = new ItemPagerFragment();
                break;

            default:
                fragment = new ContentFragment();
                Bundle args = new Bundle();
                args.putInt(ContentFragment.ARG_POSITION, position);
                fragment.setArguments(args);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavigationTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void onItemSelected(long id) {
        if(id == 7){
            Intent detailIntent = new Intent(this, fbActivity.class);
            startActivity(detailIntent);
        }

//        Intent detailIntent = new Intent(this, DetailActivity.class);
//        detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
//        startActivity(detailIntent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void populate() {
        JsonArrayRequest req = new JsonArrayRequest("http://192.168.1.15/", new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                ContentResolver cr = getContentResolver();
                try {
                    int size = response.length();
                    for (int i=0; i<size; i++) {
                        JSONObject json = response.getJSONObject(i);
                        ContentValues cv = new ContentValues(1);
                        cv.put(DataProvider.COL_CONTENT, json.toString());
                        cr.insert(DataProvider.CONTENT_URI_DATA, cv);
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // add the request object to the queue to be executed
        App.getInstance().getRequestQueue().add(req);
    }

}