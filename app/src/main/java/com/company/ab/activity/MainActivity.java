package com.company.ab.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.company.ab.R;
import com.company.ab.activity.ui.dashboard.DashBoardFragment;
import com.company.ab.activity.ui.gallery.GalleryFragment;
import com.company.ab.activity.ui.home.HomeFragment;
import com.company.ab.activity.ui.leaderboard.LeaderBoardFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private SNavigationDrawer sNavigationDrawer;
    private Class fragmentClass;
    private static Fragment fragment;


    private static final int PERMISSION_ALL=1;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    private String username;

    private ImageView userimage;
    private TextView usertext,emailtext;
    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("admin");

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("Dashboard", R.drawable.third));
        menuItems.add(new MenuItem("Campaign",R.drawable.third));
        menuItems.add(new MenuItem("Global",R.drawable.third));
        menuItems.add(new MenuItem("Leaderboard",R.drawable.third));
        menuItems.add(new MenuItem("Sign Out",R.drawable.third));

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  DashBoardFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }


        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = DashBoardFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = HomeFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = GalleryFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = LeaderBoardFragment.class;
                        break;
                    }
                    case 4:{
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this,LoginAndSignupActivity.class));
                        finish();
                    }
                }
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                     Toast.makeText(MainActivity.this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
