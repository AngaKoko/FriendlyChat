package com.anga.friendlychat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anga.friendlychat.R;
import com.anga.friendlychat.Utils;
import com.anga.friendlychat.data.Messages;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<Messages> mMessages = new ArrayList<>();
    Context mContext;

    public MessageAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutForListItem = R.layout.list_message;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        if(mMessages != null) return mMessages.size();
        return 0;
    }

    public Messages getItem(int position){
        return mMessages.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mDisplayName
                ,mMessageText
                ,mTimeText
                ,mSentText
                ,mSentTime;
        CardView mMessageLayout
                ,mSendLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDisplayName = itemView.findViewById(R.id.display_name);
            mMessageText = itemView.findViewById(R.id.message_text);
            mTimeText = itemView.findViewById(R.id.time_text);
            mSentText = itemView.findViewById(R.id.sent_text);
            mSentTime = itemView.findViewById(R.id.sent_time);
            mMessageLayout = itemView.findViewById(R.id.message_layout);
            mSendLayout = itemView.findViewById(R.id.send_layout);
        }

        void bindView(int position){
            Messages messages = getItem(position);

            mSentText.setText(messages.getMessage());
            mSentTime.setText(Utils.getTime(messages.getCreatedAt()));
        }
    }
}
