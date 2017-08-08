package com.goribmanush.uniassist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Second extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    public Toolbar toolbar;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private NavigationView mNavigationView;
    private TextView mName;
    private TextView mID;
    private CircleImageView mImageView;




    //BottomNavigationView bottomNavigationView;

    private int[] tabIcon = {

            R.drawable.ic_news,R.drawable.ic_profile,R.drawable.ic_notification
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Uni Assist");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //Initializing firebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(firebaseUser.getUid());


        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mName   = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.textViewHeaderTitle);
        mID   = (TextView)mNavigationView.getHeaderView(0).findViewById(R.id.textViewHeaderSID);
        mImageView = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.imageViewHeaderIcon);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mName.setText(dataSnapshot.child("name").getValue().toString());
                mID.setText(dataSnapshot.child("sid").getValue().toString());

                String link =dataSnapshot.child("profile_picture").getValue().toString();
                Picasso.with(getBaseContext()).load(link).into(mImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //Fragment Section Code
        viewPager = (ViewPager) findViewById(R.id.fragViewPager);
        setupViewPager(viewPager);


        //Navigation Bar (Bottom)
        tabLayout = (TabLayout) findViewById(R.id.fragTabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcons();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Retrieving Current User Data and setting it to Views;



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //End of on Create.

    //Fragment Functionality works here:
    private void setUpIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout.getTabAt(1).setIcon(tabIcon[1]);
        tabLayout.getTabAt(2).setIcon(tabIcon[2]);

    }

    private void setupViewPager( ViewPager viewPager ) {

        ViewPagerAdapter fragAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        fragAdapter.addFragment(new Fragment_news(),"");
        fragAdapter.addFragment(new Fragment_Profile(),"");
        fragAdapter.addFragment(new Fragment_Notification(),"");
        viewPager.setAdapter(fragAdapter);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navProfile) {

        } /*else if (id == R.id.navLibrary) {

        } else if (id == R.id.navSettings) {

        }*/
        else if (id == R.id.navLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(Activity_Second.this,MainActivity.class));


        }else if (id == R.id.navWeb) {
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.stamforduniversity.edu.bd/"));
            startActivity(web);

        }
        else if (id == R.id.navFacebook) {
            Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Stamford-University-Bangladesh-32596542401/?ref=br_rs"));
            startActivity(fb);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
