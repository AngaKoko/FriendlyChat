package com.anga.friendlychat.data;


import android.support.annotation.NonNull;

public class Messages {

    private long createdAt;
    private String imagePath;
    private String message;
    private String roomId;
    private String uid;
    private String displayName;
    private String id;

    public Messages(){}

    public Messages(long createdAt, String imagePath, String message, String roomId, String uid, String displayName){
        this.createdAt = createdAt;
        this.message = message;
        this.imagePath = imagePath;
        this.roomId = roomId;
        this.uid = uid;
        this.displayName = displayName;
    }

    //used to assign the id of a document from firestore to an object
    public <T extends Messages> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }

    public long getCreatedAt(){return createdAt;}
    public String getMessage(){return message;}
    public String getImagePath(){return imagePath;}
    public String getRoomId(){return roomId;}
    public String getUid(){return uid;}
    public String getDisplayName(){return displayName;}
    public String getId(){return id;}

}
