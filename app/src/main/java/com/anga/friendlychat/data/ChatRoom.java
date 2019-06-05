package com.anga.friendlychat.data;

import android.support.annotation.NonNull;

public class ChatRoom {

    private long createdAt;
    private long updatedAt;
    private String roomName;
    private String description;
    private String lastMessage;
    private String imgUrl;
    private String id;

    public ChatRoom(){}

    public ChatRoom(long createdAt, long updatedAt, String roomName, String description, String lastMessage, String imgUrl){
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roomName = roomName;
        this.description = description;
        this.lastMessage = lastMessage;
        this.imgUrl = imgUrl;
    }

    //used to assign the id of a document from firestore to an object
    public <T extends ChatRoom> T withId(@NonNull final String id) {
        this.id = id;
        return (T) this;
    }

    public String getId(){return  id;}
    public long getCreatedAt(){return createdAt;}
    public long getUpdatedAt(){return updatedAt;}
    public String getRoomName(){return roomName;}
    public String getDescription(){return description;}
    public String getLastMessage(){return lastMessage;}
    public String getImgUrl(){return imgUrl;}
}
