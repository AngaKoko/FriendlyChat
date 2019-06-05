package com.anga.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anga.friendlychat.adapters.ChatRoomAdapter;

public class MainActivity extends AppCompatActivity implements ChatRoomAdapter.chatRoomOnClickListener {

    RecyclerView mRecyclerView;
    ChatRoomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        //Creating a Layout for our RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //Assigning the layout to our RecycleView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ChatRoomAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(int position) {

    }
}
