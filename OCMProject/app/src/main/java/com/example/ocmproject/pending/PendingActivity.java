package com.example.ocmproject.pending;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingActivity extends AppCompatActivity implements PendingAdapter.ItemClickListener{

    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ArrayList<User> list;
    PendingAdapter adapter;
    RecyclerView recycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        //list.add(new User("a","a","a","a"));
        adapter = new PendingAdapter(this, list);
        recycleView = findViewById(R.id.pendingList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        recycleView.setAdapter(adapter);
        mDatabase.child("NewUser").child(auth.getUid()).child("Pending").addValueEventListener(new ValueEventListener() {
            //mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String friendId = snap.getValue(String.class);
                    //Iterable<Object> = mDatabase.child("Users").child(userId).child("Contacts").get
                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("NewUser").child(friendId);
                    friendRefs.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull  Task<DataSnapshot> task) {
                            if (!task.isSuccessful()){
                                ///
                            }
                            else {
                                DataSnapshot snapsho = task.getResult();
                                User friendUserObj = snapsho.getValue(User.class);
                                list.add(friendUserObj);
                                adapter.notifyDataSetChanged();
                            }
                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.acceptButton:
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                mDatabase.child("NewUser").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            ///
                        }
                        else{
                            User current = task.getResult().getValue(User.class);
                            current.acceptContact(adapter.getItem(position).getId());
                            Toast.makeText(PendingActivity.this, "c", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                break;
            case R.id.rejectPending:
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                mDatabase.child("NewUser").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            ///
                        }
                        else{
                            User current = task.getResult().getValue(User.class);
                            current.deleteFromPending(adapter.getItem(position).getId());
                            Toast.makeText(PendingActivity.this, "d", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                break;

            case R.id.userText:
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            default: // for item
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}