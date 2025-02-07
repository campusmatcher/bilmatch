package com.example.ocmproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.example.ocmproject.recycleFiles.ConnectionsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ConnectionsFragment extends Fragment implements ConnectionsAdapter.ItemClickListener {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;
//    private ListView listView;
//    private ArrayList<String> list;
//    private ArrayAdapter adapter;
    ArrayList<User> list;
    ConnectionsAdapter adapter;
    RecyclerView recycleView;
    OtherProfileFragment otherFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_connections, container, false);

        auth = FirebaseAuth.getInstance();
//        listView = view.findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        otherFragment = new OtherProfileFragment();
//        list = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_layout, list);
//        listView.setAdapter(adapter);
        list = new ArrayList<>();
        adapter = new ConnectionsAdapter(getActivity(), list);
        recycleView = view.findViewById(R.id.connectionsList);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setClickListener(this);
        recycleView.setAdapter(adapter);




        mDatabase.child("NewUser").child(userId).child("Contacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                adapter.notifyDataSetChanged();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String friendId = snap.getValue(String.class);
                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("NewUser").child(friendId);
                    friendRefs.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()){

                            }
                            else{
                                User friendUserObj = task.getResult().getValue(User.class);
                                String friendNameSurname = friendUserObj.getName() + " " + friendUserObj.getSurname();
                                list.add(friendUserObj);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
        FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //OtherProfileFragment fragment2 = new OtherProfileFragment();
        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,otherFragment).commit();
        Bundle bundle = new Bundle();
        User otherUser = adapter.getItem(position);
        bundle.putSerializable("your_obj", otherUser);
        otherFragment.setArguments(bundle);
        ft.replace(R.id.container, otherFragment);
        ft.addToBackStack(null);
        ft.commit();



    }
//    public void clear() {
//        int size = list.size();
//        if (size > 0) {
//            for (int i = 0; i < size; i++) {
//                list.remove(0);
//            }
//
//            notifyItemRangeRemoved(0, size);
//        }
//    }
}
