package com.example.ocmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ocmproject.Fragments.ConnectionsFragment;
import com.example.ocmproject.Fragments.MatchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Yavuz was here
    // Sencer was here
    // bilal was here
    // Arda was here
    //again

    private ImageView profileButton;
    BottomNavigationView bottomNavigationView;

    MatchFragment matchFragment = new MatchFragment();
    ConnectionsFragment connectionsFragment = new ConnectionsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        profileButton = findViewById(R.id.profile_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, matchFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.match:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, matchFragment).commit();
                        return true;
                    case R.id.connections:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, connectionsFragment).commit();
                        return true;
                }

                return false;
            }
        });


    }
}
