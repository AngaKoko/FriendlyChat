package com.anga.friendlychat;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.anga.friendlychat.adapters.MessageAdapter;
import com.anga.friendlychat.data.ChatRoom;

public class MessageActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MessageAdapter mAdapter;
    ImageButton mSendButton, mGalleryButton, mNavImageButton;
    ImageView mProfileImage;
    EditText mMessageText;
    TextView mHeader;

    ChatRoom mRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mSendButton = findViewById(R.id.send_button);
        mGalleryButton = findViewById(R.id.gallery_button);
        mMessageText = findViewById(R.id.message_text);
        mNavImageButton = findViewById(R.id.nav_image_button);
        mHeader = findViewById(R.id.header);
        mProfileImage = findViewById(R.id.room_image);

        mRecyclerView = findViewById(R.id.recycler_view);

        //Creating a Layout for our RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        //Assigning the layout to our RecycleView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MessageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        //(2) get chat room from extra
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            mRoom = (ChatRoom) intent.getSerializableExtra("room");
        }
        //ToDo (3) assign relevant variables

        mNavImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //ToDo (4) listen to life data

    //ToDo (5) send message
}
