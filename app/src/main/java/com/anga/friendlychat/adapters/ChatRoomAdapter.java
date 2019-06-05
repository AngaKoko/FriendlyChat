package com.anga.friendlychat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anga.friendlychat.R;
import com.anga.friendlychat.Utils;
import com.anga.friendlychat.data.ChatRoom;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {
    ArrayList<ChatRoom> mRooms = new ArrayList<>();
    Context mContext;
    private final chatRoomOnClickListener mChatRoomOnClickListener;

    public ChatRoomAdapter(Context context, chatRoomOnClickListener chatRoomOnClickListener) {
        this.mChatRoomOnClickListener = chatRoomOnClickListener;
        this.mContext = context;
    }

    public interface chatRoomOnClickListener{
        void onClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.list_chat_room;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ChatRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        //return the size of chat room
        if(mRooms != null) return mRooms.size();
        return 0;
    }

    //Get a room at a certain position
    private ChatRoom getItem(int position){return mRooms.get(position);}

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mRoomName
                ,mUdatedAt
                ,mLastMessage;
        ImageButton mRoomImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRoomImage = itemView.findViewById(R.id.room_image);
            mRoomName = itemView.findViewById(R.id.chat_room_name);
            mUdatedAt = itemView.findViewById(R.id.updated_at);
            mLastMessage = itemView.findViewById(R.id.last_message);
        }

        void bindView(int position){
            ChatRoom chatRoom = getItem(position);

            mRoomName.setText(chatRoom.getRoomName());
            mLastMessage.setText(chatRoom.getLastMessage());
            mUdatedAt.setText(Utils.getShortDate(chatRoom.getCreatedAt()));
        }

        @Override
        public void onClick(View v) {
            //Get position of Adapter
            int position = getAdapterPosition();
            //Handle the click
            mChatRoomOnClickListener.onClick(position);
        }
    }
}
