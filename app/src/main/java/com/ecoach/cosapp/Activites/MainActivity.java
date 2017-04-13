package com.ecoach.cosapp.Activites;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.android.volley.RequestQueue;
import com.ecoach.cosapp.Activites.Company.ManageReps.ManageReps;
import com.ecoach.cosapp.Activites.Company.ManangeMyCompanies;
import com.ecoach.cosapp.Activites.UserAccounts.LoginActivity;
import com.ecoach.cosapp.Activites.UserAccounts.ProfileEditActivity;
import com.ecoach.cosapp.Application.Application;
import com.ecoach.cosapp.DataBase.AppInstanceSettings;
import com.ecoach.cosapp.DataBase.Categories;
import com.ecoach.cosapp.DataBase.CompanyRepInvite;
import com.ecoach.cosapp.DataBase.User;
import com.ecoach.cosapp.Http.Terminator2;
import com.ecoach.cosapp.Http.VolleySingleton;
import com.ecoach.cosapp.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import com.ecoach.cosapp.Fragments.CategoriesFragment;
import com.ecoach.cosapp.Fragments.HomeFragment;
import com.ecoach.cosapp.Fragments.RecentFragment;
import com.ecoach.cosapp.RecycleAdapters.MainCategoryAdapter;
import com.ecoach.cosapp.Utilities.CircularTextView;
import com.ecoach.cosapp.Utilities.ViewUtils;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.joanzapata.iconify.widget.IconButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MaterialTabListener,HomeFragment.OnFragmentInteractionListener, CategoriesFragment.OnFragmentInteractionListener, RecentFragment.OnFragmentInteractionListener{

    ViewPagerAdapter pagerAdapter;
    MaterialTabHost tabHost;
    ViewPager pager;
    AppInstanceSettings appInstanceSettings;
    User user;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private boolean isLoggedIn = false;

    int count = 0;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();


        // find MenuItem you want to change
        //MenuItem loginOLogout = menu.findItem(R.id.logout);
       // loginOLogout.setTitle("Login");
        //loginOLogout.se
        navigationView.setNavigationItemSelectedListener(this);
        setTabHost();



        setNavHeaderItems(navigationView);


        try{

            count = CompanyRepInvite.getCompanyRepInvitations(Application.AppUserKey).size();

            Log.d("Notifications", count + "<<count   +   Key>>" + Application.AppUserKey+ " user lname : "+User.getUserByKey(Application.AppUserKey).getLname()+ " user id" +User.getUserByKey(Application.AppUserKey).getId() );
            invalidateOptionsMenu();

        }catch (Exception e){

         e.printStackTrace();
        }



    }


    private void setNavHeaderItems(NavigationView navigationView){

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.userName);
        TextView nav_email = (TextView)hView.findViewById(R.id.userEmail);

        CircleImageView imageView = (CircleImageView)hView.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    if(appInstanceSettings.isloggedIn() == false){
                        new SweetAlertDialog(MainActivity.this)
                                .setTitleText("You need to login !")
                                .show();

                    }else{
                        Intent intent = new Intent(MainActivity.this,ProfileEditActivity.class);
                        startActivity(intent);

                    }

                }catch (Exception e){

                    new SweetAlertDialog(MainActivity.this)
                            .setTitleText("You need to login !")
                            .show();

                }





            }
        });


        try{
             appInstanceSettings = AppInstanceSettings.load(AppInstanceSettings.class,1);
             user = User.getUserByKey(appInstanceSettings.getUserkey());

            isLoggedIn = appInstanceSettings.isloggedIn();



            if(isLoggedIn ){

                nav_user.setText(user.getFname() + " " + user.getLname());
                nav_email.setText(user.getEmail());


                navigationView.getMenu().findItem(R.id.comp).setVisible(true);
                navigationView.getMenu().findItem(R.id.login).setVisible(false);
                navigationView.getMenu().findItem(R.id.logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.manage).setVisible(true);
            }else{

                nav_user.setText("You are not logged in");
                navigationView.getMenu().findItem(R.id.comp).setVisible(false);
                navigationView.getMenu().findItem(R.id.login).setVisible(true);
                navigationView.getMenu().findItem(R.id.logout).setVisible(false);


                //check if user is a rep

                navigationView.getMenu().findItem(R.id.manage).setVisible(false);
                navigationView.getMenu().findItem(R.id.stats).setVisible(false);
            }

        }catch (Exception e){

            navigationView.getMenu().findItem(R.id.login).setVisible(true);
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);

            e.printStackTrace();
        }




    }

    //initialise the android maetial design tab host
    private void setTabHost() {

        tabHost = (MaterialTabHost) this.findViewById(R.id.materialTabHost);

        pager = (ViewPager) this.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        // init view pager
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }


        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }



    }

    private void logout(){

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Logout!")
                .setContentText("Are you sure you want to logout ?")
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        AppInstanceSettings appInstanceSettings = AppInstanceSettings.load(AppInstanceSettings.class,1);
                        Application.AppUserKey = "";
                         appInstanceSettings.setIsloggedIn(false);
                         appInstanceSettings.setUserkey("");
                         appInstanceSettings.save();


                        ProcessPhoenix.triggerRebirth(MainActivity.this);
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }

                })
                .show();




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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cat) {
            Intent intent = new Intent(MainActivity.this,MainCategories.class);
            startActivity(intent);
        } else if (id == R.id.recent) {
            Intent intent = new Intent(MainActivity.this,MainRecentChats.class);
            startActivity(intent);
        } else if (id == R.id.comp) {

            Intent intent = new Intent(MainActivity.this,ManangeMyCompanies.class);
            startActivity(intent);

        } else if (id == R.id.manage) {
            Intent intent = new Intent(MainActivity.this,ManageReps.class);
            startActivity(intent);

        } else if (id == R.id.stats) {

        } else if (id == R.id.share) {

        } else if (id == R.id.logout){

            logout();

        }else if(id == R.id.login){

            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.badge_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

       TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);

        ImageButton imageButton=(ImageButton)notifCount.findViewById(R.id.imagebutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    if(AppInstanceSettings.load(AppInstanceSettings.class,1).isloggedIn() == false){

                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Sorry,")
                                .setContentText("You need to Login")
                                .setConfirmText("Login")
                                .setCancelText("Cancel")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        sweetAlertDialog.dismiss();
                                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();




                    }else{

                        Intent intent = new Intent(MainActivity.this,NotificationCenter.class);
                        startActivity(intent);

                    }


                }catch (Exception e){

                  e.printStackTrace();

                }










            }
        });
      //  tv.setStrokeWidth(1);

        if(count == 0){
            tv.setVisibility(View.INVISIBLE);

        }else{

            tv.setVisibility(View.VISIBLE);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {


        super.onResume();

invalidateOptionsMenu();


        startService(new Intent(this, Terminator2.class));

        Log.d("Onresume","oresume");
    }

    @Override
    public void onTabSelected(MaterialTab tab) {

        pager.setCurrentItem(tab.getPosition());

        Log.d("tab",tab.getPosition()+"");

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles = {
                "Home", "Categories","Recent Chats"
        };

        Fragment fragment=null;


        //create a viewpager method to handle fragments call

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {

/*
*  switch (index) {
           case 0:
               // Top Rated fragment activity
               return new Categories().newInstance("","");
           case 1:
               // Games fragment activity
               return new RecentSearch().newInstance("","");
           case 2:
               // Movies fragment activity
               return new RecentSearch().newInstance("","");
       }
*
*
* */

            if(num==0)
            {



                fragment = new HomeFragment().newInstance("", "");
            } if(num==1)
            {

                fragment=new CategoriesFragment().newInstance("","");



            }if(num==2){

                fragment=  new RecentFragment().newInstance("","");

            }
            return fragment;




        }



        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }





}
