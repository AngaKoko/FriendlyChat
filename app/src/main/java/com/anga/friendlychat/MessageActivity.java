package com.anga.friendlychat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.anga.friendlychat.adapters.MessageAdapter;
import com.anga.friendlychat.data.ChatRoom;
import com.anga.friendlychat.data.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nullable;

public class MessageActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    ListenerRegistration mRegistration;

    RecyclerView mRecyclerView;
    MessageAdapter mAdapter;
    ImageButton mSendButton, mGalleryButton, mNavImageButton;
    ImageView mProfileImage;
    EditText mMessageText;
    TextView mHeader;

    ChatRoom mRoom;

    String mUid, mDisplayName, mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDisplayName = mAuth.getCurrentUser().getDisplayName();
        mEmail = mAuth.getCurrentUser().getEmail();

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
            //(3) assign relevant variables
            mHeader.setText(mRoom.getRoomName());
            if(mRoom.getImgPath() != null)Utils.setProfileImage(this, mRoom.getImgPath(), mProfileImage);

            listenForMessages();
        }

        mNavImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mMessageText.getText().toString().trim()))
                    return;
                sendMessage(mMessageText.getText().toString());
            }
        });
    }

    //(4) listen to life data
    private void listenForMessages(){
        Query query = db.collection("messages")
                .whereEqualTo("roomId", mRoom.getId())
                .orderBy("createdAt", Query.Direction.ASCENDING);

        mRegistration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("MESSAGE_LISTENER", "listen:error", e);
                    return;
                }

                Messages messages;
                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Log.d("MESSAGE_LISTENER", "New message: " + dc.getDocument().getData());
                            messages = dc.getDocument().toObject(Messages.class).withId(dc.getDocument().getId());
                            mAdapter.addMessage(messages);
                            break;
                        case MODIFIED:
                            Log.d("MESSAGE_LISTENER", "Modified message: " + dc.getDocument().getData());
                            break;
                        case REMOVED:
                            Log.d("MESSAGE_LISTENER", "Removed message: " + dc.getDocument().getData());
                            break;
                    }
                }
            }
        });
    }

    //(5) send message
    private void sendMessage(String message){
        long ts = System.currentTimeMillis();

        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("message", message);
        messageMap.put("createdAt", ts);
        messageMap.put("roomId", mRoom.getId());
        messageMap.put("uid", mUid);
        messageMap.put("displayName", mDisplayName);

        db.collection("messages").add(messageMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        //Update the room document
        Map<String, Object> roomMap = new HashMap<>();
        roomMap.put("updatedAt", ts);
        db.collection("rooms").document(mRoom.getId()).update(roomMap);

        mMessageText.setText("");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mRegistration.remove();
    }
}
