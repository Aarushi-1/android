package com.example.aarushi.tootha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {
        private RecyclerView mRecyclerview;
        private ImageAdapter mAdapter;

        private ProgressBar mProgress_circle;

        private DatabaseReference mDatabaseRef;
        private List<Uploading> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fimages);

        mRecyclerview =findViewById(R.id.recycler_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mProgress_circle=findViewById(R.id.progress_circle);

        mUploads =new ArrayList<>();

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("uploads");
        mAdapter=new ImageAdapter(ImagesActivity.this,mUploads);

        mRecyclerview.setAdapter(mAdapter);
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Uploading uploading = postSnapshot.getValue(Uploading.class);
                    mUploads.add(uploading);
                }

               // mAdapter=new ImageAdapter(ImagesActivity.this,mUploads);

                //mRecyclerview.setAdapter(mAdapter);
                mProgress_circle.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(ImagesActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgress_circle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
